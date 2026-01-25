package com.atlassian.dsa.streamingprices;

import java.util.*;

public class StreamingTicker {

    private Map<Integer, Integer> timeToPrice;
    private TreeMap<Integer, Set<Integer>> priceToTime;
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
        timeToPrice = new HashMap<>();
        priceToTime = new TreeMap<>();
        maxPrice = Integer.MIN_VALUE;
    }

    // Question : What to do if I get a duplicate time? Do I ignore or keep it

    public void upsertCommodityPriceV1(int timestamp, int commodityPrice) {
        timeToPrice.put(timestamp, commodityPrice);
        if (commodityPrice >= maxPrice) {
            maxPrice = commodityPrice;
            maxPriceTimestamp = timestamp;
        } else if (timestamp == maxPriceTimestamp && commodityPrice < maxPrice) {
            Map.Entry<Integer, Integer> maxEntry = timeToPrice.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .get();
            maxPriceTimestamp = maxEntry.getKey();
            maxPrice = maxEntry.getValue();
        }
    }

    public void upsertCommodityPrice(int timestamp, int commodityPrice) {
        Integer oldPrice = timeToPrice.put(timestamp, commodityPrice);
        if (oldPrice != null) {
            priceToTime.get(oldPrice).remove(timestamp);
            if (priceToTime.get(oldPrice).isEmpty()) {
                priceToTime.remove(oldPrice);
            }
        }
        priceToTime.computeIfAbsent(commodityPrice, k -> new HashSet<>()).add(timestamp);

    }


    public void getMaxCommodityPriceInRange(int timestamp, int commodityPrice, int start) {
//        tickerPrice.
        if (commodityPrice > maxPrice) {
            maxPrice = commodityPrice;
        }
    }

    // What if we haven't received any price yet? Do we return 0 or min value?
    public int getMaxCommodityPrice() {
        return priceToTime.isEmpty() ? Integer.MIN_VALUE : priceToTime.lastKey();
    }
}