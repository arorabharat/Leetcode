// todo
public class Solution_1366 {
    
    public String rankTeams(String[] votes) {
        int n = 26;
        int[][] rank = new int[n][n];
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                int t = vote.charAt(i) - 'A';
                rank[i][t]++;
            }
        }

    }
}
