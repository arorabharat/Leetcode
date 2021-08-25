package com.bharat.in_mem_db.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Row {

    private final Map<String, DataType> valuesMap;

    public Row(List<String> columnNames, List<DataType> columnValues) {

        valuesMap = new HashMap<>();

        if (columnNames.size() != columnValues.size()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < columnNames.size(); i++) {
            valuesMap.put(columnNames.get(i), columnValues.get(i));
        }
    }

    public int size() {
        return valuesMap.size();
    }

    public DataType getValue(String column) {
        return valuesMap.get(column);
    }

    @Override
    public String toString() {
        return "Row{" +
                "values=" + valuesMap.values().stream().toList() +
                '}';
    }
}

