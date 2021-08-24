package com.bharat.in_mem_db.controller;


import com.bharat.in_mem_db.exceptions.ColumnMismatchException;
import com.bharat.in_mem_db.exceptions.DuplicatePrimaryKeyEntryException;
import com.bharat.in_mem_db.exceptions.NoDbFoundException;
import com.bharat.in_mem_db.exceptions.NoTableFoundException;
import com.bharat.in_mem_db.model.*;
import com.bharat.in_mem_db.repository.DatabaseRepository;

public class TableControllerImpl implements TableController {

    private final DatabaseRepository databaseRepository;

    public TableControllerImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public Response<String> createTable(String databaseName, Table table) {
        try {
            Database database = databaseRepository.get(databaseName);
            return new Response<>(200, "success");
        } catch (NoDbFoundException e) {
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
    public Response<String> insertInto(String databaseName, String tableName, Row row) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            table.addRow(row);
            return new Response<>(200, "success");
        } catch (NoDbFoundException  | NoTableFoundException | DuplicatePrimaryKeyEntryException | ColumnMismatchException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> findOne(String databaseName, String tableName, DataType primaryKey) {
        try {
            Database database = databaseRepository.get(databaseName);
            Table table = database.getTable(tableName);
            Row row = table.getRow(primaryKey);
            return new Response<>(200, row.toString());
        } catch (NoDbFoundException | NoTableFoundException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> findMultiple(String databaseName) {
        return new Response<>(200, "success");
    }
}
