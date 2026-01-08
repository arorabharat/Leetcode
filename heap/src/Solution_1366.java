//Count charaacter frequency
// kth largest element

import java.util.*;

// todo
public class Solution_1366 {


    class Approach_1 {

        static class Node implements Comparable<Node> {
            Character team;
            int[] ranks;

            public Node(Character team, int[] ranks) {
                this.team = team;
                this.ranks = ranks;
            }

            @Override
            public int compareTo(Node o) {
                if (o == null) {
                    throw new RuntimeException();
                }
                if (this.ranks.length != o.ranks.length) {
                    throw new RuntimeException();
                }
                for (int i = 0; i < this.ranks.length; i++) {
                    if (this.ranks[i] > o.ranks[i]) {
                        return -1;
                    } else if (this.ranks[i] < o.ranks[i]) {
                        return 1;
                    }
                }
                return this.team.compareTo(o.team);
            }
        }

        public String rankTeams(String[] votes) {
            Map<Character, int[]> voteCount = new HashMap<>();
            if (votes.length == 0) {
                return null;
            }
            for (String vote : votes) {
                int n = vote.length();
                int rank = 0;
                for (Character team : vote.toCharArray()) {
                    if (!voteCount.containsKey(team)) {
                        voteCount.put(team, new int[n]);
                    }
                    voteCount.get(team)[rank]++;
                    rank++;
                }
            }
            List<Node> nodes = new ArrayList<>();
            for (var entry : voteCount.entrySet()) {
                nodes.add(new Node(entry.getKey(), entry.getValue()));
                System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue()));
            }
            Collections.sort(nodes);
            StringBuilder stringBuilder = new StringBuilder();
            for (Node node : nodes) {
                stringBuilder.append(node.team);
            }
            return stringBuilder.toString();
        }
    }

    class Appraoch_2 {

        static class Node implements Comparable<Node> {
            Character team;
            int[] ranks;

            public Node(Character team, int[] ranks) {
                this.team = team;
                this.ranks = ranks;
            }

            @Override
            public int compareTo(Node o) {
                for (int i = 0; i < ranks.length; i++) {
                    if (ranks[i] != o.ranks[i]) {
                        return o.ranks[i] - ranks[i]; // descending
                    }
                }
                return team - o.team; // alphabetical
            }
        }


        public String rankTeams(String[] votes) {
            Map<Character, int[]> voteCount = new HashMap<>();
            if (votes.length == 0) {
                return null;
            }
            for (String vote : votes) {
                int n = vote.length();
                int rank = 0;
                for (Character team : vote.toCharArray()) {
                    if (!voteCount.containsKey(team)) {
                        voteCount.put(team, new int[n]);
                    }
                    voteCount.get(team)[rank]++;
                    rank++;
                }
            }
            List<Node> nodes = new ArrayList<>();
            for (var entry : voteCount.entrySet()) {
                nodes.add(new Node(entry.getKey(), entry.getValue()));
                System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue())
                );
            }
            Collections.sort(nodes);
            StringBuilder stringBuilder = new StringBuilder();
            for (Node node : nodes) {
                stringBuilder.append(node.team);
            }
            return stringBuilder.toString();
        }
    }
}
