package com.bharat.in_mem_db.model;

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

    public void addTable(Table table) {
        tableMap.put(table.getName(), table);
    }

    public void removeTable(String tableName) throws NoTableFoundException {
        if (this.containsTable(tableName)) {
            this.removeTable(tableName);
        } else {
            throw new NoTableFoundException("Table do not exist.");
        }
    }

    public boolean containsTable(String tableName) {
        return tableMap.containsKey(tableName);
    }

    public Table getTable(String tableName) throws NoTableFoundException {
        if (!tableMap.containsKey(tableName)) {
            throw new NoTableFoundException("Table does not exist");
        }
        return tableMap.get(tableName);
    }

    public List<Table> getAllTable() {
        return new ArrayList<>(tableMap.values());
    }

    public String getName() {
        return name;
    }
}
