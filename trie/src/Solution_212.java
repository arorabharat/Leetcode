import java.util.ArrayList;
import java.util.List;

public class Solution_212 {

    class Solution {

        class Node {
            Node[] children = new Node[26];
            String word;
        }

        void insert(Node trie, String word) {
            Node it = trie;
            for (char c : word.toCharArray()) {
                if (it.children[c - 'a'] == null) {
                    it.children[c - 'a'] = new Node();
                }
                it = it.children[c - 'a'];
            }
            it.word = word;
        }

        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };

        void dfsWithBacktrack(char[][] board, int r, int c, Node it, List<String> matchingWords) {

            int R = board.length;
            int C = board[0].length;

            if (r >= R || r < 0 || c >= C || c < 0 || it == null) {
                return;
            }

            char val = board[r][c];
            if(val == '#') {
                return;
            }

            if (it.word != null) {
                matchingWords.add(it.word);
            }

            board[r][c] = '#';

            for (int d = 0; d < dr.length; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                dfsWithBacktrack(board, nr, nc, it.children[val - 'a'], matchingWords);
            }

            board[r][c] = val;
        }

        public List<String> findWords(char[][] board, String[] words) {

            Node trie = new Node();

            for (String word : words) {
                insert(trie, word);
            }

            List<String> matchingWords = new ArrayList<>();
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[r].length ; c++) {
                    dfsWithBacktrack(board, r, c, trie, matchingWords);
                }
            }

            return matchingWords;
        }
    }
}
