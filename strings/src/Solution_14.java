import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * https://leetcode.com/problems/longest-common-prefix/
 *
 * @link binary-tree.Solution_606
 * @see Solution_821
 * @see Solution_942
 */
class Solution_14 {

    public String longestCommonPrefix(String[] strs) {
        Trie trie = new Trie();
        for (String str : strs) {
            trie.addString(str);
        }
        Map<Character, Node> dictionary = trie.root.children;
        StringBuilder stringBuilder = new StringBuilder();
        while (dictionary.size() == 1) {
            Optional<Node> first = dictionary.values().stream().findFirst();
            char c = first.get().c;
            if (c == '$') break;
            stringBuilder.append(c);
            dictionary = first.get().children;
        }
        return stringBuilder.toString();
    }

    class Node {

        public final char c;
        private final Map<Character, Node> children;

        Node(char c) {
            this.c = c;
            children = new HashMap<>();
        }

        public Optional<Node> getNodeWithValue(char c) {
            return Optional.ofNullable(children.get(c));
        }

        public Node addChildren(char c) {
            Optional<Node> node = this.getNodeWithValue(c);
            if (node.isEmpty()) {
                Node newNode = new Node(c);
                this.children.put(c, newNode);
                return newNode;
            } else {
                return node.get();
            }
        }
    }

    class Trie {
        private final Node root;

        Trie() {
            this.root = new Node('^');
        }

        void addString(String str) {
            Node curr = this.root;
            for (char c : str.toCharArray()) {
                curr = curr.addChildren(c);
            }
            curr.addChildren('$');
        }
    }

    private static int getMinSizeString(String[] strs) {
        int minSize = Integer.MAX_VALUE;
        for (String str : strs) {
            if (str.length() < minSize) {
                minSize = str.length();
            }
        }
        return minSize;
    }

    private boolean isMatching(String[] strs, int i) {
        char c = strs[0].charAt(i);
        for (String str : strs) {
            if (str.charAt(i) != c) return false;
        }
        return true;
    }

    public String longestCommonPrefix4(String[] strs) {
        if (strs.length == 0) return "";
        int minSize = getMinSizeString(strs);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < minSize; i++) {
            if (!isMatching(strs, i)) {
                break;
            }
            stringBuilder.append(strs[0].charAt(i));
        }
        return stringBuilder.toString();
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1) return strs[0];
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen, str.length());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < minLen; i++) {
            char c = strs[0].charAt(i);
            boolean matched = true;
            for (int j = 1; j < strs.length; j++) {
                if (c != strs[j].charAt(i)) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                sb.append(c);
            } else {
                break;
            }

        }
        return sb.toString();
    }
}