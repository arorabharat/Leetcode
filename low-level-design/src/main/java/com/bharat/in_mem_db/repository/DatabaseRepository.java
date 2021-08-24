package com.bharat.in_mem_db.repository;

import com.bharat.in_mem_db.exceptions.DbAlreadyExistException;
import com.bharat.in_mem_db.exceptions.NoDbFoundException;
import com.bharat.in_mem_db.model.Database;

import java.util.List;

public interface DatabaseRepository {
    void save(Database database) throws DbAlreadyExistException;

    Database get(String name) throws NoDbFoundException;

    void remove(String name) throws NoDbFoundException;

    List<String> getAll();
}
