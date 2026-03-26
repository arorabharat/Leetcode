public class Solution_427 {

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }



    class Solution {

        boolean canMerge(Node a, Node b) {
            return b.val == a.val && a.isLeaf && b.isLeaf;
        }

        // 4
        // 0 1 2 3
        // 1 1 2 3
        // 2 1 2 3
        // 3 1 2 3
        private Node _construct(int[][] grid, int s, int x, int y) {
            if (s == 1) {
                return new Node(grid[x][y] == 1, true);
            }
            int m = s / 2;
            Node topLeft = _construct(grid, m, x, y);
            Node topRight = _construct(grid, m, x + m, y);
            Node bottomLeft = _construct(grid, m, x, y + m);
            Node bottomRight = _construct(grid, m, x + m, y + m);

            boolean allLeaves = topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf;
            boolean allOnes = topLeft.val && topRight.val && bottomLeft.val && bottomRight.val;
            boolean allZeros = topLeft.val || topRight.val || bottomLeft.val || bottomRight.val;
            if ((allLeaves && allOnes) || (allLeaves && !allZeros)) {
                System.out.println(x+" "+y);
                return topLeft;
            }

            return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
        }

        public Node construct(int[][] grid) {
            return _construct(grid, grid.length, 0, 0);
        }

    }
}
