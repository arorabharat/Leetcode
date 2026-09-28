package com.bharat.in_mem_db;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== 1. Initialize Database & Table ==========");
        Database db = new Database("ecommerce");

        Schema schema = new Schema(List.of(
                new Column("id", DataType.INTEGER, true),
                new Column("name", DataType.STRING, false, false),
                new Column("category", DataType.STRING),
                new Column("price", DataType.INTEGER),
                new Column("in_stock", DataType.BOOLEAN)
        ));

        Table products = db.createTable("products", schema);
        products.createIndex("category");
        System.out.println("Created table 'products' with secondary index on 'category'.");

        System.out.println("\n========== 2. Insert Records ==========");
        products.insert(new Row(1, "Sony WH-1000XM4", "Electronics", 350, true));
        products.insert(new Row(2, "Apple AirPods Max", "Electronics", 500, true));
        products.insert(new Row(3, "Kindle Paperwhite", "Electronics", 140, false));
        products.insert(new Row(4, "Clean Code Book", "Books", 35, true));
        products.insert(new Row(5, "Ergonomic Desk", "Furniture", 250, true));
        System.out.println("Inserted 5 rows successfully.");

        System.out.println("\n========== 3. Constraint Validations (Error Handling) ==========");
        try {
            // Duplicate Primary Key
            products.insert(new Row(1, "Duplicate ID Product", "Misc", 10, true));
        } catch (Exception e) {
            System.out.println("Caught expected PK conflict: " + e.getMessage());
        }

        try {
            // Type Mismatch: Passing String where Integer is expected
            products.insert(new Row(6, "Invalid Type Product", "Misc", "one hundred", true));
        } catch (Exception e) {
            System.out.println("Caught expected type mismatch: " + e.getMessage());
        }

        System.out.println("\n========== 4. Point Query (Primary Key O(1)) ==========");
        products.get(2).ifPresent(row -> System.out.println("Found PK=2: " + row));

        System.out.println("\n========== 5. Query via Secondary Index ('category' = 'Electronics') ==========");
        List<Row> electronics = products.query(
                List.of(new Condition("category", Operator.EQUALS, "Electronics")),
                List.of("id", "name", "price"),
                null
        );
        electronics.forEach(row -> System.out.println("  " + row));

        System.out.println("\n========== 6. Complex Filter (price > 100 AND in_stock == true) with Limit ==========");
        List<Row> expensiveInStock = products.query(
                List.of(
                        new Condition("price", Operator.GREATER_THAN, 100),
                        new Condition("in_stock", Operator.EQUALS, true)
                ),
                List.of("name", "price"),
                2
        );
        expensiveInStock.forEach(row -> System.out.println("  " + row));

        System.out.println("\n========== 7. Update Record ==========");
        products.update(1, new Row(1, "Sony WH-1000XM4 (Sale)", "Electronics", 299, true));
        products.get(1).ifPresent(row -> System.out.println("Updated PK=1: " + row));

        System.out.println("\n========== 8. Delete Record ==========");
        boolean deleted = products.delete(3);
        System.out.println("Deleted PK=3: " + deleted);
        System.out.println("Check PK=3: " + products.get(3).orElse(null));

        System.out.println("\n========== Execution Complete ==========");
    }
}
