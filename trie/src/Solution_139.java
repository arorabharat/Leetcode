import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/word-break/
 * TODO : Difficult
 */
class Solution_139 {


    class Solution {

        static class Node {

            private boolean isWordEnd;
            private final Map<Character, Node> children;

            public Node() {
                this.isWordEnd = false;
                this.children = new HashMap<>();
            }

            Node getChildAtCharOrInitialise(char c) {
                this.children.put(c, this.children.getOrDefault(c, new Node()));
                return this.children.get(c);
            }

            Node getChildAtChar(char c) {
                return this.children.get(c);
            }

            public void setWordEnd(boolean wordEnd) {
                this.isWordEnd = wordEnd;
            }

            public boolean isWordEnd() {
                return this.isWordEnd;
            }
        }

        static class Trie {

            private final Node root;

            public Trie() {
                root = new Node();
            }

            void insert(String string) {
                Node tr = root;
                for (char c : string.toCharArray()) {
                    tr = tr.getChildAtCharOrInitialise(c);
                }
                tr.setWordEnd(true);
            }

            public Node getRoot() {
                return root;
            }
        }

        private final Map<String, Boolean> isValidString = new HashMap<>();

        private boolean _wordBreak(String str, int s, int e, Trie trie) {
            if (e < s) {
                return true;
            }
            if (isValidString.containsKey(s + "," + e)) {
                return isValidString.get(s + "," + e);
            }
            Node tr = trie.getRoot();
            for (int i = s; i <= e; i++) {
                tr = tr.getChildAtChar(str.charAt(i));
                if (tr == null) {
                    isValidString.put(s + "," + e, false);
                    return false;
                }
                if (tr.isWordEnd() && _wordBreak(str, i + 1, e, trie)) {
                    isValidString.put(s + "," + e, true);
                    return true;
                }
            }
            isValidString.put(s + "," + e, false);
            return false;
        }

        public boolean wordBreak(String s, List<String> wordDict) {
            Trie trie = new Trie();
            for (String word : wordDict) {
                trie.insert(word);
            }
            return _wordBreak(s, 0, s.length() - 1, trie);
        }
    }


    private boolean _isBreakable(char[] c, TrieNode trieNode, int s, int e) {
        if (s == e) return true;
        for (int b = s + 1; b < e; b++) {
            boolean wordPresent = trieNode._isWordPresent(c, s, b);
            boolean rem = _isBreakable(c, trieNode, b, e);
            if (wordPresent && rem) return true;
        }
        return trieNode._isWordPresent(c, 0, s);
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        TrieNode trieNode = new TrieNode();
        for (String str : wordDict) {
            trieNode.insert(str.toCharArray(), 0);
        }
        return _isBreakable(s.toCharArray(), trieNode, 0, s.toCharArray().length);
    }

    static class TrieNode {

        char START_CHAR = '^';
        char END_CHAR = '$';

        Character val;
        Map<Character, TrieNode> map;

        public TrieNode(Character c) {
            this.val = c;
            this.map = new HashMap<>();
        }

        public TrieNode() {
            this.val = START_CHAR;
            this.map = new HashMap<>();
        }

        void insert(char[] c, int it) {
            if (it == c.length) {
                if (!map.containsKey(END_CHAR)) {
                    map.put(END_CHAR, new TrieNode(END_CHAR));
                }
                return;
            }
            if (!map.containsKey(c[it])) {
                map.put(c[it], new TrieNode(c[it]));
            }
            map.get(c[it]).insert(c, it + 1);
        }

        private boolean _isWordPresent(char[] c, int start, int end) {
            if (end == start) {
                return map.containsKey(END_CHAR);
            }
            if (map.containsKey(c[start])) {
                return map.get(c[start])._isWordPresent(c, start + 1, end);
            }
            return false;
        }

        boolean isWordPresent(char[] c) {
            return this._isWordPresent(c, 0, c.length);
        }
    }
}