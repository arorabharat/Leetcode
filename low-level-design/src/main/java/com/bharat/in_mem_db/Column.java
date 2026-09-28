package com.bharat.in_mem_db;

public record Column(
        String name,
        DataType type,
        boolean isPrimaryKey,
        boolean isNullable
) {
    public Column(String name, DataType type) {
        this(name, type, false, true);
    }

    public Column(String name, DataType type, boolean isPrimaryKey) {
        this(name, type, isPrimaryKey, !isPrimaryKey);
    }
}
