package com.bharat.in_mem_db;

public enum DataType {
    INTEGER,
    STRING,
    BOOLEAN;

    public boolean isValid(Object value) {
        if (value == null) return true;
        return switch (this) {
            case INTEGER -> value instanceof Integer;
            case STRING -> value instanceof String;
            case BOOLEAN -> value instanceof Boolean;
        };
    }
}
