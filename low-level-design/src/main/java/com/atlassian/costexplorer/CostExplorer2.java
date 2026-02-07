package com.atlassian.costexplorer;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;

public class CostExplorer2 {


    // immutable objects

    enum PlanType {
        BASIC,
        PREMIUM
    }

    enum Product {
        P1,
        P2
    }

    record Price (BigDecimal val) {}

    record Subscription(Product product, PlanType planType) {
    }

    static class Customer {

        private final String id;
        private final String name;

        public Customer(String name) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
        }
    }

    static class Contract {

        private final String id;
        private final Subscription subscription;
        private final Price price;

        private final long starTime;

        public Contract(Subscription subscription, long starTime, Price price) {
            this.id = UUID.randomUUID().toString();
            this.starTime = starTime;
            this.subscription = subscription;
            this.price = price;
        }
    }


    // immutable objects

    static class CustomerService {

        private final Map<String, Customer> customers = new HashMap<>();

        public CustomerService(List<Customer> customerList) {
            for (Customer c : customerList) {
                customers.put(c.id, c);
            }
        }

        public void validate(String cid) {
            if (!this.customers.containsKey(cid)) {
                throw new RuntimeException("Invalid customerId");
            }
        }
    }

    static class PricingService {

        private final Map<Subscription, BigDecimal> product2Price = new HashMap<>();

        public PricingService() {
            this.product2Price.put(new Subscription(Product.P1, PlanType.BASIC), new BigDecimal("20.0"));
            this.product2Price.put(new Subscription(Product.P1, PlanType.PREMIUM), new BigDecimal("100.0"));
            this.product2Price.put(new Subscription(Product.P2, PlanType.BASIC), new BigDecimal("40.0"));
            this.product2Price.put(new Subscription(Product.P2, PlanType.PREMIUM), new BigDecimal("200.0"));
        }

        Price getPrice(Subscription subscription) {
            validate(subscription);
            return new Price(this.product2Price.get(subscription));
        }

        private void validate(Subscription subscription) {
            if (!this.product2Price.containsKey(subscription)) {
                throw new RuntimeException("Invalid subscription");
            }
        }
    }


    static class SubscriptionService {

        private final PricingService pricingService = new PricingService();

        private final Map<String, Map<Subscription, Contract>> cust2Subs = new HashMap<>();

        public boolean isSubscribed(String customerId, Subscription subscription) {
            return cust2Subs.containsKey(customerId) && cust2Subs.get(customerId).containsKey(subscription);
        }

        public Contract subscribe(String customerId, Subscription subscription) {
            if (this.isSubscribed(customerId, subscription)) {
                return cust2Subs.get(customerId).get(subscription);
            } else {
                Price offerredPrice = pricingService.getPrice(subscription);
                Contract contract = new Contract(subscription, System.currentTimeMillis(), offerredPrice);
                cust2Subs.put(customerId, new HashMap<>());
                cust2Subs.get(customerId).put(subscription, contract);
                return contract;
            }
        }

        public List<Contract> getAllContracts(String cid) {
            return this.cust2Subs.get(cid).values().stream().toList();
        }
    }


    static class CostExplorerService {

        private final SubscriptionService subscriptionService;
        private final CustomerService customerService;

        boolean isValidYearStartMonth(Month month) {
            return month == Month.JANUARY || month == Month.APRIL || month == Month.JULY || month == Month.OCTOBER;
        }

        public CostExplorerService(CustomerService customerService, SubscriptionService subscriptionService) {
            this.customerService = customerService;
            this.subscriptionService = subscriptionService;
        }

        List<Month> getOrderedMonthsForYear(Month startMonth) {
            List<Month> months = new ArrayList<>();
            boolean start = false;
            for (Month month : Month.values()) {
                if (month == startMonth) {
                    start = true;
                }
                if (start) {
                    months.add(month);
                }
            }
            for (Month month : Month.values()) {
                if (month == startMonth) {
                    break;
                }
                months.add(month);
            }
            return months;
        }

        boolean beforeStartMonth (long time, Month month) {
            return true;
        }

        public List<BigDecimal> getMonthlyCost(String customerId, Month reportingStartMonth) {
            customerService.validate(customerId);
            if (!isValidYearStartMonth(reportingStartMonth)) {
                throw new RuntimeException("Invalid Reporting start Month");
            }
            List<Contract> allContracts = subscriptionService.getAllContracts(customerId);
            allContracts.sort(Comparator.comparingLong(c -> c.starTime));
//            List<Month> orderedMonths = getOrderedMonthsForYear(reportingStartMonth);
//            List<Contract> beforeReportingMonth = allContracts.stream().filter(c -> beforeStartMonth(c.starTime, reportingStartMonth)).toList();
//            List<Contract> afterReportingMonth = allContracts.stream().filter(c -> !beforeStartMonth(c.starTime, reportingStartMonth)).toList();
//            BigDecimal totalSubscription = new BigDecimal(0);

            for (Contract c : allContracts) {
                Price price = c.price;
                long starTime = c.starTime;

            }

            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {

        Customer c1 = new Customer("c1");
        Customer c2 = new Customer("c2");
        List<Customer> customerList = new ArrayList<>();
        customerList.add(c1);
        customerList.add(c2);

        CustomerService customerService = new CustomerService(customerList);

        SubscriptionService subscriptionService = new SubscriptionService();
        subscriptionService.subscribe(c1.id, new Subscription(Product.P1, PlanType.BASIC));
        subscriptionService.subscribe(c1.id, new Subscription(Product.P1, PlanType.PREMIUM));

        CostExplorerService service = new CostExplorerService(customerService, subscriptionService);

        System.out.println(service.getMonthlyCost(c1.id, Month.APRIL));
    }
}
