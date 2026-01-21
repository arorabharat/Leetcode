package com.atlassian.ratingservice;

import java.util.*;

public class Main2 {

    record Agent(String id, String name) {
    }

    record Rating(double value) {
    }

    record AgentRatingOutputBO(Agent agent, Rating rating) {
    }

    record AgentRatingInputBO(String agentId, int rating) {
    }

    record AgentStats(long total, int count) {
        double getAverageRating() {
            return count == 0 ? 0 : (double) total / count;
        }
    }

    static class RatingService {

        private final Map<String, AgentStats> agentStatsMap = new HashMap<>();
        private final Map<String, Agent> agentsMap = new HashMap<>();

        void addRating(AgentRatingInputBO agentRatingInputBO) {
            int rating = agentRatingInputBO.rating();
            String agentId = agentRatingInputBO.agentId();
            if (!agentsMap.containsKey(agentId)) {
                throw new RuntimeException("Agent Does not exist");
            }
            AgentStats existingStat = agentStatsMap.get(agentId);
            AgentStats newStat;
            newStat = (existingStat == null) ?
                    new AgentStats(rating, 1) :
                    new AgentStats(existingStat.total() + rating, existingStat.count() + 1);
            agentStatsMap.put(agentId, newStat);
        }

        String addAgent(String agentName) {
            String id = UUID.randomUUID().toString();
            agentsMap.put(id, new Agent(id, agentName));
            return id;
        }


        List<AgentRatingOutputBO> getAgentsSortedByAverageRating() {
            List<AgentRatingOutputBO> outputBOList = new ArrayList<>();
            agentStatsMap.forEach((id, value) -> {
                Agent agent = agentsMap.get(id);
                double averageRating = value.getAverageRating();
                AgentRatingOutputBO agentRatingOutputBO = new AgentRatingOutputBO(agent, new Rating(averageRating));
                outputBOList.add(agentRatingOutputBO);
            });
            outputBOList.sort(Comparator.comparingDouble((AgentRatingOutputBO a) -> a.rating.value).reversed());
            return outputBOList;
        }
    }


    public static void main(String[] args) {
        RatingService ratingService = new RatingService();

        String a1 = ratingService.addAgent("Shinchan");
        String a2 = ratingService.addAgent("Doremon");
        String a3 = ratingService.addAgent("Sen");
        String a4 = ratingService.addAgent("Sen");

        ratingService.addRating(new AgentRatingInputBO(a1, 3));
        ratingService.addRating(new AgentRatingInputBO(a1, 4));
        ratingService.addRating(new AgentRatingInputBO(a1, 5));

        ratingService.addRating(new AgentRatingInputBO(a2, 1));
        ratingService.addRating(new AgentRatingInputBO(a2, 2));
        ratingService.addRating(new AgentRatingInputBO(a2, 3));

        ratingService.addRating(new AgentRatingInputBO(a3, 5));
        ratingService.addRating(new AgentRatingInputBO(a3, 5));
        ratingService.addRating(new AgentRatingInputBO(a3, 5));

        ratingService.addRating(new AgentRatingInputBO(a4, 0));
        ratingService.addRating(new AgentRatingInputBO(a4, 1));
        ratingService.addRating(new AgentRatingInputBO(a4, 2));

        List<AgentRatingOutputBO> agentsSortedByAverageRating = ratingService.getAgentsSortedByAverageRating();

        for (AgentRatingOutputBO outputBO : agentsSortedByAverageRating) {
            System.out.println(outputBO);
        }
    }
}
