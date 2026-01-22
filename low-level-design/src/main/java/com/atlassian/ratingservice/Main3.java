package com.atlassian.ratingservice;

import java.util.*;

public class Main3 {

    record Agent(String id, String name) {
    }

    record Rating(double value) {
    }

    record RatingStats(long total, int count, String agentId) {
        double average() {
            return count == 0 ? 0 : (double) total / count;
        }
    }

    record AgentRatingDTO(String agentName, double averageRating) {

    }

    record RatingValidator() {
        boolean isValidFivePointRating(int rating) {
            return 0 <= rating && rating <= 5;
        }
    }


    static class RatingService {

        private final Map<String, Agent> agentDb = new HashMap<>();
        private final Map<String, RatingStats> statsDb = new HashMap<>();
        private final RatingValidator ratingValidator = new RatingValidator();
        private final TreeSet<RatingStats> leaderBoard = new TreeSet<>(
                Comparator.comparingDouble(RatingStats::average)
                        .reversed()
                        .thenComparing(RatingStats::count)
        );

        public void addRating(String id, int rating) {
            if (!agentDb.containsKey(id)) {
                throw new RuntimeException("Agent does not exist");
            }
            if (!ratingValidator.isValidFivePointRating(rating)) {
                throw new RuntimeException("Invalid rating");
            }
            RatingStats prevStat = statsDb.get(id);

            RatingStats newStat = (prevStat == null) ?
                    new RatingStats(rating, 1, id) :
                    new RatingStats(prevStat.total + rating, prevStat.count + 1, id);
            statsDb.put(id, newStat);
            if(prevStat != null) {
                leaderBoard.remove(prevStat);
            }
            leaderBoard.add(newStat);
        }


        public String addAgent(String name) {
            String id = UUID.randomUUID().toString();
            agentDb.put(id, new Agent(id, name));
            return id;
        }

        public List<AgentRatingDTO> leaderBoard() {
            return statsDb.entrySet().stream()
                    .map((e) -> {
                        return new AgentRatingDTO(agentDb.get(e.getKey()).name, e.getValue().average());
                    })
                    .sorted(
                            Comparator.comparingDouble(AgentRatingDTO::averageRating).reversed().thenComparing(AgentRatingDTO::agentName)
                    ).toList();
        }

        public List<AgentRatingDTO> lazyLeaderBoard() {
            return leaderBoard.stream().map( it -> {
                String agentName = agentDb.get(it.agentId).name;
                return new AgentRatingDTO(agentName, it.average());
            }).toList();
        }
    }

    public static void main(String args[]) {
        RatingService ratingService = new RatingService();
        String a1 = ratingService.addAgent("bharat");
        String a2 = ratingService.addAgent("vivek");
        String a3 = ratingService.addAgent("vishesh");
        String a4 = ratingService.addAgent("karunesh");
        ratingService.addRating(a1, 0);
        ratingService.addRating(a1, 1);
        ratingService.addRating(a1, 2);

        ratingService.addRating(a2, 1);
        ratingService.addRating(a2, 2);
        ratingService.addRating(a2, 3);

        ratingService.addRating(a3, 2);
        ratingService.addRating(a3, 3);
        ratingService.addRating(a3, 4);

        ratingService.addRating(a4, 3);
        ratingService.addRating(a4, 4);
        ratingService.addRating(a4, 5);

        List<AgentRatingDTO> leaderBoard = ratingService.leaderBoard();
        for (AgentRatingDTO dto : leaderBoard) {
            System.out.println(dto);
        }
        List<AgentRatingDTO> leaderBoard2 = ratingService.lazyLeaderBoard();
        for (AgentRatingDTO dto : leaderBoard2) {
            System.out.println(dto);
        }
    }
}
