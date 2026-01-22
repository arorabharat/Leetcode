package com.atlassian.costexplorer;

import java.util.*;

public class Main {

    record Price(double value) {
    }

    record Report() {
    }

    record Subscription(String id, String name, Price price) {
    }

    record Customer(String id, String name) {
    }


    class CostExplorerService {

        Map<String, Customer> custDb = new HashMap<>();
        Map<String, Subscription> subsDb = new HashMap<>();
        Map<String, Set<String>> customerSubscriptions = new HashMap<>();

        void createCustomer(String customerName) {
            String id = UUID.randomUUID().toString();
            custDb.put(id, new Customer(id, customerName));
        }

        void createSubscription(String subscriptionName, double price) {
            String id = UUID.randomUUID().toString();
            subsDb.put(id, new Subscription(id, subscriptionName, new Price(price)));
        }

        boolean subscribeCustomer(String customerId, String subscriptionId) {
            if(!custDb.containsKey(customerId)) {
                throw new RuntimeException("Customer Do not exist");
            }
            if(!subsDb.containsKey(subscriptionId)) {
                throw new RuntimeException("Customer Do not exist");
            }

            if(!customerSubscriptions.containsKey(customerId)) {
                customerSubscriptions.put(customerId, new HashSet<>());
            }
            if(customerSubscriptions.get(customerId).contains(subscriptionId)) {
                return false;
            } else {
                customerSubscriptions.get(customerId).add(subscriptionId);
                return true;
            }
        }

    }



    public static void main(String[] args) {

    }
}
