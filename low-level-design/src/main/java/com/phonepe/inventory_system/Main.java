package com.phonepe.inventory_system;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


class Product {

    private final String productId;
    private final String desc;

    public Product(String desc) {
        this.productId = UUID.randomUUID().toString();
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
    PENDING_PAYMENT,
    SHIPPED
}

enum PaymentStatus {
    SUCCESS, PENDING, FAILED;
}

class Order {
    String orderId;
    String trxId;
    List<Product> product;
    PaymentStatus paymentStatus;
    OrderStatus orderStatus;
}

interface InventoryRepository {

    void addInventory(String productId, int quantity) throws InvalidProductIdException;

    void initialiseInventory(Product product);

    void reduceInventory(String productId, int quantity) throws UnavailableInventoryException, InvalidProductIdException;

    void reserveInventory(String productId, int quantity) throws UnavailableInventoryException, InvalidProductIdException;

    void releaseReservedInventory(String productId, int quantity) throws InvalidProductIdException, UnavailableInventoryException;
}


class OrderManagementService {

    private final InventoryRepository inventoryRepository = new InventoryDB();
    private final Map<String, Product> productMap = new ConcurrentHashMap<>();

    public String addProduct(String desc) {
        Product p = new Product(desc);
        productMap.put(p.getProductId(), p);
        inventoryRepository.initialiseInventory(p);
        return p.getProductId();
    }

    void addInventory(String productId, int quantity) throws InvalidProductIdException {
        inventoryRepository.addInventory(productId, quantity);
    }

    void addToCart(String productId, int quantity, Order order) throws InvalidProductIdException, UnavailableInventoryException {
        Product p = productMap.get(productId);
        if(p == null) {
            throw  new InvalidProductIdException("Product id is not correct :"+productId);
        }
        order.product.add(p);
        inventoryRepository.reserveInventory(productId, quantity);
    }

    void cancelOrder(Order orderId) {

    }

    void fulfilOrder(String productId, int quantity) throws UnavailableInventoryException, InvalidProductIdException {
        inventoryRepository.reduceInventory(productId, quantity);
    }
}


class InventoryStats {

    private final Product product;
    private final AtomicInteger availableCount;
    private final AtomicInteger reservedCount;

    public InventoryStats(Product product, int availableCount) {
        this.availableCount = new AtomicInteger(availableCount);
        this.reservedCount = new AtomicInteger(0);
        this.product = product;
    }

    public int getAvailableCount() {
        return availableCount.get();
    }

    public AtomicInteger getReservedCount() {
        return reservedCount;
    }

    public Product getProduct() {
        return this.product;
    }

    public InventoryStats addInventoryCount(int delta) {
        this.availableCount.addAndGet(delta);
        return this;
    }

    public synchronized InventoryStats reserveAvailableCount(int quantity) throws UnavailableInventoryException {
        int currCount = this.availableCount.get();
        if (currCount - quantity >= 0) {
            this.availableCount.addAndGet(-quantity);
            this.reservedCount.addAndGet(quantity);
            return this;
        }
        throw new UnavailableInventoryException("Product : " + this.product.getProductId() + " is out of stock for requested quantity :" + quantity + " available quantity :" + this.availableCount.get());
    }

    public synchronized InventoryStats releaseReserveCount(int quantity) throws UnavailableInventoryException {
        int currCount = this.reservedCount.get();
        if (currCount - quantity >= 0) {
            this.reservedCount.getAndAdd(-quantity);
            this.availableCount.getAndAdd(quantity);
            return this;
        }
        throw new UnavailableInventoryException("Invalid request to release : " + quantity + " for Product : " + this.product.getProductId() + " but available. " + this.reservedCount.get());
    }

    public synchronized InventoryStats shipReservedCount(int quantity) throws UnavailableInventoryException {
        int currCount = this.reservedCount.get();
        if (currCount - quantity >= 0) {
            this.reservedCount.compareAndSet(currCount, currCount - quantity);
            return this;
        }
        throw new UnavailableInventoryException("Invalid request to ship : " + quantity + " for Product : " + this.product.getProductId() + " but reserve quantity in stock : " + this.reservedCount.get());
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


class InventoryDB implements InventoryRepository {

    private final Map<String, InventoryStats> inventoryStatsByProductId = new ConcurrentHashMap<>();

    @Override
    public void addInventory(String productId, int quantity) throws InvalidProductIdException {
        if (!inventoryStatsByProductId.containsKey(productId)) {
            throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
        } else {
            inventoryStatsByProductId.get(productId).addInventoryCount(quantity);
        }
    }

    @Override
    public void initialiseInventory(Product product) {
        inventoryStatsByProductId.computeIfAbsent(product.getProductId(), k -> new InventoryStats(product, 0));
    }

    @Override
    public void reduceInventory(String productId, int quantity) throws UnavailableInventoryException, InvalidProductIdException {
        if (!inventoryStatsByProductId.containsKey(productId)) {
            throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
        } else {
            inventoryStatsByProductId.get(productId).shipReservedCount(quantity);
        }
    }

    @Override
    public void reserveInventory(String productId, int quantity) throws UnavailableInventoryException, InvalidProductIdException {
        if (!inventoryStatsByProductId.containsKey(productId)) {
            throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
        } else {
            inventoryStatsByProductId.get(productId).releaseReserveCount(quantity);
        }
    }

    @Override
    public void releaseReservedInventory(String productId, int quantity) throws InvalidProductIdException, UnavailableInventoryException {
        if (!inventoryStatsByProductId.containsKey(productId)) {
            throw new InvalidProductIdException("Inventory not initialised for product :" + productId);
        } else {
            inventoryStatsByProductId.get(productId).releaseReserveCount(quantity);
        }
    }
}

public class Main {


    public static void main(String[] args) {

    }
}
