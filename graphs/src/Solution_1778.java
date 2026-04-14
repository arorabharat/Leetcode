import java.util.*;

public class Solution_1778 {

    class Solution {

        Set<String> visited;
        char[] directions = {'L', 'R', 'U', 'D'};
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        char[] revDirections = {'R', 'L', 'D', 'U'};

        // spiral order traversal
        int dfs(GridMaster master, int distance, List<Integer> index) {
            if (master.isTarget()) {
                return distance;
            }
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                char dir = directions[i];
                char revDir = this.revDirections[i];
                List<Integer> nextIndex = List.of(index.get(0) + dx[i], index.get(1) + dy[1]);
                if (master.canMove(dir) && visited.contains(nextIndex.toString())) {
                    master.move(dir);
                    int targetDistance = dfs(master, distance + 1, index);
                    master.move(revDir);
                    if (targetDistance < minDistance) {
                        minDistance = targetDistance;
                    }
                }
            }
            return minDistance;
        }

        public int findShortestPath(GridMaster master) {
            visited = new HashSet<>();
            int targetDistance = dfs(master, 0, List.of(0, 0));
            if (targetDistance == Integer.MAX_VALUE) {
                return -1;
            }
            return targetDistance;
        }
    }

    interface GridMaster {
        boolean canMove(char direction);

        void move(char direction);

        boolean isTarget();
    }

    class Solution1 {


        int[][] grid = new int[1001][1001];
        boolean[][] visited = new boolean[1001][1001];
        char[] dir = {'U', 'D', 'L', 'R'};
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        char[] revDir = {'D', 'U', 'R', 'L'};

        void buildGrid(GridMaster master, int r, int c) {
            if (master.isTarget()) {
                grid[r][c] = 2;
            } else {
                grid[r][c] = 1;
            }
            visited[r][c] = true;
            for (int i = 0; i < dir.length; i++) {
                if (master.canMove(dir[i]) && !visited[r + dr[i]][c + dc[i]]) {
                    master.move(dir[i]);
                    buildGrid(master, r + dr[i], c + dc[i]);
                    master.move(revDir[i]);
                }
            }
        }

        public int findShortestPath(GridMaster master) {
            if (master.isTarget()) {
                return 0;
            }
            buildGrid(master, 500, 500);
            Queue<int[]> q = new LinkedList<>();
            visited = new boolean[1001][1001];
            q.add(new int[]{500, 500});
            visited[500][500] = true;
            int distance = 0;
            while (!q.isEmpty()) {
                int qSize = q.size();
                for (int i = 0; i < qSize; i++) {
                    int[] cell = q.remove();
                    int r = cell[0];
                    int c = cell[1];
                    for (int k = 0; k < dir.length; k++) {
                        int nr = r + dr[k];
                        int nc = c + dc[k];
                        if (grid[nr][nc] == 1 && !visited[nr][nc]) {
                            q.add(new int[]{nr, nc});
                            visited[nr][nc] = true;
                        } else if (grid[nr][nc] == 2) {
                            return distance + 1;
                        }
                    }
                }
                distance++;
            }
            return -1;
        }
    }

    class Solution3 {

        class Pair {

            int r;
            int c;

            public Pair(int r, int c) {
                this.r = r;
                this.c = c;
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Pair pair))
                    return false;
                return r == pair.r && c == pair.c;
            }

            @Override
            public int hashCode() {
                return Objects.hash(r, c);
            }
        }

        class Graph {

            private final GridMaster master;

            private final Map<Pair, List<Pair>> adj;
            private Pair target;
            private final Pair start;

            private Set<Pair> visited;
            char[] dir = {'U', 'D', 'L', 'R'};
            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            char[] revDir = {'D', 'U', 'R', 'L'};

            public Graph(GridMaster master) {
                this.adj = new HashMap<>();
                this.master = master;
                this.visited = new HashSet<>();
                this.start = new Pair(0, 0);
            }

            int minDistance() {
                this.visited = new HashSet<>();
                dfs(start);
                this.visited = new HashSet<>();
                if (target == null) {
                    return -1;
                }
                Queue<Pair> q = new LinkedList<>();
                q.add(start);
                this.visited.add(start);
                int distance = 0;
                while (!q.isEmpty()) {
                    int qs = q.size();
                    for (int i = 0; i < qs; i++) {
                        Pair p = q.remove();
                        for (Pair nc : this.adj.getOrDefault(p, new ArrayList<>())) {
                            if (!visited.contains(nc)) {
                                if (nc.equals(target)) {
                                    return distance + 1;
                                } else {
                                    q.add(nc);
                                    this.visited.add(nc);
                                }
                            }
                        }
                    }
                    distance++;
                }
                return distance;
            }

            void dfs(Pair p) {
                this.visited.add(p);
                if (master.isTarget()) {
                    this.target = p;
                }
                for (int i = 0; i < dir.length; i++) {
                    int nr = p.r + dr[i];
                    int nc = p.c + dc[i];
                    Pair np = new Pair(nr, nc);
                    if (master.canMove(dir[i]) && !this.visited.contains(np)) {
                        this.adj.put(p, this.adj.getOrDefault(p, new ArrayList<>()));
                        this.adj.put(np, this.adj.getOrDefault(np, new ArrayList<>()));
                        this.adj.get(p).add(np);
                        this.adj.get(np).add(p);
                        master.move(dir[i]);
                        dfs(np);
                        master.move(revDir[i]);
                    }
                }
            }
        }

        public int findShortestPath(GridMaster master) {
            if (master.isTarget()) {
                return 0;
            }
            Graph graph = new Graph(master);
            return graph.minDistance();
        }
    }


    class Solution4 {

        class Cell {
            int r;
            int c;

            Cell(int r, int c) {
                this.r = r;
                this.c = c;
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Cell)) {
                    return false;
                }
                Cell c2 = (Cell) o;
                return c2.r == this.r && c2.r == this.c;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.r, this.c);
            }

        }

        private final Set<Cell> traversableCells = new HashSet<>();
        private Cell target;

        char[] dir = {'U', 'D', 'L', 'R'};
        char[] revDir = {'D', 'U', 'R', 'L'};
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int bfs(Cell startCell) {
            Queue<Cell> q = new LinkedList<>();
            q.add(startCell);
            traversableCells.remove(startCell);
            int distance = 0;
            while (!q.isEmpty()) {
                int qSize = q.size();
                for (int i = 0; i < qSize; i++) {
                    Cell currCell = q.remove();
                    for (int j = 0; j < dir.length; j++) {
                        int nr = currCell.r + dr[i];
                        int nc = currCell.c + dc[i];
                        Cell nextCell = new Cell(nr, nc);
                        if (nextCell.equals(target)) {
                            return distance + 1;
                        }
                        if (traversableCells.contains(nextCell)) {
                            traversableCells.remove(nextCell);
                            q.add(nextCell);
                        }
                    }

                }
                distance++;
            }
            return -1;
        }

        void dfs(GridMaster master, Cell currCell) {
            traversableCells.add(currCell);
            if (master.isTarget()) {
                this.target = currCell;
            }
            for (int i = 0; i < dir.length; i++) {
                int nr = currCell.r + dr[i];
                int nc = currCell.c + dc[i];
                Cell nextCell = new Cell(nr, nc);
                if (master.canMove(dir[i]) && !traversableCells.contains(nextCell)) {
                    master.move(dir[i]);
                    dfs(master, nextCell);
                    master.move(revDir[i]);
                }
            }
        }

        public int findShortestPath(GridMaster master) {
            if(master.isTarget()) {
                return 0;
            }
            Cell startCell = new Cell(0, 0);
            dfs(master, startCell);
            return bfs(startCell);
        }
    }
}
