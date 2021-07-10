import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/word-break/
 * TODO : Difficult
 */
class Solution_139 {


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

    private boolean _isBreakable(char[] c, TrieNode trieNode, int s, int e) {
        if (s == e) return true;
        for (int b = s+1; b < e; b++) {
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
}