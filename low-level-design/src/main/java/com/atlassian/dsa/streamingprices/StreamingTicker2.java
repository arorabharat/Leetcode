package com.atlassian.dsa.streamingprices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class StreamingTicker2 {

    public static class StreamingTickerService {

        private final AtomicLong latestTime = new AtomicLong();
        private final ReentrantLock lock = new ReentrantLock();
        private final ConcurrentHashMap<Long, Integer> priceChart = new ConcurrentHashMap<>();
        private final ConcurrentSkipListMap<Integer, Integer> sortedPriceFreq = new ConcurrentSkipListMap<>();

        public void updatePrice(long timeStamp, int newPrice) {
            lock.lock();
            Integer oldPrice = priceChart.get(timeStamp);
            if (oldPrice != null) {
                int oldFreq = sortedPriceFreq.get(oldPrice);
                if (oldFreq == 1) {
                    sortedPriceFreq.remove(oldPrice);
                } else {
                    sortedPriceFreq.put(oldPrice, oldFreq - 1);
                }
            }
            sortedPriceFreq.compute(newPrice, (k, v) -> (v == null) ? 1 : v + 1);
            priceChart.put(timeStamp, newPrice);
            latestTime.compareAndSet(latestTime.longValue(), Math.max(timeStamp, latestTime.longValue()));
            lock.unlock();
        }

        public int getCurrentPrice() {
            return priceChart.getOrDefault(latestTime.longValue(), -1);
        }

        public int getMaxPrice() {
            return sortedPriceFreq.isEmpty() ? -1 : sortedPriceFreq.lastKey();
        }

        public int getMinPrice() {
            return sortedPriceFreq.isEmpty() ? -1 : sortedPriceFreq.firstKey();
        }
    }

    public static void main(String[] args) {

    }
}
