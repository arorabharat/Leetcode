package com.atlassian.ratingsystem;


import java.util.*;

/**
 * What the problem really is
 * You are given:
 * • A list of ballots
 * • Each ballot ranks up to 3 candidates
 * • Points are assigned by position:
 * 1st choice → 3 points
 * 2nd choice → 2 points
 * 3rd choice → 1 point
 */

public class Main {


    record Ballot(Optional<String> firstRank, Optional<String> secondRank, Optional<String> thirdRank) {
    }

    record Stat(String name, long totalPoints) {
    }

    static class BallotService_1 {

        List<String> getResults(List<Ballot> ballots) {
            Map<String, Long> leaderBoard = new HashMap<>();
            for (Ballot ballot : ballots) {
                if (ballot.firstRank.isPresent()) {
                    String candidateName = ballot.firstRank.get();
                    leaderBoard.put(candidateName, leaderBoard.getOrDefault(candidateName, 0L) + 3);
                }
                if (ballot.secondRank.isPresent()) {
                    String candidateName = ballot.secondRank.get();
                    leaderBoard.put(candidateName, leaderBoard.getOrDefault(candidateName, 0L) + 2);
                }
                if (ballot.thirdRank.isPresent()) {
                    String candidateName = ballot.thirdRank.get();
                    leaderBoard.put(candidateName, leaderBoard.getOrDefault(candidateName, 0L) + 1);
                }
            }
            System.out.println(leaderBoard.toString());
            return leaderBoard.entrySet()
                    .stream()
                    .map( e -> new Stat(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparingLong(Stat::totalPoints).reversed().thenComparing(Stat::name))
                    .map(Stat::name).toList();
        }

    }

    public static void main(String[] args) {
        BallotService_1 ballotService1 = new BallotService_1();
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(new Ballot(Optional.of("bharat"), Optional.of("anukriti"), Optional.of("cheenu")));
        ballots.add(new Ballot(Optional.of("anukriti"), Optional.of("bharat"), Optional.of("cheenu")));
        ballots.add(new Ballot(Optional.of("anukriti"), Optional.of("bharat"), Optional.of("cheenu")));
        List<String> results = ballotService1.getResults(ballots);
        System.out.println(results.toString());
    }

    public class BallotService2 {


        record Ballot (List<String> candidates) {
        }

        public static List<String> getResults(List<Ballot> ballots) {
            Map<String, Integer> leaderBoard = new HashMap<>();

            int[] points = {3, 2, 1};

            // 1. Aggregate scores
            for (Ballot ballot : ballots) {
                List<String> candidates = ballot.candidates;
                for (int i = 0; i < candidates.size(); i++) {
                    String candidate = candidates.get(i);
                    leaderBoard.put(candidate, leaderBoard.getOrDefault(candidate, 0) + points[i]
                    );
                }
            }

            // 2. Sort candidates
            List<String> result = new ArrayList<>(leaderBoard.keySet());

            Comparator<String> stringComparator = (a, b) -> {
                int scoreCompare = Integer.compare(leaderBoard.get(b), leaderBoard.get(a));
                if (scoreCompare != 0) {
                    return scoreCompare; // higher score first
                }
                return a.compareTo(b); // tie-breaker
            };
            result.sort(stringComparator);
            return result;
        }
    }
}
