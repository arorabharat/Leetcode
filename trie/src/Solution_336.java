import java.util.*;

public class Solution_336 {

    // O(N^2*L) solution will give TLE
    class Solution {

        // there is way to optimize space consumption.
        // O(L)
        boolean isPalindrome(String w1, String w2) {
            String w = w1 + w2;
            int i = 0;
            int j = w.length() - 1;
            while (i < j) {
                if (w.charAt(i) != w.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }

        public List<List<Integer>> palindromePairs(String[] words) {
            int length = words.length;
            List<List<Integer>> pairList = new ArrayList<>();
            // O(N^2)
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (i != j && isPalindrome(words[i], words[j])) {
                        pairList.add(List.of(i, j));
                    }
                }
            }
            return pairList;
        }
    }

    class Solution2 {

        class Node {
            int wordEnd = -1;
            Map<Character, Node> children = new HashMap<>();
            List<Integer> palindromeIndexes = new ArrayList<>();
        }

        boolean isPalindrome(String w, int i) {
            if (i == w.length()) {
                return false;
            }
            int j = w.length() - 1;
            while (i < j) {
                if (w.charAt(i) != w.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }

        public List<List<Integer>> palindromePairs(String[] words) {
            int length = words.length;
            List<List<Integer>> pairList = new ArrayList<>();
            Node trie = new Node();
            // insert reverse word

            for (int i = 0; i < length; i++) {
                String word = words[i];
                String reverseWord = new StringBuilder(word).reverse().toString();
                Node tr = trie;
                for (int j = 0; j < reverseWord.length(); j++) {
                    char c = reverseWord.charAt(j);
                    if (!tr.children.containsKey(c)) {
                        tr.children.put(c, new Node());
                    }
                    tr = tr.children.get(c);
                    if (isPalindrome(reverseWord, j + 1)) {
                        tr.palindromeIndexes.add(i);
                    }
                }
                tr.wordEnd = i;
            }
            for (int i = 0; i < length; i++) {
                String word = words[i];
                Node tr = trie;
                int j = 0;
                while (j < word.length()) {
                    char c = word.charAt(j);
                    tr = tr.children.get(c);
                    if (tr == null) {
                        break;
                    }
                    // case 1 anad case 3
                    if (tr.wordEnd != -1 && isPalindrome(word, j + 1)) {
                        pairList.add(List.of(i, tr.wordEnd));
                    }
                    j++;
                }

                if (tr == null) {
                    continue;
                }
                if (tr.wordEnd != -1 && j == word.length() && i != tr.wordEnd) {
                    pairList.add(List.of(tr.wordEnd, i));
                }
                if (tr.wordEnd != -1) {
                    for (Integer v : tr.palindromeIndexes) {
                        pairList.add(List.of(i, v));
                    }
                }
            }
            return pairList;
        }
    }


    class Solution3 {

        class Node {
            int wordEnd = -1;
            Map<Character, Node> children = new HashMap<>();
            List<Integer> palindromeIndexes = new ArrayList<>();
        }

        private boolean isPalindrome(String w, int l) {
            int r = w.length() - 1;

            while (l < r) {
                if (w.charAt(l) != w.charAt(r)) {
                    return false;
                }
                l++;
                r--;
            }
            return true;
        }

        public List<List<Integer>> palindromePairs(String[] words) {

            Node root = new Node();

            // Step 1: insert reversed words into trie
            for (int i = 0; i < words.length; i++) {

                String reversed = new StringBuilder(words[i]).reverse().toString();

                Node node = root;

                for (int j = 0; j < reversed.length(); j++) {

                    // if remaining substring is palindrome
                    if (isPalindrome(reversed, j)) {
                        node.palindromeIndexes.add(i);
                    }

                    char c = reversed.charAt(j);

                    node.children.putIfAbsent(c, new Node());

                    node = node.children.get(c);
                }

                node.wordEnd = i;

                // full word is palindrome suffix
                node.palindromeIndexes.add(i);
            }


            // Step 2: search each word in trie
            List<List<Integer>> result = new ArrayList<>();

            for (int i = 0; i < words.length; i++) {

                Node node = root;
                String word = words[i];

                int j = 0;

                for (; j < word.length(); j++) {

                    // case 1:
                    // current trie node is end of another word
                    // remaining substring must be palindrome
                    if (node.wordEnd != -1 &&
                            node.wordEnd != i &&
                            isPalindrome(word, j)) {

                        result.add(List.of(i, node.wordEnd));
                    }

                    node = node.children.get(word.charAt(j));

                    if (node == null) {
                        break;
                    }
                }

                if (node == null) {
                    continue;
                }

                // case 2:
                // current word completely matched
                // check stored palindrome suffix words
                for (int idx : node.palindromeIndexes) {

                    if (idx != i) {
                        result.add(List.of(i, idx));
                    }
                }
            }

            return result;
        }
    }

    class Solution4 {

        static class Node {
            Node[] children = new Node[26];
            int wordIndex = -1;
        }

        void insert(Node trie, String word, int index) {
            Node tr = trie;
            for (char c : word.toCharArray()) {
                if (tr.children[c - 'a'] == null) {
                    tr.children[c - 'a'] = new Node();
                }
                tr = tr.children[c - 'a'];
            }
            tr.wordIndex = index;
        }

        boolean isPalindrome(List<Character> sbr) {
            int s = 0;
            int e = sbr.size() - 1;
            while (s <= e) {
                if (sbr.get(s) != sbr.get(e)) {
                    return false;
                }
                s++;
                e--;
            }
            return false;
        }

        void dfs(Node root, List<Character> sbr, List<Integer> results) {
            if (root.wordIndex != -1) {
                if (isPalindrome(sbr)) {
                    results.add(root.wordIndex);
                }
            }
            for (int i = 0; i < 26; i++) {
                Node child = root.children[i];
                if (child != null) {
                    sbr.addLast((char) (i + 'a'));
                    dfs(child, sbr, results);
                    sbr.removeLast();
                }
            }
        }

        List<Integer> find(Node trie, String word) {
            Node tr = trie;
            List<Integer> matchingIndex = new ArrayList<>();
            Node lastMatchedNode = tr;
            int s = 0;
            for (s = 0; s < word.length(); s++) {
                char c = word.charAt(s);
                if (tr.children[c - 'a'] == null) {
                    break;
                }
                lastMatchedNode = tr;
                tr = tr.children[c - 'a'];
            }

            if (lastMatchedNode.wordIndex != -1 && s == word.length()) {
                matchingIndex.add(tr.wordIndex);
            }

            if(s < word.length()) {
                List<Character> remString = new ArrayList<>();
                while (s < word.length()) {
                    remString.add(word.charAt(s));
                    s++;
                }
                if(isPalindrome(remString)) {
                    matchingIndex.add(tr.wordIndex);
                }
            }

            for (Node child : tr.children) {
                if (child != null) {
                    dfs(child, new ArrayList<>(), matchingIndex);
                }
            }
            return matchingIndex;
        }

        public List<List<Integer>> palindromePairs(String[] words) {

            Node trie = new Node();

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                insert(trie, word, i);
            }

            List<List<Integer>> palindromes = new ArrayList<>();

            for (int j = 0; j < words.length; j++) {
                String revWord = new StringBuilder(words[j]).reverse().toString();
                List<Integer> matchingIndex = find(trie, revWord);
                System.out.println(matchingIndex.toString() + " " + j + " " + revWord + " " + words[j]);
                for (int i : matchingIndex) {
                    if (i != j) {
                        palindromes.add(List.of(i, j));
                    }
                }
            }

            return palindromes;
        }
    }
}
