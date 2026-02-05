package com.atlassian.costexplorer;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;

public class CostExplorer2 {


    static class Customer {

        private final String id;
        private final String name;

        public Customer(String name) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
        }
    }

    static class Subscription {

        private final String id;
        private final String name;

        public Subscription(String name) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
        }
    }

    static class Contract {

        private final String id;
        private final String subscriptionId;
        private final long starTime;

        public Contract(String subscriptionId, long starTime) {
            this.id = UUID.randomUUID().toString();
            this.starTime = starTime;
            this.subscriptionId = subscriptionId;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }


    static class CostExplorerService {

        boolean isValidYearStartMonth(Month month) {
            return month == Month.JANUARY || month == Month.APRIL || month == Month.JULY || month == Month.OCTOBER;
        }

        private final Map<String, Subscription> subscriptions = new HashMap<>();
        private final Map<String, Customer> customers = new HashMap<>();
        private final Map<String, Map<Month, Set<Contract>>> cust2Contract = new HashMap<>();
        private final Map<String, Map<String, Contract>> cust2Subs = new HashMap<>();

        public CostExplorerService(List<Customer> customerList, List<Subscription> subscriptionList) {

            for (Customer c : customerList) {
                customers.put(c.id, c);
            }

            for (Subscription s : subscriptionList) {
                subscriptions.put(s.id, s);
            }
        }

        public boolean subscribe(String customerId, String subscriptionId, Month month) {
            if (!customers.containsKey(customerId)) {
                throw new RuntimeException("Invalid customer");
            }
            if (!subscriptions.containsKey(subscriptionId)) {
                throw new RuntimeException("Invalid subscription");
            }
            if (cust2Subs.containsKey(customerId) && cust2Subs.get(customerId).containsKey(subscriptionId)) {
                return false;
            }
            Contract contract = createContractForCustomer(customerId, subscriptionId, month);
            addSubscriptionForCustomer(customerId, subscriptionId, contract);
            return true;
        }

        private void addSubscriptionForCustomer(String customerId, String subscriptionId, Contract contract) {
            cust2Subs.putIfAbsent(customerId, new HashMap<>());
            cust2Subs.get(customerId).put(subscriptionId, contract);
        }

        private Contract createContractForCustomer(String customerId, String subscriptionId, Month month) {
            cust2Contract.putIfAbsent(customerId, new HashMap<>());
            Contract contract = new Contract(subscriptionId, System.currentTimeMillis());
            cust2Contract.get(customerId).putIfAbsent(month, new HashSet<>());
            cust2Contract.get(customerId).get(month).add(contract);
            return contract;
        }

        public List<BigDecimal> getMonthlyCost(String customerId, Month yearStartMonth) {
            if (!isValidYearStartMonth(yearStartMonth)) {
                throw new RuntimeException("Invalid Data");
            }
            if (!customers.containsKey(customerId)) {
                throw new RuntimeException("Invalid Data");
            }
            List<Month> months = List.of(Month.values());

            boolean rollOverYear = true;
            List<BigDecimal> costForCurrentYear = new ArrayList<>();
            BigDecimal productPrice = new BigDecimal("100");
            BigDecimal totalSubscription = new BigDecimal(0);

            for (Month month : months) {
                if (month == yearStartMonth) {
                    rollOverYear = false;
                }
                BigDecimal totalSubscriptionForMonth = getTotalSubscriptionForMonth(customerId, month);
                totalSubscription = totalSubscription.add(totalSubscriptionForMonth);
                if (!rollOverYear) {
                    costForCurrentYear.add(productPrice.multiply(totalSubscription));
                }
            }

            List<BigDecimal> costForRollOverYear = new ArrayList<>();

            for (Month month : months) {
                if (month == yearStartMonth) {
                    break;
                }
                costForRollOverYear.add(productPrice.multiply(totalSubscription));
            }

            List<BigDecimal> costForNext12Months = new ArrayList<>();
            costForNext12Months.addAll(costForRollOverYear);
            costForNext12Months.addAll(costForCurrentYear);
            return costForNext12Months;
        }

        private BigDecimal getTotalSubscriptionForMonth(String customerId, Month month) {
            if (!cust2Contract.containsKey(customerId) || !cust2Contract.get(customerId).containsKey(month)) {
                return new BigDecimal(0);
            }
            return new BigDecimal(cust2Contract.get(customerId).get(month).size());
        }
    }

    public static void main(String[] args) {

        Customer c1 = new Customer("c1");
        Customer c2 = new Customer("c2");
        List<Customer> customerList = new ArrayList<>();
        customerList.add(c1);
        customerList.add(c2);

        Subscription s1 = new Subscription("s1");
        Subscription s2 = new Subscription("s2");
        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(s1);
        subscriptionList.add(s2);
        CostExplorerService service = new CostExplorerService(customerList, subscriptionList);

        service.subscribe(c1.id, s1.id, Month.JANUARY);
        service.subscribe(c1.id, s2.id, Month.JUNE);

        System.out.println(service.getMonthlyCost(c1.id, Month.APRIL));
    }
}
