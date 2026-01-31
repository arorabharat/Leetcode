import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/design-file-system/description/?envType=company&envId=atlassian&favoriteSlug=atlassian-all
 */
public class Solution_1166 {

    // approach 1
    class FileSystem {

        static class Trie {

            Map<String, Trie> nextNode;
            boolean isEnd;
            int val;
            String word;

            public Trie(String word, int val) {
                this.isEnd = true;
                this.val = val;
                this.word = word;
                nextNode = new HashMap<>();
            }

            Trie getNextNode(String word) {
                return nextNode.get(word);
            }

            boolean addChildNode(String word, int val) {
                Trie childNode = this.nextNode.get(word);
                if (childNode != null) {
                    if (childNode.isEnd) {
                        return false;
                    } else {
                        childNode.isEnd = true;
                        childNode.val = val;
                        return true;
                    }
                } else {
                    this.nextNode.put(word, new Trie(word, val));
                    return true;
                }
            }
        }

        private final Trie root = new Trie("", -1);

        public FileSystem() {

        }

        public boolean createPath(String path, int value) {
            String[] split = path.split("/");
            int n = split.length;
            if (n == 0) return false;
            Trie it = root;
            for (int i = 1; i < n - 1; i++) {
                it = it.getNextNode(split[i]);
                if (it == null) {
                    return false;
                }
            }
            return it.addChildNode(split[n - 1], value);
        }

        public int get(String path) {
            String[] split = path.split("/");
            int n = split.length;
            if (n == 0) return -1;
            Trie it = root;
            for (int i = 1; i < n; i++) {
                it = it.getNextNode(split[i]);
                if (it == null) {
                    return -1;
                }
            }
            return it.val;
        }
    }

    class FileSystem2 {

        HashMap<String, Integer> paths;

        public FileSystem2() {
            this.paths = new HashMap<String, Integer>();
        }

        public boolean createPath(String path, int value) {

            // Step-1: basic path validations
            if (path.isEmpty() || (path.length() == 1 && path.equals("/")) || this.paths.containsKey(path)) {
                return false;
            }

            int delimIndex = path.lastIndexOf("/");
            String parent = path.substring(0, delimIndex);

            // Step-2: if the parent doesn't exist. Note that "/" is a valid parent.
            if (parent.length() > 1 && !this.paths.containsKey(parent)) {
                return false;
            }

            // Step-3: add this new path and return true.
            this.paths.put(path, value);
            return true;
        }

        public int get(String path) {
            return this.paths.getOrDefault(path, -1);
        }
    }

}
