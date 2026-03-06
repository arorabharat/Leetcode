import java.util.ArrayList;
import java.util.List;

public class Solution_52 {


    /*
    [[1,2,3,4],
    [5,6,7,8],
    [9,10,11,12],
    [13,14,15,16],
    [17,18,19,20],
    [21,22,23,24]]
     */
    class Solution1 {

        // right -> down -> left -> up
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int X;
        int Y;
        boolean[][] visited;
        int direction = 0;

        // TC : O(N)
        // SC : O(N)
        void dfs(int[][] matrix, int x, int y, List<Integer> results) {
            if (!visited[x][y]) {
                visited[x][y] = true;
                results.add(matrix[x][y]);
                int prevDirection = direction;
                do {
                    int nx = x + dx[direction];
                    int ny = y + dy[direction];
                    if (0 <= nx && nx < this.X && 0 <= ny && ny < this.Y && !visited[nx][ny]) {
                        dfs(matrix, nx, ny, results);
                    } else {
                        direction = (direction + 1) % 4;
                    }
                } while (prevDirection != direction);
            }
        }

        public List<Integer> spiralOrder(int[][] matrix) {
            this.X = matrix.length;
            if (X == 0) {
                return new ArrayList<>();
            }
            this.Y = matrix[0].length;
            this.visited = new boolean[this.X][this.Y];
            List<Integer> results = new ArrayList<>();
            dfs(matrix, 0, 0, results);
            return results;
        }
    }
}
