package com.bharat.in_mem_db.controller;


import com.bharat.in_mem_db.exceptions.*;
import com.bharat.in_mem_db.model.*;
import com.bharat.in_mem_db.repository.DatabaseRepository;

import java.util.List;

public class TableControllerImpl implements TableController {

    private final DatabaseRepository databaseRepository;

    public TableControllerImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public Response<String> createTable(String databaseName, Table table) {
        try {
            Database database = databaseRepository.get(databaseName);
            database.addTable(table);
            return new Response<>(200, "success");
        } catch (NoDbFoundException | DuplicateTableException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> dropTable(String databaseName, String tableName) {
        try {
            Database database = databaseRepository.get(databaseName);
            database.removeTable(tableName);
            return new Response<>(200, "success");
        } catch (NoDbFoundException | NoTableFoundException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> showTables(String databaseName) {
        try {
            Database database = databaseRepository.get(databaseName);
            System.out.println("List of tables in the db : " + databaseName);
            for (Table table : database.getAllTable()) {
                System.out.println(table.getName());
            }
            return new Response<>(200, "success");
        } catch (NoDbFoundException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> insertRow(String databaseName, String tableName, Row row) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            table.addRow(row);
            return new Response<>(200, "success");
        } catch (NoDbFoundException | NoTableFoundException | DuplicatePrimaryKeyEntryException | ColumnMismatchException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> deleteRow(String databaseName, String tableName, DataType primaryKey) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            table.removeRow(primaryKey);
            return new Response<>(200, "success");
        } catch (NoDbFoundException | NoTableFoundException | NoSuchRowException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> findMultiple(String databaseName, String tableName, List<String> columns, List<DataType> values) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            List<Row> rows = table.getMultipleRow(columns, values);
            System.out.println("Rows matching the criteria : ");
            for (Row row : rows) {
                System.out.println(row);
            }
            return new Response<>(200, "success");
        } catch (NoDbFoundException | NoTableFoundException | NoSuchRowException | NoSuchColumnException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> findOne(String databaseName, String tableName, DataType primaryKey) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            Row row = table.getOneRow(primaryKey);
            return new Response<>(200, row.toString());
        } catch (NoDbFoundException | NoTableFoundException | NoSuchRowException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> getAllRows(String databaseName, String tableName) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            List<Row> rows = table.getAllRows();
            System.out.println("Printing the table " + tableName);
            for (Row row : rows) {
                System.out.println(row);
            }
            return new Response<>(200, "success");
        } catch (NoDbFoundException | NoTableFoundException | NoSuchRowException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }
}
