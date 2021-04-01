/**
 * https://leetcode.com/problems/dungeon-game/
 */
class Solution_174 {

    public int calculateMinimumHP(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[][] st = new int[m + 1][n + 1];
        int[][] ht = new int[m + 1][n + 1];

        for (int i = 2; i <= m; i++) {
            st[i][0] = Integer.MAX_VALUE;
        }
        for (int j = 2; j <= n; j++) {
            st[0][j] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int downHealth = ht[i - 1][j] + grid[i - 1][j - 1];
                int rightHealth = ht[i][j - 1] + grid[i - 1][j - 1];
                int downBoost = 0;
                int rightBoost = 0;
                if (downHealth < 1) {
                    downBoost = 1 - downHealth;
                    downHealth = 1;
                }
                if (rightHealth < 1) {
                    rightBoost = 1 - rightHealth;
                    rightHealth = 1;
                }
                int down = st[i - 1][j] == Integer.MAX_VALUE ? st[i - 1][j] : st[i - 1][j] + downBoost;
                int right = st[i][j - 1] == Integer.MAX_VALUE ? st[i][j - 1] : st[i][j - 1] + rightBoost;
                if (down < right) {
                    ht[i][j] = downHealth;
                    st[i][j] = down;
                } else {
                    ht[i][j] = rightHealth;
                    st[i][j] = right;
                }
                System.out.print(grid[i - 1][j - 1] + " " + st[i][j] + " " + ht[i][j] + " , ");
            }
            System.out.println();
        }
        return Math.max(1, st[m][n]);
    }
}