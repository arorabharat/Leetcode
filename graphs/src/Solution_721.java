import java.util.*;

public class Solution_721 {


    // without union find
    class Solution {

        static class Node {

            String name;
            Set<String> emails;

            public Node(List<String> account) {
                this.name = account.getFirst();
                this.emails = new HashSet<>();
                for (int i = 1; i < account.size(); i++) {
                    this.emails.add(account.get(i));
                }
            }

            void merge(Node node) {
                assert this.name.equals(node.name);
                this.emails.addAll(node.emails);
            }

            boolean isSame(Node node) {
                for (String email : this.emails) {
                    if (node.emails.contains(email)) {
                        return true;
                    }
                }
                return false;
            }

            List<String> getAccount() {
                List<String> account = new ArrayList<>();
                account.add(this.name);
                account.addAll(this.emails.stream().sorted().toList());
                return account;
            }

        }

        class Graph {

            private final Set<Node> disJointSets = new HashSet<>();

            void addNode(Node node) {
                if (disJointSets.isEmpty()) {
                    disJointSets.add(node);
                    return;
                }
                List<Node> commonNodeSet = new ArrayList<>();
                for (Node root : disJointSets) {
                    if (root.isSame(node)) {
                        commonNodeSet.add(root);
                    }
                }
                if (commonNodeSet.isEmpty()) {
                    disJointSets.add(node);
                    return;
                }
                for (Node commonNode : commonNodeSet) {
                    disJointSets.remove(commonNode);
                }
                Node firstNode = commonNodeSet.getFirst();
                firstNode.merge(node);
                for (int i = 1; i < commonNodeSet.size(); i++) {
                    firstNode.merge(commonNodeSet.get(i));
                }
                disJointSets.add(firstNode);
            }

            List<List<String>> getNodes() {
                List<List<String>> nodesList = new ArrayList<>();
                for (Node node : disJointSets) {

                    nodesList.add(node.getAccount());
                }
                return nodesList;
            }
        }

        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            Graph graph = new Graph();
            for (List<String> account : accounts) {
                graph.addNode(new Node(account));
            }
            return graph.getNodes();
        }
    }
}
