/**
 * https://leetcode.com/problems/longest-word-in-dictionary/
 *
 */
class Solution_720 {

    static class Node {

        private final Node[] childNodes;
        private final static int NUM_OF_CHILD = 26;
        private String word;

        Node() {
            childNodes = new Node[NUM_OF_CHILD];
        }

        public Node getChildNode(char value) {
            if (!Character.isAlphabetic(value)) return null;
            int idx = index(value);
            return childNodes[idx];
        }

        public Node[] getChildNodes() {
            return childNodes;
        }

        public Node addChildNode(char value) {
            if (!Character.isAlphabetic(value)) return null;
            int idx = index(value);
            if (childNodes[idx] != null) return childNodes[idx];
            childNodes[idx] = new Node();
            return childNodes[idx];
        }

        private int index(char value) {
            return value - 'a';
        }
    }

    static class Trie {

        private final Node root;

        Trie() {
            this.root = new Node();
        }

        public void insertString(String str) {
            Node curr = root;
            for (char c : str.toCharArray()) {
                curr = curr.addChildNode(c);
            }
            curr.word = str;
        }

        public boolean containsString(String str) {
            Node curr = root;
            for (char c : str.toCharArray()) {
                curr = curr.getChildNode(c);
                if (curr == null) return false;
            }
            return curr.word != null;
        }

        public String longestString() {
            String maxString = "";
            for (Node next : root.getChildNodes()) {
                String tempMax = dfs(next, maxString);
                if (maxString.length() < tempMax.length()) {
                    maxString = tempMax;
                }
            }
            return maxString;
        }

        private String dfs(Node node, String maxString) {
            if (node == null) return maxString;
            String word = node.word;
            if (word != null) {
                maxString = word;
                for (Node next : node.getChildNodes()) {
                    String tempMax = dfs(next, maxString);
                    if (maxString.length() < tempMax.length()) {
                        maxString = tempMax;
                    }
                }
            }
            return maxString;
        }
    }

    public String longestWord(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insertString(word);
        }
        return trie.longestString();
    }
}