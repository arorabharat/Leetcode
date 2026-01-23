package com.atlassian.votes;


import java.util.*;

public class VoteCounter {

    /**
     * Candidates - name, uuid
     * Ballot - List<uuid> in order of preference, min : 1, max 3
     * <p>
     * - given a list of ballots
     * - return ranking of all candidates
     * - 1st pos = 3 points
     * - 2nd pos = 2 pts
     * - 3rd pos = 1 pt
     * - Ordering is governed by:
     * - total pts
     * - lexicographic earlier wins
     * <p>
     * List<Ballot> ballots
     * Set<Candidate> candidates
     * Map<Candidate, Integer> candidate points;
     * List<Candidate> candidates
     * <p>
     * List<Candidate> getResult(ballots)
     * - for each ballot
     * - for each candidate
     * - if pos1 add 3 to count, 2nd pos gets 2, 3rd gets 1
     * - keep track of point in a map of candidate to point.
     * - Have the candidates array sorted by the total points, and if tie then through lexicographically
     *
     */

    static class Candidate {
        String name;
        String uuid;

        Candidate(String name) {
            this.name = name;
            this.uuid = UUID.randomUUID().toString();
        }

    }

    // Ballot should not have duplicate names
    static class Ballot {
        final List<String> orderedVote;

        Ballot(String... votes) {
            if (votes.length > 3) {
                throw new RuntimeException("Invalid vote count");
            }
            orderedVote = List.of(votes);
        }

        List<String> getVote() {
            return orderedVote;
        }

    }

    List<Candidate> candidates;
    Map<String, Candidate> uuidCandidateMap;
    Map<String, Integer> voteCountMap;

    VoteCounter(List<Candidate> candidates) {
        if (candidates == null) {
            throw new RuntimeException("candidate should not be null");
        }
        this.candidates = new ArrayList<>(candidates);

        uuidCandidateMap = new HashMap<>();
        candidates.stream().forEach(c -> uuidCandidateMap.put(c.uuid, c));

        voteCountMap = new HashMap<>();
        candidates.stream().forEach(c -> voteCountMap.put(c.uuid, 0));
    }

    List<String> getResults(List<Ballot> ballots) {
        /**
         * 1. for each ballot update candidate point
         * 2. then sort array based on points, second on string comparison
         */

        if (ballots == null) {
            throw new RuntimeException("no ballot given");
        }

        for (var ballot : ballots) {
            List<String> voteCandidates = ballot.getVote();
            for (int i = 0; i < voteCandidates.size(); i++) {
                String candidateUuid = voteCandidates.get(i);

                switch (i) {
                    case 0:
                        voteCountMap.compute(candidateUuid, (k, v) -> v != null ? v += 3 : 3);
                        break;
                    case 1:
                        voteCountMap.compute(candidateUuid, (k, v) -> v != null ? v += 2 : 2);
                        break;
                    case 2:
                        voteCountMap.compute(candidateUuid, (k, v) -> v != null ? v += 1 : 1);
                        break;
                }
            }
        }


        candidates.sort((c1, c2) -> {
            int diff = voteCountMap.get(c1.uuid) - voteCountMap.get(c2.uuid);
            if (diff == 0) {
                return c1.name.compareTo(c2.name);
            }
            return -1 * diff;
        });
        return candidates.stream().map(c -> c.name).toList();
    }

    public static void main(String[] args) {
        Candidate c1 = new Candidate("c9");
        Candidate c2 = new Candidate("c2");
        Candidate c3 = new Candidate("c3");
        Candidate c4 = new Candidate("c4");
        VoteCounter voteCounter = new VoteCounter(List.of(c1, c2, c3, c4));

        Ballot b1 = new Ballot(c3.uuid, c2.uuid);
        Ballot b2 = new Ballot(c3.uuid, c1.uuid);
        Ballot b3 = new Ballot(c3.uuid, c1.uuid);

        System.out.println(voteCounter.getResults(List.of(b1, b2, b3)));
    }

}
