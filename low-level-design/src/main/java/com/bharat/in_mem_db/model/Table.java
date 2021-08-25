package com.bharat.in_mem_db.model;

import com.bharat.in_mem_db.exceptions.ColumnMismatchException;
import com.bharat.in_mem_db.exceptions.DuplicatePrimaryKeyEntryException;
import com.bharat.in_mem_db.exceptions.NoSuchColumnException;
import com.bharat.in_mem_db.exceptions.NoSuchRowException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private final String name;
    private final String primaryKeyColumn;
    private final Map<DataType, Row> tableRows;
    private final Map<String, Class<? extends DataType>> columnMap;

    public Table(String name, List<String> columnNames, List<Class<? extends DataType>> columnsDataType, String primaryKeyColumn) {
        this.name = name;
        this.tableRows = new HashMap<>();
        this.primaryKeyColumn = primaryKeyColumn;
        this.columnMap = new HashMap<>();
        if (columnNames.size() != columnsDataType.size()) {
            throw new IllegalArgumentException("Number of columns not equal to number of data types.");
        }
        for (int i = 0; i < columnNames.size(); i++) {
            this.columnMap.put(columnNames.get(i), columnsDataType.get(i));
        }
        if (!columnMap.containsKey(primaryKeyColumn)) {
            throw new IllegalArgumentException("Primary key column do not exist");
        }
    }

    public String getName() {
        return name;
    }

    public Row getOneRow(DataType primaryKey) throws NoSuchRowException {
        if (!tableRows.containsKey(primaryKey)) {
            throw new NoSuchRowException("Row with primary key does not exist");
        }
        return tableRows.get(primaryKey);
    }

    public List<Row> getMultipleRow(List<String> columns, List<DataType> values) throws NoSuchRowException, NoSuchColumnException {
        for (String columnName : columns) {
            if (!columnMap.containsKey(columnName)) {
                throw new NoSuchColumnException("Column does not exist");
            }
        }
        List<Row> resultantRows = new ArrayList<>();
        for (DataType primaryKey : tableRows.keySet()) {
            Row row = tableRows.get(primaryKey);
            boolean match = true;
            for (int i = 0; i < columns.size(); i++) {
                if (!values.get(i).equals(row.getValue(columns.get(i)))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                resultantRows.add(row);
            }
        }
        return resultantRows;
    }


    public List<Row> getAllRows() throws NoSuchRowException {
        return new ArrayList<>(tableRows.values());
    }

    public Row addRow(Row newRow) throws DuplicatePrimaryKeyEntryException, ColumnMismatchException {
        DataType primaryKey = newRow.getValue(primaryKeyColumn);
        if (tableRows.containsKey(primaryKey)) {
            throw new DuplicatePrimaryKeyEntryException("Primary key already exist in table");
        }
        validateRow(newRow);
        return tableRows.put(primaryKey, newRow);
    }

    public void removeRow(DataType primaryKey) throws NoSuchRowException {
        if (!tableRows.containsKey(primaryKey)) {
            throw new NoSuchRowException("Primary key do not exist in table");
        }
        tableRows.remove(primaryKey);
    }

    public void updateRow(Row updatedRow) throws NoSuchRowException, ColumnMismatchException {
        DataType primaryKey = updatedRow.getValue(primaryKeyColumn);
        if (!tableRows.containsKey(primaryKey)) {
            throw new NoSuchRowException("Row does not exist in table");
        }
        validateRow(updatedRow);
        tableRows.put(primaryKey, updatedRow);
    }

    private void validateRow(Row newRow) throws ColumnMismatchException {
        if (newRow.size() != columnMap.size()) {
            throw new ColumnMismatchException("Number of values in newRow is not equal to the number of column in table.");
        }
        for (String column : columnMap.keySet()) {
            Class<? extends DataType> fromRow = newRow.getValue(column).getClass();
            Class<? extends DataType> fromColumn = columnMap.get(column);
            if (!fromRow.equals(fromColumn)) {
                throw new ColumnMismatchException("Number of values in newRow is not equal to the number of column in table.");

            }
        }
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", rows=" + tableRows +
                '}';
    }
}
