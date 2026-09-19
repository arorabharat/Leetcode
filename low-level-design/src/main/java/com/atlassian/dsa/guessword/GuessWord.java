package com.atlassian.dsa.guessword;

import java.util.*;

public class GuessWord {


    String generateFeedback(String s, String g) {
        validate(s);
        validate(g);
        s = s.toLowerCase();
        g = g.toLowerCase();

        if (g.length() != s.length()) {
            throw new IllegalArgumentException("Secret word and word length can not be different");
        }

        int n = s.length();
        char[] result = new char[n];
        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != g.charAt(i)) {
                freq[c - 'a']++;
            }
        }

        for (int i = 0; i < g.length(); i++) {
            char c = g.charAt(i);
            if (c == s.charAt(i)) {
                result[i] = 'G';
            } else if (freq[c - 'a'] > 0) {
                freq[c - 'a']--;
                result[i] = 'Y';
            } else {
                result[i] = 'X';
            }
        }

        return new String(result);
    }

    private static void validate(String s) {
        Objects.requireNonNull(s);
        String alphaOnly = "^[a-zA-Z]+$";
        if (!s.matches(alphaOnly)) {
            throw new IllegalArgumentException(s + "secret contains other than alphabet");
        }
    }

    private boolean align(String g, String ng, String feedback) {
        return generateFeedback(g,ng).equals(feedback);
    }

    private void filter(String g, Queue<String> possibleWords, String feedback) {
        int size = possibleWords.size();
        for (int i = 0; i < size; i++) {
            String ng = possibleWords.poll();
            if (align(g, ng, feedback)) {
                possibleWords.add(ng);
            }
        }
    }

    private boolean isMatched(String feedback) {
        for (char c : feedback.toCharArray()) {
            if (c != 'G') {
                return false;
            }
        }
        return true;
    }

    public String guessWord(String s, List<String> guessWords, int maxAttempt) {
        Queue<String> possibleWords = new LinkedList<>(guessWords);
        for (int i = 0; i < maxAttempt; i++) {
            if (possibleWords.isEmpty()) {
                return null;
            }
            String g = possibleWords.poll();
            String feedback = generateFeedback(s, g);
            if (isMatched(feedback)) {
                return g;
            }
            filter(g, possibleWords, feedback);
        }
        return "ATTEMPT_OVER";
    }
}
