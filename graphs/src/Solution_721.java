import java.util.*;

public class Solution_721 {

    class DSU {

        int representative[];
        int size[];

        DSU(int sz) {
            representative = new int[sz];
            size = new int[sz];

            for (int i = 0; i < sz; ++i) {
                representative[i] = i;
                size[i] = 1;
            }
        }

        // Finds the representative of group x
        public int findRepresentative(int x) {
            if (x == representative[x]) {
                return x;
            }

            // This is path compression
            return representative[x] = findRepresentative(representative[x]);
        }

        // Unite the group that contains "a" with the group that contains "b"
        public void unionBySize(int a, int b) {
            int representativeA = findRepresentative(a);
            int representativeB = findRepresentative(b);

            // If nodes a and b already belong to the same group, do nothing.
            if (representativeA == representativeB) {
                return;
            }

            // Union by size: point the representative of the smaller
            // group to the representative of the larger group.
            if (size[representativeA] >= size[representativeB]) {
                size[representativeA] += size[representativeB];
                representative[representativeB] = representativeA;
            } else {
                size[representativeB] += size[representativeA];
                representative[representativeA] = representativeB;
            }
        }
    }

    class Solution3 {

        public List<List<String>> accountsMerge(List<List<String>> accountList) {
            int accountListSize = accountList.size();
            DSU dsu = new DSU(accountListSize);

            // Maps email to their component index
            Map<String, Integer> emailGroup = new HashMap<>();

            for (int i = 0; i < accountListSize; i++) {
                int accountSize = accountList.get(i).size();

                for (int j = 1; j < accountSize; j++) {
                    String email = accountList.get(i).get(j);
                    String accountName = accountList.get(i).get(0);

                    // If this is the first time seeing this email then
                    // assign component group as the account index
                    if (!emailGroup.containsKey(email)) {
                        emailGroup.put(email, i);
                    } else {
                        // If we have seen this email before then union this
                        // group with the previous group of the email
                        dsu.unionBySize(i, emailGroup.get(email));
                    }
                }
            }

            // Store emails corresponding to the component's representative
            Map<Integer, List<String>> components = new HashMap<Integer, List<String>>();
            for (String email : emailGroup.keySet()) {
                int group = emailGroup.get(email);
                int groupRep = dsu.findRepresentative(group);

                if (!components.containsKey(groupRep)) {
                    components.put(groupRep, new ArrayList<String>());
                }

                components.get(groupRep).add(email);
            }

            // Sort the components and add the account name
            List<List<String>> mergedAccounts = new ArrayList<>();
            for (int group : components.keySet()) {
                List<String> component = components.get(group);
                Collections.sort(component);
                component.add(0, accountList.get(group).get(0));
                mergedAccounts.add(component);
            }

            return mergedAccounts;
        }
    }

    class Solution2 {

        class DSU {

            int representative[];
            int size[];

            DSU(int sz) {
                representative = new int[sz];
                size = new int[sz];

                for (int i = 0; i < sz; ++i) {
                    representative[i] = i;
                    size[i] = 1;
                }
            }

            // Finds the representative of group x
            public int findRepresentative(int x) {
                if (x == representative[x]) {
                    return x;
                }

                // This is path compression
                return representative[x] = findRepresentative(representative[x]);
            }

            // Unite the group that contains "a" with the group that contains "b"
            public void unionBySize(int a, int b) {
                int representativeA = findRepresentative(a);
                int representativeB = findRepresentative(b);

                // If nodes a and b already belong to the same group, do nothing.
                if (representativeA == representativeB) {
                    return;
                }

                // Union by size: point the representative of the smaller
                // group to the representative of the larger group.
                if (size[representativeA] >= size[representativeB]) {
                    size[representativeA] += size[representativeB];
                    representative[representativeB] = representativeA;
                } else {
                    size[representativeB] += size[representativeA];
                    representative[representativeA] = representativeB;
                }
            }
        }

        public List<List<String>> accountsMerge(List<List<String>> accountList) {
            int accountListSize = accountList.size();
            DSU dsu = new DSU(accountListSize);

            // Maps email to their component index
            Map<String, Integer> emailGroup = new HashMap<>();

            for (int i = 0; i < accountListSize; i++) {
                int accountSize = accountList.get(i).size();

                for (int j = 1; j < accountSize; j++) {
                    String email = accountList.get(i).get(j);
                    String accountName = accountList.get(i).get(0);

                    // If this is the first time seeing this email then
                    // assign component group as the account index
                    if (!emailGroup.containsKey(email)) {
                        emailGroup.put(email, i);
                    } else {
                        // If we have seen this email before then union this
                        // group with the previous group of the email
                        dsu.unionBySize(i, emailGroup.get(email));
                    }
                }
            }

            // Store emails corresponding to the component's representative
            Map<Integer, List<String>> components = new HashMap<Integer, List<String>>();
            for (String email : emailGroup.keySet()) {
                int group = emailGroup.get(email);
                int groupRep = dsu.findRepresentative(group);

                if (!components.containsKey(groupRep)) {
                    components.put(groupRep, new ArrayList<String>());
                }

                components.get(groupRep).add(email);
            }

            // Sort the components and add the account name
            List<List<String>> mergedAccounts = new ArrayList<>();
            for (int group : components.keySet()) {
                List<String> component = components.get(group);
                Collections.sort(component);
                component.add(0, accountList.get(group).get(0));
                mergedAccounts.add(component);
            }

            return mergedAccounts;
        }
    }

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
