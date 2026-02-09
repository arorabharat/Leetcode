package com.atlassian.lld.survey;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * Domain Models & DTOs
 */

record AgentPerformance(String agentId, String agentName, double averageScore, long totalRatings) {}

record Agent(String id, String name) {
    public Agent(String name) {
        this(UUID.randomUUID().toString(), name);
    }
}

/**
 * Thread-safe accumulator for rating statistics.
 * Uses Adders to minimize contention in high-write scenarios.
 */
class RatingStats {
    private final DoubleAdder scoreSum = new DoubleAdder();
    private final LongAdder ratingCount = new LongAdder();

    void record(double score) {
        scoreSum.add(score);
        ratingCount.increment();
    }

    double calculateAverage() {
        long count = ratingCount.sum();
        return count == 0 ? 0.0 : scoreSum.sum() / count;
    }

    long getCount() {
        return ratingCount.sum();
    }
}

/**
 * Repository for Agent persistence
 */
class AgentRegistry {
    private final Map<String, Agent> storage = new ConcurrentHashMap<>();

    public String registerAgent(String name) {
        Agent agent = new Agent(name);
        storage.put(agent.id(), agent);
        return agent.id();
    }

    public Agent findByIdOrThrow(String id) {
        return Optional.ofNullable(storage.get(id))
                .orElseThrow(() -> new NoSuchElementException("Agent not found with ID: " + id));
    }

    public List<Agent> findAll() {
        return new ArrayList<>(storage.values());
    }
}

/**
 * Business Logic Layer
 */
interface SurveyService {
    void submitRating(String agentId, double score);
    List<AgentPerformance> getLeaderboard();
}

class SurveyServiceImpl implements SurveyService {
    private final RatingValidator validator;
    private final AgentRegistry agentRegistry;
    private final Map<String, RatingStats> agentStatsMap = new ConcurrentHashMap<>();

    public SurveyServiceImpl(RatingValidator validator, AgentRegistry agentRegistry) {
        this.validator = validator;
        this.agentRegistry = agentRegistry;
    }

    @Override
    public void submitRating(String agentId, double score) {
        validator.validate(score);
        agentRegistry.findByIdOrThrow(agentId); // Validation: Agent must exist

        agentStatsMap.computeIfAbsent(agentId, k -> new RatingStats())
                .record(score);
    }

    @Override
    public List<AgentPerformance> getLeaderboard() {
        return agentRegistry.findAll().stream()
                .map(this::mapToPerformance)
                // Primary Sort: Avg Score (Desc), Secondary Sort: Volume (Desc)
                .sorted(Comparator.comparingDouble(AgentPerformance::averageScore).reversed()
                        .thenComparing(Comparator.comparingLong(AgentPerformance::totalRatings).reversed()))
                .collect(Collectors.toList());
    }

    private AgentPerformance mapToPerformance(Agent agent) {
        RatingStats stats = agentStatsMap.getOrDefault(agent.id(), new RatingStats());
        return new AgentPerformance(
                agent.id(),
                agent.name(),
                stats.calculateAverage(),
                stats.getCount()
        );
    }
}

/**
 * Validation Logic
 */
interface RatingValidator {
    void validate(double score);
}

class RangeRatingValidator implements RatingValidator {
    private final double min;
    private final double max;

    public RangeRatingValidator(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void validate(double score) {
        if (score < min || score > max) {
            throw new IllegalArgumentException(String.format("Score %.1f is out of range [%.1f, %.1f]", score, min, max));
        }
    }
}

public class CustomerSatisfactionSurvey {
    public static void main(String[] args) {
        AgentRegistry registry = new AgentRegistry();
        SurveyService surveyService = new SurveyServiceImpl(new RangeRatingValidator(0, 5), registry);

        // Setup Data
        String alice = registry.registerAgent("Alice");
        String bob = registry.registerAgent("Bob");

        // Simulate Interactions
        surveyService.submitRating(alice, 4.5);
        surveyService.submitRating(alice, 5.0);
        surveyService.submitRating(bob, 5.0);

        // Display Results
        System.out.println("--- Agent Leaderboard ---");
        surveyService.getLeaderboard().forEach(p ->
                System.out.printf("%-10s | Avg: %.2f | Ratings: %d%n", p.agentName(), p.averageScore(), p.totalRatings())
        );
    }
}