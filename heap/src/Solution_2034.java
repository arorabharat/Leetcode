import java.util.*;

public class Solution_2034 {

    // brute force
    class Approach_1 {

        class StockPrice {
            private final Map<Integer, Integer> stockPrices = new HashMap<>();
            private final PriorityQueue<Integer> minq = new PriorityQueue<>();
            private final PriorityQueue<Integer> maxq = new PriorityQueue<>(Comparator.reverseOrder());
            int maxTimeStamp = -1;

            public StockPrice() {
            }

            public void update(int timestamp, int price) {
                if (stockPrices.containsKey(timestamp)) {
                    minq.remove(stockPrices.get(timestamp));
                    maxq.remove(stockPrices.get(timestamp));
                }
                minq.add(price);
                maxq.add(price);
                stockPrices.put(timestamp, price);
                maxTimeStamp = Math.max(maxTimeStamp, timestamp);
            }

            public int current() {
                return stockPrices.getOrDefault(maxTimeStamp, -1);
            }

            public int minimum() {
                return (minq.isEmpty()) ? -1 : minq.peek();
            }

            public int maximum() {
                return (maxq.isEmpty()) ? -1 : maxq.peek();
            }
        }
    }

    // amortised time complexity of O(1)
    // Lazy removal - but this still gives amortised time complexity
    // what if we want to do better on each function call
    class Approach_2 {

        class StockPrice {

            private final Map<Integer, Integer> stockPrices = new HashMap<>();
            private final PriorityQueue<Integer> minq = new PriorityQueue<>();
            private final PriorityQueue<Integer> minqr = new PriorityQueue<>();
            private final PriorityQueue<Integer> maxq = new PriorityQueue<>(Comparator.reverseOrder());
            private final PriorityQueue<Integer> maxqr = new PriorityQueue<>(Comparator.reverseOrder());
            int maxTimeStamp = -1;

            public StockPrice() {
            }

            public void update(int timestamp, int price) {
                if (stockPrices.containsKey(timestamp)) {
                    minqr.add(stockPrices.get(timestamp));
                    maxqr.add(stockPrices.get(timestamp));
                }
                minq.add(price);
                maxq.add(price);
                stockPrices.put(timestamp, price);
                maxTimeStamp = Math.max(maxTimeStamp, timestamp);
            }

            public int current() {
                return stockPrices.getOrDefault(maxTimeStamp, -1);
            }

            public int minimum() {
                while (!minqr.isEmpty() && !minq.isEmpty() && minqr.peek().equals(minq.peek())) {
                    minqr.remove();
                    minq.remove();
                }
                return (minq.isEmpty()) ? -1 : minq.peek();
            }

            public int maximum() {
                while (!maxqr.isEmpty() && !maxq.isEmpty() && maxqr.peek().equals(maxq.peek())) {
                    maxqr.remove();
                    maxq.remove();
                }
                return (maxq.isEmpty()) ? -1 : maxq.peek();
            }
        }
    }

    class Approach_3 {

        class StockPrice {

            private final SortedMap<Integer, Integer> priceToFrequency = new TreeMap<>();
            private final Map<Integer, Integer> timestampToMap = new TreeMap<>();
            int maxTimeStamp = -1;

            public StockPrice() {
            }

            public void update(int timestamp, int price) {
                if (timestampToMap.containsKey(timestamp)) {
                    Integer prevPrice = timestampToMap.get(timestamp);
                    int newFreq = priceToFrequency.get(prevPrice) - 1;
                    if (newFreq > 0) {
                        priceToFrequency.put(prevPrice, newFreq);
                    } else {
                        priceToFrequency.remove(prevPrice);
                    }
                }
                timestampToMap.put(timestamp, price);
                priceToFrequency.put(price, priceToFrequency.getOrDefault(price, 0) + 1);
                maxTimeStamp = Math.max(maxTimeStamp, timestamp);
            }

            public int current() {
                return timestampToMap.getOrDefault(maxTimeStamp, -1);
            }

            public int minimum() {
                return (priceToFrequency.isEmpty()) ? -1 : priceToFrequency.firstKey();
            }

            public int maximum() {
                return (priceToFrequency.isEmpty()) ? -1 : priceToFrequency.lastKey();
            }
        }
    }


    class StockPrice {

        private final Map<Integer, Integer> timeToPrice = new HashMap<>();
        private final TreeMap<Integer, Set<Integer>> sortedByPrice = new TreeMap<>();
        int latestTimestamp = -1;

        public StockPrice() {
        }

        public void update(int timestamp, int price) {
            Integer prevPrice = timeToPrice.get(timestamp);
            timeToPrice.put(timestamp, price);
            if (prevPrice != null) {
                assert sortedByPrice.containsKey(prevPrice);
                sortedByPrice.get(prevPrice).remove(timestamp);
                if (sortedByPrice.get(prevPrice).isEmpty()) {
                    sortedByPrice.remove(prevPrice);
                }
            }
            addTimestampToSortedPriceTable(timestamp, price);
            latestTimestamp = Math.max(latestTimestamp, timestamp);
        }

        private void addTimestampToSortedPriceTable(int timestamp, int price) {
            if (!sortedByPrice.containsKey(price)) {
                sortedByPrice.put(price, new HashSet<>());
            }
            sortedByPrice.get(price).add(timestamp);
        }

        public int current() {
            return latestTimestamp != -1 ? 0 : timeToPrice.get(latestTimestamp);
        }

        public int minimum() {
            return (sortedByPrice.isEmpty()) ? 0 : sortedByPrice.firstKey();
        }

        public int maximum() {
            return (sortedByPrice.isEmpty()) ? 0 : sortedByPrice.lastKey();
        }
    }

/**
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */
}
