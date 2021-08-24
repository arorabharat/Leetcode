package com.bharat.in_mem_db.controller;

import com.bharat.in_mem_db.model.Response;

public interface DbController {

    Response<String> createDb(String name);

    Response<String> dropDb(String name);

    Response<String> showDatabases();
}
