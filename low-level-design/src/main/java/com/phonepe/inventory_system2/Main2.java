package com.phonepe.inventory_system2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


class Product {

    private final String productId;
    private final String desc;

    public Product(String desc) {
        this.productId = "product-" + UUID.randomUUID().toString().substring(0,8);
        this.desc = desc;
    }

    public String getProductId() {
        return productId;
    }

    public String getDesc() {
        return desc;
    }
}

// similarly product repository.

enum OrderStatus {
    CREATED,
    ITEM_RESERVED,
    CANCELLED,
    PENDING_PAYMENT,
    SHIPPED
}

class Order {
    // immutable
    private final String orderId;
    private final String userId;

    // mutable
    private final Map<String, Integer> items;
    private OrderStatus orderStatus;

    public Order(String userId) {
        this.orderId = "order-" + UUID.randomUUID().toString().substring(0, 8);
        this.userId = userId;
        this.items = new ConcurrentHashMap<>();
        this.orderStatus = OrderStatus.CREATED;
    }

    public void addItem(String productId, int quantity) {
        this.items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

class InventoryStats {

    // immutable
    private final String productId;

    // mutable
    private int availableCount;
    private int reservedCount;

    public InventoryStats(String productId, int availableCount) {
        this.availableCount = availableCount;
        this.reservedCount = 0;
        this.productId = productId;
    }

    public synchronized int getAvailableCount() {
        return availableCount;
    }

    public String getProductId() {
        return this.productId;
    }

    public synchronized void addInventory(int quantity) {
        this.availableCount += quantity;
    }

    public synchronized void reserveInventory(int quantity) throws UnavailableInventoryException {
        if (this.availableCount - quantity >= 0) {
            this.availableCount -= quantity;
            this.reservedCount += quantity;
        } else {
            throw new UnavailableInventoryException("Product : " + this.getProductId() + " is out of stock for requested quantity :" + quantity + " available quantity :" + this.availableCount);
        }
    }

    public synchronized void releaseInventory(int quantity) throws UnavailableInventoryException {
        if (this.reservedCount - quantity >= 0) {
            this.reservedCount -= quantity;
            this.availableCount += quantity;
        } else {
            throw new UnavailableInventoryException("Invalid request to release : " + quantity + " for Product : " + this.getProductId() + " but available. " + this.reservedCount);
        }
    }

    public synchronized void shipInventory(int quantity) throws UnavailableInventoryException {
        if (this.reservedCount - quantity >= 0) {
            this.reservedCount -= quantity;
        } else {
            throw new UnavailableInventoryException("Invalid request to ship : " + quantity + " for Product : " + this.getProductId() + " but reserve quantity in stock : " + this.reservedCount);
        }
    }
}


interface InventoryService {

    void addInventory(Map<String, Integer> items);

    Integer getInventory(String productId);

    void fulfillReserveInventory(Map<String, Integer> items) throws UnavailableInventoryException, InvalidProductIdException;

    void reserveInventory(Map<String, Integer> items) throws UnavailableInventoryException, InvalidProductIdException;

    void releaseReservedInventory(Map<String, Integer> items) throws InvalidProductIdException, UnavailableInventoryException;
}

class ProductService {

    private final Map<String, Product> productsDB;

    public ProductService() {
        this.productsDB = new ConcurrentHashMap<>();
    }

    public String addProduct(String desc) {
        Product p = new Product(desc);
        productsDB.put(p.getProductId(), p);
        return p.getProductId();
    }

    public Product getProduct(String productId) {
        return this.productsDB.get(productId);
    }
}


class OrderManagementService {

    private final InventoryService inventoryService;

    public OrderManagementService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void checkoutOrder(Order order) {
        Map<String, Integer> items = order.getItems();
        if (items.isEmpty()) {
            throw new RuntimeException("No item in order :" + order.getOrderId());
        }
        try {
            inventoryService.reserveInventory(items);
            order.setOrderStatus(OrderStatus.ITEM_RESERVED);
            System.out.println("Fulfilled order : " + order.getOrderId());
        } catch (UnavailableInventoryException e) {
            throw new RuntimeException("Unable to place the Order as Item is out of stock :" + order.getOrderId());
        } catch (InvalidProductIdException e) {
            throw new RuntimeException("Invalid product Id  in the order :" + order.getOrderId());
        }
    }

    void confirmPayment(Order order) {
        try {
            this.inventoryService.fulfillReserveInventory(order.getItems());
            order.setOrderStatus(OrderStatus.SHIPPED);
        } catch (UnavailableInventoryException e) {
            throw new RuntimeException("Unable to place the Order as Item is out of stock :" + order.getOrderId());
        } catch (InvalidProductIdException e) {
            throw new RuntimeException("Invalid product Id in the order :" + order.getOrderId());
        }
    }

    void cancelOrder(Order order) {
        try {
            this.inventoryService.releaseReservedInventory(order.getItems());
            order.setOrderStatus(OrderStatus.CANCELLED);
        } catch (UnavailableInventoryException e) {
            throw new RuntimeException("Unable to place the Order as Item is out of stock :" + order.getOrderId());
        } catch (InvalidProductIdException e) {
            throw new RuntimeException("Invalid product Id in the order :" + order.getOrderId());
        }
    }
}


class UnavailableInventoryException extends Exception {
    public UnavailableInventoryException(String message) {
        super(message);
    }
}

class InvalidProductIdException extends Exception {
    public InvalidProductIdException(String message) {
        super(message);
    }
}


class InventoryServiceImpl implements InventoryService {

    private final Map<String, InventoryStats> inventoryDB;

    public InventoryServiceImpl() {
        this.inventoryDB = new ConcurrentHashMap<>();
    }

    @Override
    public void addInventory(Map<String, Integer> items) {
        for (String productId : items.keySet()) {
            System.out.println(productId);
            inventoryDB.computeIfAbsent(productId, k -> new InventoryStats(productId, 0));
            inventoryDB.get(productId).addInventory(items.get(productId));
        }
        System.out.println(inventoryDB);
    }

    @Override
    public Integer getInventory(String productId) {
        if(this.inventoryDB.containsKey(productId)) {
            return this.inventoryDB.get(productId).getAvailableCount();
        } else {
            return 0;
        }
    }

    @Override
    public void fulfillReserveInventory(Map<String, Integer> items) throws UnavailableInventoryException, InvalidProductIdException {
        for (String productId : items.keySet()) {
            if (!inventoryDB.containsKey(productId)) {
                throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
            }
        }
        for (String productId : inventoryDB.keySet()) {
            inventoryDB.get(productId).shipInventory(items.get(productId));
        }
    }

    @Override
    public void reserveInventory(Map<String, Integer> items) throws UnavailableInventoryException, InvalidProductIdException {
        for (String productId : items.keySet()) {
            if (!inventoryDB.containsKey(productId)) {
                throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
            }
        }
        for (String productId : items.keySet()) {
            inventoryDB.get(productId).reserveInventory(items.get(productId));
        }
    }

    @Override
    public void releaseReservedInventory(Map<String, Integer> items) throws InvalidProductIdException, UnavailableInventoryException {

        for (String productId : items.keySet()) {
            if (!inventoryDB.containsKey(productId)) {
                throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
            }
        }
        for (String productId : inventoryDB.keySet()) {
            inventoryDB.get(productId).releaseInventory(items.get(productId));
        }

    }
}

public class Main2 {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryServiceImpl();

        Product iphone = new Product("Iphone");
        Map<String, Integer> items = new HashMap<>();
        items.put(iphone.getProductId(), 2);
        inventoryService.addInventory(items);

        System.out.println("iphone inventory : " + inventoryService.getInventory(iphone.getProductId()));

        OrderManagementService orderManagementService = new OrderManagementService(inventoryService);
        Order o1 = new Order("user1");
        o1.addItem(iphone.getProductId(), 1);
        Order o2 = new Order("user2");
        o2.addItem(iphone.getProductId(), 1);
        System.out.println("O1 : " + o1.getOrderId());
        System.out.println("O2 : " + o2.getOrderId());
        Runnable r1 = () -> orderManagementService.checkoutOrder(o1);
        Runnable r2 = () -> orderManagementService.checkoutOrder(o2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("iphone inventory" + inventoryService.getInventory(iphone.getProductId()));
    }
}

