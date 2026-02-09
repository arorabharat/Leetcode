package com.atlassian.lld.survey;


import java.time.Month;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/**
 * Domain Models & DTOs
 */

record AgentPerformance(String agentId, String agentName, double averageScore, long totalRatings) {
}

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
interface RatingService {

    void submitRating(String agentId, double score);

    void submitRating(String agentId, double score, YearMonth month);

    List<AgentPerformance> getLeaderboard();

    List<AgentPerformance> getLeaderboard(YearMonth month);
}

interface ReportSorter {
    Comparator<AgentPerformance> get();
}

class ReverseSortByAverageReportSorter implements ReportSorter {

    @Override
    public Comparator<AgentPerformance> get() {
        return Comparator.comparingDouble(AgentPerformance::averageScore).reversed()
                .thenComparing(Comparator.comparingLong(AgentPerformance::totalRatings).reversed()
                        .thenComparing(AgentPerformance::agentName));
    }
}

class RatingServiceImpl implements RatingService {

    private final RatingValidator validator;
    private final ReportSorter comparator;
    private final AgentRegistry agentRegistry;

    private final Map<String, RatingStats> agent2RatingStats = new ConcurrentHashMap<>();

    public RatingServiceImpl(RatingValidator validator, AgentRegistry agentRegistry, ReportSorter comparator) {
        this.validator = validator;
        this.agentRegistry = agentRegistry;
        this.comparator = comparator;
    }

    @Override
    public void submitRating(String agentId, double score) {
        validator.validate(score);
        agentRegistry.findByIdOrThrow(agentId); // Validation: Agent must exist
        agent2RatingStats.computeIfAbsent(agentId, k -> new RatingStats()).record(score);
    }

    @Override
    public void submitRating(String agentId, double score, YearMonth mont) {

    }

    @Override
    public List<AgentPerformance> getLeaderboard() {
        return agentRegistry.findAll().stream()
                .map(this::mapToPerformance)
                .sorted(comparator.get())
                .toList();
    }

    @Override
    public List<AgentPerformance> getLeaderboard(YearMonth month) {
        return List.of();
    }

    private AgentPerformance mapToPerformance(Agent agent) {
        RatingStats stats = agent2RatingStats.getOrDefault(agent.id(), new RatingStats());
        return new AgentPerformance(
                agent.id(),
                agent.name(),
                stats.calculateAverage(),
                stats.getCount()
        );
    }
}

class RatingServiceTimeImpl implements RatingService {

    private final RatingValidator validator;
    private final ReportSorter comparator;
    private final AgentRegistry agentRegistry;

    private final Map<String, Map<YearMonth, RatingStats>> agent2MonthlyRatingStats = new ConcurrentHashMap<>();

    public RatingServiceTimeImpl(RatingValidator validator, AgentRegistry agentRegistry, ReportSorter comparator) {
        this.validator = validator;
        this.agentRegistry = agentRegistry;
        this.comparator = comparator;
    }

    @Override
    public void submitRating(String agentId, double score) {

    }

    @Override
    public void submitRating(String agentId, double score, YearMonth month) {
        validator.validate(score);
        agentRegistry.findByIdOrThrow(agentId); // Validation: Agent must exist
        agent2MonthlyRatingStats.computeIfAbsent(agentId, k -> new ConcurrentHashMap<>());
        agent2MonthlyRatingStats.get(agentId).computeIfAbsent(month, k -> new RatingStats()).record(score);
    }

    @Override
    public List<AgentPerformance> getLeaderboard() {
        return agentRegistry.findAll().stream()
                .map(this::mapToPerformance)
                .sorted(comparator.get())
                .toList();
    }

    @Override
    public List<AgentPerformance> getLeaderboard(YearMonth month) {
        return agentRegistry.findAll().stream()
                .map(agent -> this.mapAgentToMonthlyPerformance(agent, month))
                .sorted(comparator.get())
                .toList();
    }

    private AgentPerformance mapAgentToMonthlyPerformance(Agent agent, YearMonth month) {
        Map<YearMonth, RatingStats> monthlyStats = agent2MonthlyRatingStats.get(agent.id());
        AgentPerformance zeroRatingEntry = new AgentPerformance(agent.id(), agent.name(), 0, 0);
        if (monthlyStats == null) {
            return zeroRatingEntry;
        }
        RatingStats ratingStats = monthlyStats.get(month);
        if (ratingStats == null) {
            return zeroRatingEntry;
        }
        return new AgentPerformance(
                agent.id(),
                agent.name(),
                ratingStats.calculateAverage(),
                ratingStats.getCount()
        );
    }

    private AgentPerformance mapToPerformance(Agent agent) {
        Map<YearMonth, RatingStats> monthlyStats = agent2MonthlyRatingStats.get(agent.id());
        if (monthlyStats == null) {
            return new AgentPerformance(
                    agent.id(),
                    agent.name(),
                    0,
                    0
            );
        }
        RatingStats allMonthRatingsStat = new RatingStats();
        monthlyStats.values().forEach(v -> allMonthRatingsStat.record(v.calculateAverage()));
        return new AgentPerformance(
                agent.id(),
                agent.name(),
                allMonthRatingsStat.calculateAverage(),
                allMonthRatingsStat.getCount()
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
        String a1 = registry.registerAgent("a1");
        String a2 = registry.registerAgent("a2");

        ReportSorter reportSorter = new ReverseSortByAverageReportSorter();
        RatingService ratingService = new RatingServiceImpl(new RangeRatingValidator(0, 5), registry, reportSorter);

        ratingService.submitRating(a1, 4.5);
        ratingService.submitRating(a1, 5.0);
        ratingService.submitRating(a2, 5.0);
        ratingService.submitRating(a2, 5.0);

        // Display Results
        System.out.println("--- Agent Leaderboard ---");
        ratingService.getLeaderboard().forEach(p ->
                System.out.printf("%-10s | Avg: %.2f | Ratings: %d%n", p.agentName(), p.averageScore(), p.totalRatings())
        );
    }
}