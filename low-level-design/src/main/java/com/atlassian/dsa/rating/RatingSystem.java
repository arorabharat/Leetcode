package com.atlassian.dsa.rating;

import java.util.*;

public class RatingSystem {

    static class RatingService {

        static class Agent {

            private final String name;
            private final double avgRating;

            public Agent(String name, double avgRating) {
                this.name = name;
                this.avgRating = avgRating;
            }

            public String getName() {
                return name;
            }

            public double getAvgRating() {
                return avgRating;
            }

            @Override
            public String toString() {
                return "Agent{" +
                        "name='" + name + '\'' +
                        ", avgRating=" + avgRating +
                        '}';
            }
        }

        static class Rating implements Comparable<Rating> {

            private int totalRatings;
            private int ratingCount;

            public Rating() {
                this.ratingCount = 0;
                this.totalRatings = 0;
            }

            public int getTotalRatings() {
                return this.totalRatings;
            }

            public int getRatingCount() {
                return this.ratingCount;
            }

            boolean isValidRating(int rating) {
                return 0 <= rating && rating <= 5;
            }

            void addRating(int rating) {
                if (!isValidRating(rating)) {
                    throw new RuntimeException("Invalid Rating");
                }
                this.totalRatings += rating;
                this.ratingCount++;
            }

            double averageRating() {
                return (double) totalRatings / ratingCount;
            }

            @Override
            public int compareTo(Rating o) {
                return Double.compare(this.averageRating(), o.averageRating());
            }
        }

        private final Map<String, Rating> agentRatingMap = new HashMap<>();
        private final TreeMap<Double, Set<String>> sortedRating = new TreeMap<>();
        private final Set<String> validAgents = new HashSet<>();

        public RatingService(List<String> agents) {
            this.validAgents.addAll(agents);
        }

        public void giveRating(String agentName, int rating) {
            if (!validAgents.contains(agentName)) {
                throw new RuntimeException("Invalid Agent");
            }
            Rating existingRating = agentRatingMap.get(agentName);
            if (existingRating == null) {
                Rating newRating = new Rating();
                newRating.addRating(rating);
                agentRatingMap.put(agentName, newRating);
                sortedRating.putIfAbsent(newRating.averageRating(), new HashSet<>());
                sortedRating.get(newRating.averageRating()).add(agentName);
            } else {
                sortedRating.get(existingRating.averageRating()).remove(agentName);
                if (sortedRating.get(existingRating.averageRating()).isEmpty()) {
                    sortedRating.remove(existingRating.averageRating());
                }
                existingRating.addRating(rating);
                sortedRating.putIfAbsent(existingRating.averageRating(), new HashSet<>());
                sortedRating.get(existingRating.averageRating()).add(agentName);
            }
        }

        public List<Agent> agentsSortedByRatingOnDemand() {
            Comparator<Map.Entry<String, Rating>> compareByAvgRating = new Comparator<Map.Entry<String, Rating>>() {
                @Override
                public int compare(Map.Entry<String, Rating> o1, Map.Entry<String, Rating> o2) {
                    Rating r1 = o1.getValue();
                    Rating r2 = o2.getValue();
                    return r1.compareTo(r2);
                }
            };
            return agentRatingMap.entrySet().stream().sorted(compareByAvgRating.reversed()).map(e -> new Agent(e.getKey(), e.getValue().averageRating())).toList();
        }

        public List<Agent> agentsSortedByRatingOnPreCompute() {
            List<Agent> sortedAgents = new ArrayList<>();
            for (Double rating : sortedRating.keySet()) {
                for (String name : sortedRating.get(rating)) {
                    sortedAgents.add(new Agent(name, rating));
                }
            }
            return sortedAgents;
        }

    }

    public static void main(String[] args) {

        RatingService ratingService = new RatingService(List.of("a1", "a2", "a3", "a4"));

        ratingService.giveRating("a1", 5);
        ratingService.giveRating("a1", 4);
        ratingService.giveRating("a1", 3);
        ratingService.giveRating("a1", 3);
        ratingService.giveRating("a1", 3);

        ratingService.giveRating("a2", 4);
        ratingService.giveRating("a2", 3);
        ratingService.giveRating("a2", 2);

        ratingService.giveRating("a3", 3);
        ratingService.giveRating("a3", 2);
        ratingService.giveRating("a3", 1);

        ratingService.giveRating("a4", 1);
        ratingService.giveRating("a4", 2);
        ratingService.giveRating("a4", 2);

        List<RatingService.Agent> sortedAgentsOnDemand = ratingService.agentsSortedByRatingOnDemand();
        List<RatingService.Agent> sortedAgentsPreCompute = ratingService.agentsSortedByRatingOnDemand();
        System.out.println(sortedAgentsOnDemand);
        System.out.println(sortedAgentsPreCompute);
    }
}
