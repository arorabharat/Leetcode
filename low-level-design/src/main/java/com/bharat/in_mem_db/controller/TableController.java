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

    Response<String> insertRow(String databaseName, String tableName, Row row);

    Response<String> deleteRow(String databaseName, String tableName, DataType primaryKey);

    Response<String> getAllRows(String databaseName, String tableName);

    Response<String> findMultiple(String databaseName, String tableName, List<String> columns, List<DataType> values);

    Response<String> findOne(String databaseName, String tableName, DataType primaryKey);
}
