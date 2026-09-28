package com.bharat.in_mem_db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryDbTest {

    private Database db;
    private Table usersTable;

    @BeforeEach
    void setUp() {
        db = new Database("test_db");
        Schema schema = new Schema(List.of(
                new Column("id", DataType.INTEGER, true),
                new Column("name", DataType.STRING, false, false),
                new Column("age", DataType.INTEGER),
                new Column("city", DataType.STRING),
                new Column("active", DataType.BOOLEAN)
        ));
        usersTable = db.createTable("users", schema);
        usersTable.createIndex("city");

        usersTable.insert(new Row(1, "Alice", 25, "New York", true));
        usersTable.insert(new Row(2, "Bob", 35, "San Francisco", true));
        usersTable.insert(new Row(3, "Charlie", 40, "New York", false));
    }

    @Test
    void testGetById() {
        Optional<Row> row = usersTable.get(1);
        assertTrue(row.isPresent());
        assertEquals("Alice", row.get().get(1));
        assertEquals(25, row.get().get(2));
    }

    @Test
    void testDuplicatePrimaryKeyThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                usersTable.insert(new Row(1, "Duplicate Alice", 30, "Chicago", true))
        );
    }

    @Test
    void testTypeMismatchThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                usersTable.insert(new Row(4, "David", "forty", "Boston", true))
        );
    }

    @Test
    void testNotNullConstraintThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                usersTable.insert(new Row(4, null, 28, "Boston", true))
        );
    }

    @Test
    void testQueryBySecondaryIndex() {
        List<Row> rows = usersTable.query(
                List.of(new Condition("city", Operator.EQUALS, "New York")),
                null,
                null
        );
        assertEquals(2, rows.size());
    }

    @Test
    void testQueryWithFilterAndProjection() {
        List<Row> rows = usersTable.query(
                List.of(
                        new Condition("age", Operator.GREATER_THAN, 30),
                        new Condition("active", Operator.EQUALS, true)
                ),
                List.of("id", "name"),
                10
        );
        assertEquals(1, rows.size());
        assertEquals(2, rows.get(0).get(0));     // id
        assertEquals("Bob", rows.get(0).get(1)); // name
        assertEquals(2, rows.get(0).size());     // only projected columns
    }

    @Test
    void testUpdateRow() {
        usersTable.update(1, new Row(1, "Alice Cooper", 26, "Los Angeles", true));
        Optional<Row> updated = usersTable.get(1);
        assertTrue(updated.isPresent());
        assertEquals("Alice Cooper", updated.get().get(1));
        assertEquals("Los Angeles", updated.get().get(3));

        // Verify secondary index updated
        List<Row> oldCity = usersTable.query(
                List.of(new Condition("city", Operator.EQUALS, "New York")),
                null,
                null
        );
        assertEquals(1, oldCity.size()); // only Charlie left
    }

    @Test
    void testDeleteRow() {
        assertTrue(usersTable.delete(2));
        assertFalse(usersTable.get(2).isPresent());

        // Secondary index should no longer contain Bob
        List<Row> sfRows = usersTable.query(
                List.of(new Condition("city", Operator.EQUALS, "San Francisco")),
                null,
                null
        );
        assertTrue(sfRows.isEmpty());
    }
}
