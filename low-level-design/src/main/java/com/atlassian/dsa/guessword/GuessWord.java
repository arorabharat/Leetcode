package com.atlassian.dsa.guessword;

import javax.xml.stream.events.Characters;
import java.util.Objects;

public class GuessWord {


    String generateFeedback(String s, String w) {

        Objects.requireNonNull(s);
        Objects.requireNonNull(w);
        String alphaOnly = "^[a-zA-Z]+$";
        if(!s.matches(alphaOnly)) {
            throw new IllegalArgumentException("secret contains other than alphabet");
        }
        if(!w.matches(alphaOnly)) {
            throw new IllegalArgumentException("guess word contains other than alphabet");
        }
        s = s.toLowerCase();
        w = w.toLowerCase();

        if (w.length() != s.length()) {
            throw new IllegalArgumentException("Secret word and word length can not be different");
        }

        int n = s.length();
        char[] result = new char[n];
        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != w.charAt(i)) {
                freq[c - 'a']++;
            }
        }

        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
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
}
