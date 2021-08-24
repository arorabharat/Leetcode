package com.bharat.in_mem_db.controller;

import com.bharat.in_mem_db.exceptions.DbAlreadyExistException;
import com.bharat.in_mem_db.exceptions.NoDbFoundException;
import com.bharat.in_mem_db.model.Database;
import com.bharat.in_mem_db.model.Response;
import com.bharat.in_mem_db.repository.DatabaseRepository;

public class DbControllerImpl implements DbController {

    private final DatabaseRepository databaseRepository;

    public DbControllerImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public Response<String> createDb(String name) {
        try {
            Database database = new Database(name);
            databaseRepository.save(database);
            return new Response<>(200, "success");
        } catch (DbAlreadyExistException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> dropDb(String name) {
        try {
            databaseRepository.remove(name);
            return new Response<>(200, "success");
        } catch (NoDbFoundException e) {
            e.printStackTrace();
            return new Response<>(400, e.getMessage());
        }
    }

    @Override
    public Response<String> showDatabases() {
        System.out.println("List of all databases : ");
        for (String name : databaseRepository.getAll()) {
            System.out.println(name);
        }
        return new Response<>(200, "success");
    }
}
