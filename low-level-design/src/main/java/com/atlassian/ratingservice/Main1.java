package com.atlassian.ratingservice;

import java.util.*;

class Agent {

    private final String agentId;
    private final String name;

    public Agent(String agentId, String name) {
        this.agentId = agentId;
        this.name = name;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getName() {
        return name;
    }
}


class Rating {

    private final int value;

    public Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class AggregateRating {
    private long totalRating;
    private int numberOfRating;
    private Agent agent;

    public AggregateRating(Agent agent) {
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }

    public void addRating(long newRating) {
        this.totalRating = totalRating + newRating;
        incNumberOfRating();
    }

    private void incNumberOfRating() {
        this.numberOfRating = numberOfRating + 1;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public int getNumberOfRating() {
        return numberOfRating;
    }
}

class AgentRatingRepository {

    private final Map<String, AggregateRating> ratingDb;

    PriorityQueue<AggregateRating> sortedAgentRatings = new PriorityQueue<>((a, b) -> Double.compare(averageRating(b), averageRating(a)));

    private double averageRating(AggregateRating rating) {
        return (double) rating.getTotalRating() / rating.getNumberOfRating();
    }

    public AgentRatingRepository(Map<String, AggregateRating> ratingDb) {
        this.ratingDb = ratingDb;
    }

    void addRatingForAgent(Agent agent, Rating rating) {
        String agentId = agent.getAgentId();
        if (!ratingDb.containsKey(agentId)) {
            ratingDb.put(agentId, new AggregateRating(agent));
        } else {
            sortedAgentRatings.remove(ratingDb.get(agentId));
        }
        AggregateRating aggregateRating = ratingDb.get(agentId);
        aggregateRating.addRating(rating.getValue());
        sortedAgentRatings.add(aggregateRating);
    }

    AggregateRating getAggregateRating(Agent agent) {
        String agentId = agent.getAgentId();
        if (!ratingDb.containsKey(agentId)) {
            throw new RuntimeException("Agent not found");
        } else {
            return ratingDb.get(agentId);
        }
    }

    double getAverageRatingOfAgent(Agent agent) {
        String agentId = agent.getAgentId();
        if (!ratingDb.containsKey(agentId)) {
            throw new RuntimeException("Agent not found");
        } else {
            return averageRating(ratingDb.get(agentId));
        }
    }

    List<Agent> getAgentsSortedByAvgRating() {
        List<Agent> result = new ArrayList<>();
        sortedAgentRatings.forEach(rating -> result.add(rating.getAgent()));
        return result;
    }
}

class RatingHandler {



    private final AgentRatingRepository agentRatingRepository;


    public RatingHandler(AgentRatingRepository agentRatingRepository) {
        this.agentRatingRepository = agentRatingRepository;
    }

    void submitRating(Agent agent, Rating rating) {
        this.agentRatingRepository.addRatingForAgent(agent, rating);
    }

    double getAverageRating(Agent agent) {
        AggregateRating aggregateRating = this.agentRatingRepository.getAggregateRating(agent);
        return this.agentRatingRepository.getAverageRatingOfAgent(agent);
    }

    List<Agent> getSortedAgentByAvgRating() {
        return this.agentRatingRepository.getAgentsSortedByAvgRating();
    }
}


public class Main1 {

    public static void main(String[] args) {
        AgentRatingRepository agentRatingRepository = new AgentRatingRepository(new HashMap<>());
        RatingHandler ratingHandler = new RatingHandler(agentRatingRepository);
        Agent agent1 = new Agent("1", "mitsi");
        Agent agent2 = new Agent("2", "shinchan");
        Agent agent3 = new Agent("3", "nohra");
        ratingHandler.submitRating(agent1, new Rating(5));
        ratingHandler.submitRating(agent1, new Rating(3));
        ratingHandler.submitRating(agent1, new Rating(5));
        ratingHandler.submitRating(agent2, new Rating(5));
        ratingHandler.submitRating(agent3, new Rating(4));
        ratingHandler.submitRating(agent3, new Rating(5));
        System.out.println(ratingHandler.getAverageRating(agent1));
        System.out.println(ratingHandler.getAverageRating(agent2));
        System.out.println(ratingHandler.getAverageRating(agent3));
        for (Agent agent : ratingHandler.getSortedAgentByAvgRating()) {
            System.out.println(agent.getName());
        }
    }
}
