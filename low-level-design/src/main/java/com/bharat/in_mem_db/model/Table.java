package com.bharat.in_mem_db.model;

import com.bharat.in_mem_db.exceptions.ColumnMismatchException;
import com.bharat.in_mem_db.exceptions.DuplicatePrimaryKeyEntryException;
import com.bharat.in_mem_db.exceptions.NoSuchRowException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private final String name;
    private final int primaryKeyColumn;
    private final Map<DataType, Row> tableRows;
    private final List<Class<? extends DataType>> columns;

    public Table(String name, List<Class<? extends DataType>> columns,int primaryKeyColumn) {
        this.name = name;
        this.tableRows = new HashMap<>();
        this.columns = columns;
        this.primaryKeyColumn = primaryKeyColumn;
//        if(primaryKeyColumn >= columns.size()) {
//            // throw error
//        }
    }

    public String getName() {
        return name;
    }

    public Row getRow(DataType primaryKey) throws NoSuchRowException {
        if(!tableRows.containsKey(primaryKey)) {
            throw new NoSuchRowException("Row with primary key does not exist");
        }
        return tableRows.get(primaryKey);
    }

    public List<Row> getAllRows() throws NoSuchRowException {
        return new ArrayList<>(tableRows.values());
    }

    public Row addRow(Row newRow) throws DuplicatePrimaryKeyEntryException, ColumnMismatchException {
        int n = newRow.values.size();
        int m = columns.size();
        if (m != n) {
            throw new ColumnMismatchException("Number of values in newRow is not equal to the number of column in table.");
        }
        DataType primaryKey = newRow.getValues().get(primaryKeyColumn);
        if (tableRows.containsKey(primaryKey)) {
            throw new DuplicatePrimaryKeyEntryException("Primary key already exist in table");
        }
        for (int i = 0; i < n; i++) {
            Class<? extends DataType> fromRow = newRow.values.get(i).getClass();
            Class<? extends DataType> fromColumn = columns.get(i);
            if (!fromRow.equals(fromColumn)) {
                throw new ColumnMismatchException("Number of values in newRow is not equal to the number of column in table.");

            }
        }
        return tableRows.put(primaryKey, newRow);
    }

    public void removeRow(DataType primaryKey) throws NoSuchRowException {
        if (!tableRows.containsKey(primaryKey)) {
            throw new NoSuchRowException("Primary key do not exist in table");
        }
        tableRows.remove(primaryKey);
    }

    public void updateRow(Row updatedRow) throws NoSuchRowException {
        DataType primaryKey = updatedRow.getValues().get(primaryKeyColumn);
        if (!tableRows.containsKey(primaryKey)) {
            throw new NoSuchRowException("Row does not exist in table");
        }
        tableRows.put(primaryKey,updatedRow);
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", rows=" + tableRows +
                '}';
    }
}
