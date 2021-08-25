package com.bharat.in_mem_db.driver;

import com.bharat.in_mem_db.controller.DbController;
import com.bharat.in_mem_db.controller.DbControllerImpl;
import com.bharat.in_mem_db.controller.TableController;
import com.bharat.in_mem_db.controller.TableControllerImpl;
import com.bharat.in_mem_db.model.*;
import com.bharat.in_mem_db.repository.DatabaseRepository;
import com.bharat.in_mem_db.repository.DatabaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DatabaseRepository databaseRepository = new DatabaseRepositoryImpl();
        DbController dbController = new DbControllerImpl(databaseRepository);
        String products = "products";
        System.out.println(dbController.createDb(products).getObject());
        String users = "users";
        System.out.println(dbController.createDb(users).getObject());
        System.out.println(dbController.dropDb(users).getObject());
        dbController.showDatabases();
        TableController tableController = new TableControllerImpl(databaseRepository);
        List<Class<? extends DataType>> columns = new ArrayList<>();
        columns.add(CustomInt.class);
        columns.add(CustomString.class);
        String headphone = "Headphone";
        Table table = new Table(headphone, columns, 0);
        System.out.println(tableController.createTable(products, table));
        System.out.println(tableController.showTables(products));
//        List<DataType> values = new ArrayList<>();
//        values.add(new CustomInt(1));
//        values.add(new CustomString("bharat"));
//        Row row = new Row(values);
//        tableController.insertInto(products, headphone, row);
//        System.out.println(table);table
    }
}
