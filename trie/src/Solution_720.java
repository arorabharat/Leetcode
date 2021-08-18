class Solution_720 {

    static class Node {
        private final char value;
        private final Node[] childNodes;
        private final static int NUM_OF_CHILD = 26;
        private boolean isLeafNode;

        Node(char value) {
            this.value = value;
            childNodes = new Node[NUM_OF_CHILD];
            this.isLeafNode = false;
        }

        public char getValue() {
            return value;
        }

        public boolean isLeafNode() {
            return isLeafNode;
        }

        public void setLeafNode() {
            isLeafNode = true;
        }

        public Node getChildNode(char value) {
            if (!Character.isAlphabetic(value)) return null;
            int idx = index(value);
            return childNodes[idx];
        }

        public Node addChildNode(char value) {
            if (!Character.isAlphabetic(value)) return null;
            int idx = index(value);
            if (childNodes[idx] != null) return childNodes[idx];
            childNodes[idx] = new Node(value);
            return childNodes[idx];
        }

        private int index(char value) {
            return value - 'a';
        }
    }

    static class Trie {

        private final Node root;

        Trie() {
            this.root = new Node(Character.MIN_VALUE);
        }

        public void insertString(String str) {
            Node curr = root;
            for (char c : str.toCharArray()) {
                curr = curr.addChildNode(c);
            }
            curr.setLeafNode();
        }

        public boolean containsString(String str) {
            Node curr = root;
            for (char c : str.toCharArray()) {
                curr = curr.getChildNode(c);
                if (curr == null) return false;
            }
            return curr.isLeafNode();
        }
    }

    public boolean doAllSubStringExist(String str, Trie trie) {
        for (int i = 0; i < str.length(); i++) {
            String subStr = str.substring(0, i + 1);
            if (!trie.containsString(subStr)) return false;
        }
        return true;
    }


    public String longestWord(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insertString(word);
        }
        String maxString = "";
        for (String word : words) {
            if (doAllSubStringExist(word, trie) && maxString.length() <= word.length()) {
                if (maxString.length() == word.length()) {
                    maxString = (word.compareTo(maxString) < 0) ? word : maxString;
                } else {
                    maxString = word;
                }
            }
        }
        return maxString;
    }
}