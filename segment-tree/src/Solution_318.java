class Node {

    int v;
    int l;
    int r;

    Node(int l, int r) {
        this.l = l;
        this.r = r;
    }

    public void setValue(Node a, Node b) {
        this.v = a.v + b.v;
    }

    public void setValue(int v) {
        this.v = v;
    }
}

class SegmentTree {

    Node[] nodes;
    int[] arr;
    private final static int ROOT = 0;

    SegmentTree() {
    }

    public void build(int[] nums) {
        arr = nums;
        int n = nums.length;
        nodes = new Node[4 * n];
        _build(nums, 0, n - 1, ROOT);
    }

    private void _build(int[] nums, int s, int e, int p) {
        nodes[p] = new Node(s, e);
        if (s == e) {
            nodes[p].setValue(nums[s]);
        } else {
            int m = (s + e) / 2;
            _build(nums, s, m, left(p));
            _build(nums, m + 1, e, right(p));
            nodes[p].setValue(nodes[left(p)], nodes[right(p)]);
        }
    }

    public int left(int p) {
        return 2 * p + 1;
    }

    public int right(int p) {
        return 2 * p + 2;
    }

    public void update(int index, int value) {
        _update(index, value, ROOT);
    }

    private void _update(int index, int value, int p) {
        if (index < nodes[p].l || nodes[p].r < index) {
            return;
        } else if (nodes[p].l == index && index == nodes[p].r) {
            nodes[p].setValue(value);
        } else {
            _update(index, value, left(p));
            _update(index, value, right(p));
            nodes[p].setValue(nodes[left(p)], nodes[right(p)]);
        }
    }

    public int rangeQuery(int l, int r) {
        return _rangeQuery(l, r, ROOT);
    }

    private int _rangeQuery(int l, int r, int p) {
        if (r < nodes[p].l || nodes[p].r < l) {
            return 0;
        } else if (l <= nodes[p].l && nodes[p].r <= r) {
            return nodes[p].v;
        } else {
            int left = _rangeQuery(l, r, left(p));
            int right = _rangeQuery(l, r, right(p));
            return left + right;
        }
    }
}


class Solution_318 {

    SegmentTree tree;

    public Solution_318(int[] nums) {
        tree = new SegmentTree();
        tree.build(nums);
    }

    public void update(int index, int val) {
        tree.update(index, val);
    }

    public int sumRange(int left, int right) {
        return tree.rangeQuery(left, right);
    }
}