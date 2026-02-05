package com.atlassian.dsa.streamingprices;

import java.util.*;

public class StreamingTicker {

    private Map<Integer, Integer> timePrice;
    private TreeMap<Integer, Set<Integer>> priceTime;
    int maxPrice;
    int maxPriceTimestamp;
    // Uses red black tree => self balancing bst
    // put, get, remove => log n

    public static void main(String[] args) {
        StreamingTicker streamingTicker = new StreamingTicker();
        streamingTicker.upsertCommodityPrice(1, 5);
        streamingTicker.upsertCommodityPrice(2, 11);
        streamingTicker.upsertCommodityPrice(3, 8);
        streamingTicker.upsertCommodityPrice(2, 12);

        System.out.println(streamingTicker.getMaxCommodityPrice());
    }

    StreamingTicker() {
        timePrice = new HashMap<>();
        priceTime = new TreeMap<>();
        maxPrice = Integer.MIN_VALUE;
    }

    // Question : What to do if I get a duplicate time? Do I ignore or keep it

    public void upsertCommodityPriceV1(int timestamp, int commodityPrice) {
        timePrice.put(timestamp, commodityPrice);
        if (commodityPrice >= maxPrice) {
            maxPrice = commodityPrice;
            maxPriceTimestamp = timestamp;
        } else if (timestamp == maxPriceTimestamp && commodityPrice < maxPrice) {
            Map.Entry<Integer, Integer> maxEntry = timePrice.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .get();
            maxPriceTimestamp = maxEntry.getKey();
            maxPrice = maxEntry.getValue();
        }
    }

    public void upsertCommodityPrice(int timestamp, int commodityPrice) {
        Integer oldPrice = timePrice.put(timestamp, commodityPrice);
        if (oldPrice != null) {
            priceTime.get(oldPrice).remove(timestamp);
            if (priceTime.get(oldPrice).isEmpty()) {
                priceTime.remove(oldPrice);
            }
        }
        priceTime.computeIfAbsent(commodityPrice, k -> new HashSet<>()).add(timestamp);

    }


    public void getMaxCommodityPriceInRange(int timestamp, int commodityPrice, int start) {
//        tickerPrice.
        if (commodityPrice > maxPrice) {
            maxPrice = commodityPrice;
        }
    }

    // What if we haven't received any price yet? Do we return 0 or min value?
    public int getMaxCommodityPrice() {
        return priceTime.isEmpty() ? Integer.MIN_VALUE : priceTime.lastKey();
    }
}