package com.atlassian.dsa.streamingprices;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StreamingTicker1 {


    static class StreamingPrice {

        long latestTimestamp = -1;
        Map<Long, Integer> timeToPriceMap = new HashMap<>();
        TreeMap<Integer, Integer> priceToTimestampFreq = new TreeMap<>();

        StreamingPrice() {
        }

        void updatePrice(long timeStamp, int newPrice) {
            if (timeToPriceMap.containsKey(timeStamp)) {
                // reduce the frequency
                int oldPrice = timeToPriceMap.get(timeStamp);
                int oldFreq = priceToTimestampFreq.get(oldPrice);
                if (oldFreq == 1) {
                    priceToTimestampFreq.remove(oldPrice);
                } else {
                    priceToTimestampFreq.put(oldPrice, oldFreq - 1);
                }
            }
            timeToPriceMap.put(timeStamp, newPrice);
            priceToTimestampFreq.compute(newPrice, (k, v) -> v == null ? 1 : v + 1);
            latestTimestamp = Math.max(latestTimestamp, timeStamp);
        }

        int getCurrentPrice() {
            return timeToPriceMap.getOrDefault(latestTimestamp, -1);
        }

        int getMaxPrice() {
            return priceToTimestampFreq.isEmpty() ? Integer.MIN_VALUE : priceToTimestampFreq.lastKey();
        }

        int getMinPrice() {
            return priceToTimestampFreq.isEmpty() ? Integer.MAX_VALUE : priceToTimestampFreq.firstKey();
        }
    }


    public static void main(String[] args) {
        StreamingPrice streamingPrice = new StreamingPrice();
    }
}
