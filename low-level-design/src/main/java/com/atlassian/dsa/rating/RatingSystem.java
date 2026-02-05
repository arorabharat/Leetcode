package com.atlassian.dsa.rating;

import java.util.*;

public class RatingSystem {

    static class Agent {

        public final String id;
        public final String name;

        public Agent(String name) {
            this.name = name;
            this.id = UUID.randomUUID().toString();
        }
    }

    static class AgentRating {

        private final String name;
        private final double avgRating;

        public AgentRating(String name, double avgRating) {
            this.name = name;
            this.avgRating = avgRating;
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

    static class RatingService {

        private final Map<String, Rating> agent2Rating = new HashMap<>();
        private final Map<String, Agent> id2Agent = new HashMap<>();
        private final TreeMap<Double, Set<String>> rating2Agent = new TreeMap<>();
        private final Set<String> validAgents = new HashSet<>();

        public RatingService(List<String> agents) {
            this.validAgents.addAll(agents);
        }

        public void giveRating(String agent, int rating) {
            if (!validAgents.contains(agent)) {
                throw new RuntimeException("Invalid Agent");
            }
            Rating existingRating = agent2Rating.get(agent);
            if (existingRating == null) {
                Rating newRating = new Rating();
                newRating.addRating(rating);
                agent2Rating.put(agent, newRating);
                rating2Agent.putIfAbsent(newRating.averageRating(), new HashSet<>());
                rating2Agent.get(newRating.averageRating()).add(agent);
            } else {
                rating2Agent.get(existingRating.averageRating()).remove(agent);
                if (rating2Agent.get(existingRating.averageRating()).isEmpty()) {
                    rating2Agent.remove(existingRating.averageRating());
                }
                existingRating.addRating(rating);
                rating2Agent.putIfAbsent(existingRating.averageRating(), new HashSet<>());
                rating2Agent.get(existingRating.averageRating()).add(agent);
            }
        }

        public List<AgentRating> agentsSortedByRatingOnDemand() {
            Comparator<Map.Entry<String, Rating>> compareByAvgRating = new Comparator<Map.Entry<String, Rating>>() {
                @Override
                public int compare(Map.Entry<String, Rating> o1, Map.Entry<String, Rating> o2) {
                    Rating r1 = o1.getValue();
                    Rating r2 = o2.getValue();
                    return r1.compareTo(r2);
                }
            };
            return agent2Rating.entrySet().stream().sorted(compareByAvgRating.reversed()).map(e -> new AgentRating(e.getKey(), e.getValue().averageRating())).toList();
        }

        public List<AgentRating> agentsSortedByRatingOnPreCompute() {
            List<AgentRating> sortedAgentRatings = new ArrayList<>();
            for (Double rating : rating2Agent.keySet()) {
                for (String name : rating2Agent.get(rating)) {
                    sortedAgentRatings.add(new AgentRating(name, rating));
                }
            }
            return sortedAgentRatings;
        }

    }

    public static void main(String[] args) {

        List<Agent> agents =new ArrayList<>();
        agents.add(new Agent("a1"));
        agents.add(new Agent("a2"));
        agents.add(new Agent("a3"));
        agents.add(new Agent("a4"));

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

        List<AgentRating> sortedAgentsOnDemand = ratingService.agentsSortedByRatingOnDemand();
        List<AgentRating> sortedAgentsPreCompute = ratingService.agentsSortedByRatingOnDemand();
        System.out.println(sortedAgentsOnDemand);
        System.out.println(sortedAgentsPreCompute);
    }
}
