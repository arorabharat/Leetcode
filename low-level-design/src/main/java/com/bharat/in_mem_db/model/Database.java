package com.bharat.in_mem_db.model;

import com.bharat.in_mem_db.exceptions.DuplicateTableException;
import com.bharat.in_mem_db.exceptions.NoTableFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private final String name;
    private final Map<String, Table> tableMap;

    public Database(String name) {
        this.name = name;
        this.tableMap = new HashMap<>();
    }

    public void addTable(Table table) throws DuplicateTableException {
        if (tableMap.containsKey(table.getName())) {
            throw new DuplicateTableException("Table with sma name already exist");
        } else {
            tableMap.put(table.getName(), table);
        }
    }

    public void removeTable(String tableName) throws NoTableFoundException {
        if (this.containsTable(tableName)) {
            tableMap.remove(tableName);
        } else {
            throw new NoTableFoundException("Table do not exist.");
        }
    }

    private boolean containsTable(String tableName) {
        return tableMap.containsKey(tableName);
    }

    public Table getTable(String tableName) throws NoTableFoundException {
        if (this.containsTable(tableName)) {
            return tableMap.get(tableName);
        } else {
            throw new NoTableFoundException("Table does not exist");
        }
    }

    public List<Table> getAllTable() {
        return new ArrayList<>(tableMap.values());
    }

    public String getName() {
        return name;
    }
}
