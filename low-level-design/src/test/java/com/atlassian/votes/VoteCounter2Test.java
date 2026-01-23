package com.atlassian.votes;

import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VoteCounter2Test {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @Test
    public void testScores() {
        VoteCounter2.Candidate c1 = new VoteCounter2.Candidate("c1");
        VoteCounter2.Candidate c2 = new VoteCounter2.Candidate("c2");
        VoteCounter2.Candidate c3 = new VoteCounter2.Candidate("c3");
        VoteCounter2.Candidate c4 = new VoteCounter2.Candidate("c4");
        VoteCounter2.Candidate c5 = new VoteCounter2.Candidate("c5");

        VoteCounter2.Ballot b1 = new VoteCounter2.Ballot(List.of(c1, c2, c3));
        VoteCounter2.Ballot b2 = new VoteCounter2.Ballot(List.of(c2, c3, c4));
        VoteCounter2.Ballot b3 = new VoteCounter2.Ballot(List.of(c3, c4, c5));

        VoteCounter2.VoteCounterService voteCounterService = new VoteCounter2.VoteCounterService();

        List<VoteCounter2.Candidate> results = voteCounterService.getResults(List.of(b1, b2, b3));
        assertEquals(5, results.size());
        assertEquals(c3, results.get(0));
        assertEquals(c2, results.get(1));
    }

}