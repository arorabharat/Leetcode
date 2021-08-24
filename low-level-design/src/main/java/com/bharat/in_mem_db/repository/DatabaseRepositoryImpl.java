package com.bharat.in_mem_db.repository;

import com.bharat.in_mem_db.exceptions.DbAlreadyExistException;
import com.bharat.in_mem_db.exceptions.NoDbFoundException;
import com.bharat.in_mem_db.model.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseRepositoryImpl implements DatabaseRepository {

    private final Map<String, Database> storage;

    public DatabaseRepositoryImpl() {
        storage = new HashMap<>();
    }

    @Override
    public void save(Database database) throws DbAlreadyExistException {
        if (storage.containsKey(database.getName())) {
            throw new DbAlreadyExistException("Database do no exists");
        }
        storage.put(database.getName(), database);
    }

    @Override
    public Database get(String name) throws NoDbFoundException {
        if (!storage.containsKey(name)) {
            throw new NoDbFoundException("Database do no exists");
        }
        return storage.get(name);
    }

    @Override
    public void remove(String name) throws NoDbFoundException {
        if (!storage.containsKey(name)) {
            throw new NoDbFoundException("Database do no exists");
        }
        storage.remove(name);
    }

    @Override
    public List<String> getAll() {
        return new ArrayList<>(storage.keySet());
    }
}
