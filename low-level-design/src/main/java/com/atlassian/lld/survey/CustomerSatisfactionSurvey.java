package com.atlassian.lld.survey;


import java.util.*;

// immutable object
record Rating(double val) {
}

record AgentRatingDTO(String id, String name, Rating rating) {
}

record Agent(String id, String name) {
    public Agent(String name) {
        this(UUID.randomUUID().toString(), name);
    }
}

// mutable
class AggregateRating {

    private final String id;

    private double val;
    private int count;

    AggregateRating() {
        this.id = UUID.randomUUID().toString();
    }

    void aggregate(double rating) {
        this.val += rating;
        this.count++;
    }

    public String getId() {
        return id;
    }

    public double getVal() {
        return val;
    }

    public int getCount() {
        return count;
    }

    public double avg() {
        return count == 0 ? 0 : val / count;
    }
}

interface RatingService {
    void rateAgent(String agentId, double rating);

    List<AgentRatingDTO> getAllAgentRatings();
}

class AgentRepo {

    private final Map<String, Agent> agentDb = new HashMap<>();

    public String createAgent(String name) {
        Agent agent = new Agent(name);
        agentDb.put(agent.id(), agent);
        return agent.id();
    }

    public Optional<Agent> getAgent(String id) {
        if (agentDb.containsKey(id)) {
            return Optional.of(agentDb.get(id));
        } else {
            return Optional.empty();
        }
    }

    public List<Agent> getAllAgents() {
        return agentDb.values().stream().toList();
    }

    public boolean isValid(String id) {
        return agentDb.containsKey(id);
    }

}

interface RatingValidator {
    boolean isValid(double val);
}

class FivePointRatingValidator implements RatingValidator {

    @Override
    public boolean isValid(double val) {
        return 0 <= val && val <= 5;
    }
}

class RatingServiceImpl implements RatingService {

    private final RatingValidator ratingValidator;
    private final AgentRepo agentRepo;
    private final Map<String, AggregateRating> agent2Rating = new HashMap<>();


    public RatingServiceImpl(RatingValidator ratingValidator, AgentRepo agentRepo) {
        this.ratingValidator = ratingValidator;
        this.agentRepo = agentRepo;
    }

    @Override
    public void rateAgent(String agentId, double rating) {
        if (!ratingValidator.isValid(rating)) {
            throw new RuntimeException("Invalid Rating");
        }
        if (!agentRepo.isValid(agentId)) {
            throw new RuntimeException("Invalid Agent Id");
        }
        agent2Rating.computeIfAbsent(agentId, k -> new AggregateRating());
        agent2Rating.get(agentId).aggregate(rating);
    }

    @Override
    public List<AgentRatingDTO> getAllAgentRatings() {
        Comparator<Agent> agentComparator = new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                AggregateRating a1 = agent2Rating.getOrDefault(o1.id(), new AggregateRating());
                AggregateRating a2 = agent2Rating.getOrDefault(o2.id(), new AggregateRating());
                int compare = Double.compare(a2.avg(), a1.avg());
                if (compare == 0) {
                    return Integer.compare(a2.getCount(), a1.getCount());
                }
                return compare;
            }
        };
        return agentRepo.getAllAgents().stream().sorted(agentComparator).map(this::getAgentRatingDTO).toList();
    }

    private AgentRatingDTO getAgentRatingDTO(Agent agent) {
        return new AgentRatingDTO(agent.id(), agent.name(), getRating(agent));
    }

    private Rating getRating(Agent agent) {
        return new Rating(agent2Rating.getOrDefault(agent.id(), new AggregateRating()).avg());
    }
}

public class CustomerSatisfactionSurvey {

    public static void main(String[] args) {
        AgentRepo agentRepo = new AgentRepo();
        String a1 = agentRepo.createAgent("a1");
        String a2 = agentRepo.createAgent("a2");
        String a3 = agentRepo.createAgent("a3");
        RatingService ratingService = new RatingServiceImpl(new FivePointRatingValidator(), agentRepo);
        ratingService.rateAgent(a1, 1.0);
        ratingService.rateAgent(a1, 5.0);
        ratingService.rateAgent(a2, 5.0);
        ratingService.rateAgent(a3, 4.0);
        System.out.println(ratingService.getAllAgentRatings());
    }
}
