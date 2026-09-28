package com.bharat.in_mem_db;

import java.util.*;

public class Database {
    private final String name;
    private final Map<String, Table> tables = new HashMap<>();

    public Database(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Table createTable(String tableName, Schema schema) {
        if (tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table '" + tableName + "' already exists in database '" + name + "'");
        }
        Table table = new Table(tableName, schema);
        tables.put(tableName, table);
        return table;
    }

    public Table getTable(String tableName) {
        Table table = tables.get(tableName);
        if (table == null) {
            throw new NoSuchElementException("Table '" + tableName + "' does not exist in database '" + name + "'");
        }
        return table;
    }

    public boolean dropTable(String tableName) {
        return tables.remove(tableName) != null;
    }

    public Set<String> listTables() {
        return Collections.unmodifiableSet(tables.keySet());
    }
}
