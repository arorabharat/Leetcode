package com.atlassian.votes;

import java.util.*;

public class VoterCounter {


    static class Candidate {

        private final String name;
        private final String id;

        public Candidate(String name) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Candidate{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    static class Ballot {

        private final List<Candidate> candidates;

        public Ballot(List<Candidate> candidates) {
            if (candidates.size() > 3) {
                throw new RuntimeException("Invalid candidate count");
            }
            this.candidates = candidates;
        }

        public List<Candidate> getCandidates() {
            return candidates;
        }
    }

    static class VoteCounterService {

        private final Map<String, Integer> scoreBoard = new HashMap<>();
        private final Map<String, Candidate> candidateMap = new HashMap<>();
        private final int[] points = {3, 2, 1};

        List<Candidate> getResults(List<Ballot> ballots) {

            // aggregate the score
            for (Ballot ballot : ballots) {
                List<Candidate> candidates = ballot.getCandidates();
                for (int i = 0; i < candidates.size(); i++) {
                    Candidate candidate = candidates.get(i);
                    String id = candidate.id;
                    scoreBoard.put(id, scoreBoard.getOrDefault(id, 0) + points[i]);
                    candidateMap.put(id, candidate);
                }
            }

            List<Candidate> candidates = new ArrayList<>();
            for (String id: candidateMap.keySet()) {
                candidates.add(candidateMap.get(id));
            }

            // sorting the candidate
            candidates.sort((a, b) -> {
                if (scoreBoard.get(a.getId()).equals(scoreBoard.get(b.getId()))) {
                    return a.getId().compareTo(b.getId());
                } else {
                    return Integer.compare(scoreBoard.get(b.getId()), scoreBoard.get(a.getId()));
                }
            });
            return candidates;
        }
    }

    public static void main(String[] args) {
        Candidate c1 = new Candidate("c1");
        Candidate c2 = new Candidate("c2");
        Candidate c3 = new Candidate("c3");
        Candidate c4 = new Candidate("c4");
        Candidate c5 = new Candidate("c5");
        Candidate c6 = new Candidate("c6");
        Candidate c7 = new Candidate("c7");

        Ballot b1 = new Ballot(List.of(c1, c2, c3));
        Ballot b2 = new Ballot(List.of(c2, c3, c4));
        Ballot b3 = new Ballot(List.of(c3, c4, c5));


        VoteCounterService counterService = new VoteCounterService();
        List<Candidate> results = counterService.getResults(List.of(b1, b2, b3));
        System.out.println(results.toString());

    }
}
