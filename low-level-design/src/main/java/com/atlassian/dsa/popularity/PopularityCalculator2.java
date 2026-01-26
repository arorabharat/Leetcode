package com.atlassian.dsa.popularity;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class PopularityCalculator2 {

    interface MostPopular {
        void increasePopularity(Integer contentId);

        Integer mostPopular();

        void decreasePopularity(Integer contentId);
    }

    static class MostPopularImplementation implements MostPopular {

        // contentId -> popularity
        private final Map<Integer, Integer> popularity = new HashMap<>();

        // popularity -> set of contentIds
        private final Map<Integer, Set<Integer>> buckets = new HashMap<>();

        // track current maximum popularity
        private int maxPopularity = 0;

        @Override
        public void increasePopularity(Integer contentId) {
            int oldCount = popularity.getOrDefault(contentId, 0);
            int newCount = oldCount + 1;

            popularity.put(contentId, newCount);

            // remove from old bucket
            if (oldCount > 0) {
                removeFromBucket(oldCount, contentId);
            }

            // add to new bucket
            buckets
                    .computeIfAbsent(newCount, k -> new HashSet<>())
                    .add(contentId);

            // update max
            maxPopularity = Math.max(maxPopularity, newCount);

            printState();
        }

        @Override
        public void decreasePopularity(Integer contentId) {
            Integer oldCount = popularity.get(contentId);
            if (oldCount == null) {
                return;
            }

            removeFromBucket(oldCount, contentId);

            int newCount = oldCount - 1;

            if (newCount == 0) {
                popularity.remove(contentId);
            } else {
                popularity.put(contentId, newCount);
                buckets
                        .computeIfAbsent(newCount, k -> new HashSet<>())
                        .add(contentId);
            }

            // fix maxPopularity if needed
            if (oldCount == maxPopularity && !buckets.containsKey(oldCount)) {
                maxPopularity--;
            }

            printState();
        }

        @Override
        public Integer mostPopular() {
            if (maxPopularity == 0) {
                return null;
            }
            // return any one contentId from the max bucket
            return buckets.get(maxPopularity).iterator().next();
        }

        private void removeFromBucket(int count, Integer contentId) {
            Set<Integer> set = buckets.get(count);
            set.remove(contentId);
            if (set.isEmpty()) {
                buckets.remove(count);
            }
        }

        private void printState() {
            System.out.println("Buckets: " + buckets);
            System.out.println("MaxPopularity: " + maxPopularity);
            System.out.println();
        }
    }
}

