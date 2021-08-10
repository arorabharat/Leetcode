class Solution_1472 {

    /**
     * Your BrowserHistory object will be instantiated and called as such:
     * BrowserHistory obj = new BrowserHistory(homepage);
     * obj.visit(url);
     * String param_2 = obj.back(steps);
     * String param_3 = obj.forward(steps);
     */
    static class BrowserHistory {

        Node pseudoNode;
        Node curr;

        public BrowserHistory(String homepage) {
            this.pseudoNode = new Node(null, null, null);
            this.curr = new Node(homepage, pseudoNode, null);
            this.pseudoNode.next = this.curr;
        }

        public void visit(String url) {
            this.curr.next = new Node(url, this.curr, null);
            this.curr = this.curr.next;
        }

        public String back(int steps) {
            while (steps > 0 && this.curr.prev != this.pseudoNode) {
                this.curr = this.curr.prev;
                steps--;
            }
            return this.curr.url;
        }

        public String forward(int steps) {
            while (steps > 0 && this.curr != null && this.curr.next != null) {
                this.curr = this.curr.next;
                steps--;
            }
            return this.curr.url;
        }

        static class Node {
            String url;
            Node prev;
            Node next;

            Node(String url) {
                this.url = url;
            }

            Node(String url, Node prev, Node next) {
                this.url = url;
                this.prev = prev;
                this.next = next;
            }
        }
    }

}
