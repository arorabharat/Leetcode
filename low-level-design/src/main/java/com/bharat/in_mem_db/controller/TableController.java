package com.bharat.in_mem_db.controller;

import com.bharat.in_mem_db.model.DataType;
import com.bharat.in_mem_db.model.Response;
import com.bharat.in_mem_db.model.Row;
import com.bharat.in_mem_db.model.Table;

public interface TableController {
    Response<String> createTable(String databaseName, Table table);

    Response<String> dropTable(String databaseName, String tableName);

    Response<String> insertInto(String databaseName, String tableName, Row row);

    Response<String> findMultiple(String databaseName);

    Response<String> findOne(String databaseName, String tableName, DataType primaryKey);
}
