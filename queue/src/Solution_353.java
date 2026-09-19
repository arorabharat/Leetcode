import java.util.*;

public class Solution_353 {
    class SnakeGame1 {

        record Pair(int r, int c) {

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Pair pair)) return false;
                return r() == pair.r() && c() == pair.c();
            }

            @Override
            public int hashCode() {
                return Objects.hash(r(), c());
            }

            @Override
            public String toString() {
                return "Pair{" +
                        "r=" + r +
                        ", c=" + c +
                        '}';
            }
        }

        private final Set<Pair> snakePositionsSet = new HashSet<>();
        private final Deque<Pair> snakePositionsQueue = new LinkedList<>();
        private final int C;
        private final int R;
        private int foodIndex;
        private final int[][] food;

        int[] dr = {0,0,1,-1};
        int[] dc = {1,-1,0,0};


        public SnakeGame1(int C, int R, int[][] food) {
            this.C = C;
            this.R = R;
            this.foodIndex = 0;
            this.food = food;
            Pair start = new Pair(0, 0);
            this.snakePositionsQueue.add(start);
            this.snakePositionsSet.add(start);
        }

        int getIndex(String direction) {
            return switch (direction) {
                case "R" -> 0;
                case "L" -> 1;
                case "D" -> 2;
                case "U" -> 3;
                default -> -1;
            };
        }

        boolean isValid(Pair l) {
            return 0 <= l.r && l.r < this.R && 0 <= l.c && l.c < this.C;
        }

        boolean isFoodPosition(Pair l) {
            return this.foodIndex < food.length && food[foodIndex][0] == l.r && food[foodIndex][1] == l.c;
        }

        public int move(String direction) {
            Pair headPos = this.snakePositionsQueue.getFirst();
            Pair tailPos = this.snakePositionsQueue.getLast();
            int d = getIndex(direction);
            Pair nextHeadPos = new Pair(headPos.r + dr[d], headPos.c + dc[d]);
            if (!isValid(nextHeadPos)) {
                return -1;
            }
            if (!isFoodPosition(nextHeadPos)) {
                this.foodIndex++;
                this.snakePositionsSet.remove(tailPos);
            }
            if (this.snakePositionsSet.contains(nextHeadPos)) {
                return -1;
            }
            this.snakePositionsSet.add(nextHeadPos);
            this.snakePositionsQueue.addFirst(nextHeadPos);
            return this.snakePositionsQueue.size();
        }
    }

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
}
