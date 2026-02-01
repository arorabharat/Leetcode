package com.atlassian.dsa.rating;

import java.util.*;
import java.util.function.Function;

public class RatingSystem2 {

    public static class RatingService {

        // Output DTO
        public record Agent(String name, double avg) {
        }

        // Internal state
        static class Score {
            int sum = 0;
            int count = 0;

            void add(int r) {
                if (r < 0 || r > 5) throw new IllegalArgumentException("Invalid rating");
                sum += r;
                count++;
            }

            double avg() {
                return count == 0 ? 0.0 : (double) sum / count;
            }
        }

        private final Set<String> agents;
        private final Map<String, Score> scores = new HashMap<>();

        // avg * 1000 -> agents (stable integer key)
        private final TreeMap<Integer, Set<String>> leaderboard = new TreeMap<>();

        public RatingService(List<String> agents) {
            this.agents = new HashSet<>(agents);
        }

        public void rate(String agent, int rating) {
            if (!agents.contains(agent)) {
                throw new IllegalArgumentException("Unknown agent");
            }

            Score s = scores.computeIfAbsent(agent, k -> new Score());

            if (s.count > 0) {
                remove(agent, scaledAvg(s));
            }

            s.add(rating);
            add(agent, scaledAvg(s));
        }

        // ---- Queries ----

        // O(n log n) on demand
        public List<Agent> sortedOnDemand() {
            Function<Map.Entry<String, Score>, Agent> entryAgentFunction = e -> new Agent(e.getKey(), e.getValue().avg());
            List<Agent> agents = new ArrayList<>(scores.entrySet().stream()
                    .map(entryAgentFunction)
                    .toList());
            agents.sort(Comparator.comparingDouble(Agent::avg).reversed().thenComparing(Agent::name));
            return agents;
        }

        // O(n) read, O(log n) update
        public List<Agent> sortedPrecomputed() {
            List<Agent> result = new ArrayList<>();
            for (int avg : leaderboard.descendingKeySet()) {
                for (String name : leaderboard.get(avg)) {
                    result.add(new Agent(name, avg / 1000.0));
                }
            }
            return result;
        }

        // ---- Helpers ----

        private int scaledAvg(Score s) {
            return (int) (s.avg() * 1000);
        }

        private void add(String agent, int avg) {
            leaderboard.computeIfAbsent(avg, k -> new HashSet<>()).add(agent);
        }

        private void remove(String agent, int avg) {
            Set<String> set = leaderboard.get(avg);
            set.remove(agent);
            if (set.isEmpty()) leaderboard.remove(avg);
        }
    }

    // ---- Demo ----
    public static void main(String[] args) {
        RatingService rs = new RatingService(List.of("a1", "a2", "a3", "a4"));

        rs.rate("a1", 5);
        rs.rate("a1", 4);
        rs.rate("a1", 3);
        rs.rate("a2", 4);
        rs.rate("a2", 3);
        rs.rate("a3", 3);
        rs.rate("a3", 2);
        rs.rate("a4", 2);

        System.out.println(rs.sortedOnDemand());
        System.out.println(rs.sortedPrecomputed());
    }
}
