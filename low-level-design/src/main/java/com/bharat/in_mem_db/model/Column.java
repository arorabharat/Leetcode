package com.bharat.in_mem_db.model;

public class Column {

    private final Class<? extends DataType> dataType;
    private final String columnName;

    public Column(Class<? extends DataType> dataType, String columnName) {
        this.dataType = dataType;
        this.columnName = columnName;
    }

    public Class<? extends DataType> getDataType() {
        return dataType;
    }

    public String getColumnName() {
        return columnName;
    }
}
