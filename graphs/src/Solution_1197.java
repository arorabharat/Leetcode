import java.util.*;

public class Solution_1197 {

    class Solution {

        class Loc {

            int x;
            int y;

            public Loc(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Loc loc)) return false;
                return x == loc.x && y == loc.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }
        }

        int[] dx = {-2, -2, 2, 2, -1, 1, -1, 1};
        int[] dy = {-1, 1, -1, 1, -2, -2, 2, 2};

        boolean inRange(Loc loc) {
            return -2 <= loc.x && loc.x <= 302 && -2 <= loc.y && loc.y <= 302;
        }

        int bfs(int x, int y, Set<Loc> visited) {
            Queue<Loc> currQ = new LinkedList<>();
            Loc start = new Loc(0, 0);
            currQ.add(start);
            visited.add(start);
            int distance = 0;
            while (!currQ.isEmpty()) {
                Queue<Loc> nextQ = new LinkedList<>();
                while (!currQ.isEmpty()) {
                    Loc curr = currQ.poll();
                    if (curr.x == x && curr.y == y) {
                        return distance;
                    }
                    for (int i = 0; i < dx.length; i++) {
                        Loc next = new Loc(curr.x + dx[i], curr.y + dy[i]);
                        if (inRange(next) && !visited.contains(next)) {
                            nextQ.add(next);
                            visited.add(next);
                        }
                    }
                }
                distance++;
                currQ = nextQ;
            }
            return -1;
        }

        public int minKnightMoves(int x, int y) {
            Set<Loc> visited = new HashSet<>();
            return bfs(x, y, visited);
        }

    }
}
