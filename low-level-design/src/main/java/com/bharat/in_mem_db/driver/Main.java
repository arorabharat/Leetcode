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


        System.out.println("Create a database : ");
        System.out.println(dbController.createDb(products).getObject());
        String users = "users";

        System.out.println("Create a database : ");
        System.out.println(dbController.createDb(users).getObject());


        System.out.println("Drop the database : ");
        System.out.println(dbController.dropDb(users).getObject());

        System.out.println("Show all databases : ");
        System.out.println(dbController.showDatabases().getObject());

        TableController tableController = new TableControllerImpl(databaseRepository);

        List<Class<? extends DataType>> columns = new ArrayList<>();
        columns.add(CustomInt.class);
        columns.add(CustomString.class);
        String headphone = "Headphone";
        String speakers = "speakers";
        Table table1 = new Table(headphone, columns, 0);
        Table table2 = new Table(speakers, columns, 0);

        System.out.println("Create a new table : ");
        System.out.println(tableController.createTable(products, table1).getObject());
        System.out.println("Create a new table : ");
        System.out.println(tableController.createTable(products, table2).getObject());

        System.out.println("List all the table : ");
        System.out.println(tableController.showTables(products).getObject());

        List<DataType> valuesList = new ArrayList<>();
        valuesList.add(new CustomInt(1));
        valuesList.add(new CustomString("1000 XM4"));
        Row row = new Row(valuesList);

        List<DataType> valuesList2 = new ArrayList<>();
        valuesList2.add(new CustomInt(2));
        valuesList2.add(new CustomString("1001 XM4"));
        Row row2 = new Row(valuesList2);

        System.out.println("Inserting ino the table : ");
        System.out.println(tableController.insertInto(products,headphone,row).getObject());
        System.out.println("Inserting ino the table : ");
        System.out.println(tableController.insertInto(products,headphone,row2).getObject());

        System.out.println("Find using the primary key : ");
        System.out.println(tableController.findOne(products,headphone,new CustomInt(1)).getObject());
        System.out.println("Find using the primary key : ");
        System.out.println(tableController.findOne(products,headphone,new CustomInt(2)).getObject());
        System.out.println("Find all rows in the table : ");
        System.out.println(tableController.findAll(products,headphone).getObject());

        System.out.println("Delete row using primary key in the table : ");
        System.out.println(tableController.deleteRow(products,headphone,new CustomInt(2)).getObject());

        System.out.println("Find all rows in the table : ");
        System.out.println(tableController.findAll(products,headphone).getObject());

        System.out.println("Drop the table : ");
        System.out.println(tableController.dropTable(products,headphone).getObject());

        System.out.println("List all the table : ");
        System.out.println(tableController.showTables(products).getObject());


//        tableController.insertInto(products,headphone,)
//        List<DataType> values = new ArrayList<>();
//        values.add(new CustomInt(1));
//        values.add(new CustomString("bharat"));
//        Row row = new Row(values);
//        tableController.insertInto(products, headphone, row);
//        System.out.println(table);table
    }
}
