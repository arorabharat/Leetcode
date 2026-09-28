package com.bharat.in_mem_db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {
    private final List<Column> columns;
    private final Map<String, Integer> columnIndexMap = new HashMap<>();
    private final int primaryKeyIndex;

    public Schema(List<Column> columns) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("Schema must contain at least one column");
        }
        this.columns = List.copyOf(columns);
        int pkIndex = -1;

        for (int i = 0; i < this.columns.size(); i++) {
            Column col = this.columns.get(i);
            if (columnIndexMap.containsKey(col.name())) {
                throw new IllegalArgumentException("Duplicate column name: " + col.name());
            }
            columnIndexMap.put(col.name(), i);

            if (col.isPrimaryKey()) {
                if (pkIndex != -1) {
                    throw new IllegalArgumentException("Only a single primary key column is supported");
                }
                pkIndex = i;
            }
        }

        if (pkIndex == -1) {
            throw new IllegalArgumentException("A primary key column must be specified");
        }
        this.primaryKeyIndex = pkIndex;
    }

    public int getColumnIndex(String columnName) {
        Integer index = columnIndexMap.get(columnName);
        if (index == null) {
            throw new IllegalArgumentException("Column not found in schema: " + columnName);
        }
        return index;
    }

    public int getPrimaryKeyIndex() {
        return primaryKeyIndex;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void validate(Row row) {
        if (row == null || row.size() != columns.size()) {
            throw new IllegalArgumentException(
                    "Row column count (" + (row == null ? 0 : row.size()) + ") does not match schema count (" + columns.size() + ")"
            );
        }

        for (int i = 0; i < columns.size(); i++) {
            Column col = columns.get(i);
            Object value = row.get(i);

            if (value == null && !col.isNullable()) {
                throw new IllegalArgumentException("Column '" + col.name() + "' cannot be null");
            }

            if (value != null && !col.type().isValid(value)) {
                throw new IllegalArgumentException(
                        "Column '" + col.name() + "' expects type " + col.type() + ", got " + value.getClass().getSimpleName()
                );
            }
        }
    }

    public Row project(Row row, List<String> projectedColumns) {
        if (projectedColumns == null || projectedColumns.isEmpty()) {
            return row;
        }
        Object[] projectedValues = new Object[projectedColumns.size()];
        for (int i = 0; i < projectedColumns.size(); i++) {
            int colIdx = getColumnIndex(projectedColumns.get(i));
            projectedValues[i] = row.get(colIdx);
        }
        return new Row(projectedValues);
    }
}
