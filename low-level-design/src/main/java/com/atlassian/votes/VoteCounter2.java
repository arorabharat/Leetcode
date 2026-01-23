package com.atlassian.votes;

import java.util.*;

public class VoteCounter2 {


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
            return this.name;
        }
    }

    static class Ballot {

        private final List<Candidate> candidateList;

        public Ballot(List<Candidate> candidateList) {
            if (candidateList.size() > 3) {
                throw new RuntimeException("Invalid vote count.");
            }
            this.candidateList = candidateList;
        }

        public List<Candidate> getCandidateList() {
            return candidateList;
        }
    }

    static class VoteCounterService {

        private final Map<String, Long> scoredBoard = new HashMap<>();
        private final Map<String, Candidate> candidateDb = new HashMap<>();
        private final int[] points = {3, 2, 1};

        public List<String> getResults(List<Ballot> ballots) {

            // aggregated the scores
            for (Ballot ballot : ballots) {
                List<Candidate> candidates = ballot.getCandidateList();
                for (int i = 0; i < candidates.size(); i++) {
                    Candidate candidate = candidates.get(i);
                    String id = candidate.getId();
                    scoredBoard.put(id, scoredBoard.getOrDefault(id, 0L) + points[i]);
                    candidateDb.put(id, candidate);
                }
            }

            List<String> candidates = new ArrayList<>(scoredBoard.keySet());

            Comparator<String> candidateComparator = (c1, c2) -> {
                if (scoredBoard.get(c1) == scoredBoard.get(c2)) {
                    return c1.compareTo(c2);
                } else {
                    return Long.compare(scoredBoard.get(c2), scoredBoard.get(c1));
                }
            };

            candidates.sort(candidateComparator);
            return candidates;
        }

        void printScores() {
            scoredBoard.keySet().forEach(id ->
                    System.out.println(candidateDb.get(id) + " " + scoredBoard.get(id))
            );
        }

        List<Candidate> getCandidatesFromId(List<String> ids) {
            return ids.stream().map(candidateDb::get).toList();
        }

    }

    public static void main(String[] args) {

        Candidate c1 = new Candidate("c1");
        Candidate c2 = new Candidate("c2");
        Candidate c3 = new Candidate("c3");
        Candidate c4 = new Candidate("c4");
        Candidate c5 = new Candidate("c5");

        Ballot b1 = new Ballot(List.of(c1, c2, c3));
        Ballot b2 = new Ballot(List.of(c2, c3, c4));
        Ballot b3 = new Ballot(List.of(c3, c4, c5));

        VoteCounterService voteCounterService = new VoteCounterService();

        List<String> ids = voteCounterService.getResults(List.of(b1, b2, b3));
        List<Candidate> winners = voteCounterService.getCandidatesFromId(ids);
        System.out.println(winners);
        voteCounterService.printScores();
    }
}
