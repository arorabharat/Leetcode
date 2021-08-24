package com.bharat.in_mem_db.model;

import com.bharat.in_mem_db.exceptions.ColumnMismatchException;
import com.bharat.in_mem_db.exceptions.DuplicatePrimaryKeyEntryException;
import com.bharat.in_mem_db.exceptions.NoSuchRowException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private final String name;
    private final Map<DataType, Row> rows;
    private final List<Class<? extends DataType>> columns;

    public Table(String name, List<Class<? extends DataType>> columns) {
        this.name = name;
        this.rows = new HashMap<>();
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public Row getRow(DataType primaryKey) {
        return rows.get(primaryKey);
    }

    public Row addRow(Row row) throws DuplicatePrimaryKeyEntryException, ColumnMismatchException {
        if (rows.containsKey(row.getPrimaryKey())) {
            throw new DuplicatePrimaryKeyEntryException("Primary key already exist in table");
        }
        int n = row.values.size();
        int m = columns.size();
        if (m != n) {
            throw new ColumnMismatchException("Number of values in row is not equal to the number of column in table.");
        }
        for (int i = 0; i < n; i++) {
            Class<? extends DataType> fromRow = row.values.get(i).getClass();
            Class<? extends DataType> fromColumn = columns.get(i);
            if (!fromRow.equals(fromColumn)) {
                throw new ColumnMismatchException("Number of values in row is not equal to the number of column in table.");

            }
        }
        return rows.put(row.getPrimaryKey(), row);
    }

    public Row updateRow(Row row) throws NoSuchRowException {
        if (!rows.containsKey(row.getPrimaryKey())) {
            throw new NoSuchRowException("Row does not exist in table");
        }
        return rows.get(row.getPrimaryKey());
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", rows=" + rows +
                '}';
    }
}
