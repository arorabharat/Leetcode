import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 * TODO : implement Trie very imortant
 */
class Solution_208 {


    class TrieNode {
        Map<Character, TrieNode> trieNodeMap = new HashMap<>();
        boolean isLast;
    }

    class Trie {

        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                TrieNode next = curr.trieNodeMap.getOrDefault(c, new TrieNode());
                curr.trieNodeMap.put(c, next);
                curr = next;
            }
            curr.isLast = true;
        }

        public boolean search(String word) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                curr = curr.trieNodeMap.getOrDefault(c, null);
                if (curr == null) return false;
            }
            return curr.isLast;
        }

        public boolean startsWith(String prefix) {
            TrieNode curr = root;
            for (char c : prefix.toCharArray()) {
                curr = curr.trieNodeMap.getOrDefault(c, null);
                if (curr == null) return false;
            }
            return true;
        }
    }

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
}
