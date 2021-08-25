package com.bharat.in_mem_db.controller;

import com.bharat.in_mem_db.model.DataType;
import com.bharat.in_mem_db.model.Response;
import com.bharat.in_mem_db.model.Row;
import com.bharat.in_mem_db.model.Table;

import java.util.List;

public interface TableController {

    Response<String> createTable(String databaseName, Table table);

    Response<String> dropTable(String databaseName, String tableName);

    Response<String> showTables(String databaseName);

    Response<String> insertInto(String databaseName, String tableName, Row row);

    Response<String> deleteRow(String databaseName, String tableName, DataType primaryKey);

//    Response<String> findMultiple(String databaseName, String tableName, List<Class<? extends DataType>> columns, List<>);
    Response<String> findMultiple(String databaseName);

    Response<String> findOne(String databaseName, String tableName, DataType primaryKey);

    Response<String> findAll(String databaseName, String tableName);
}
