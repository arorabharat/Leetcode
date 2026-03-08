public class Solution_473 {

    // [1,1,2,2,2]
    // [1,1,1,1,3,3,3,3]

    // incorrect solution
    boolean[] visited;
    int[] matchsticks;

    public boolean backtrack(int count, int target) {
        if (count == 4) {
            return true;
        }
        // include in current bucket
        //  do not include in current bucket
        int sum = 0;
        for (int i = 0; i < matchsticks.length; i++) {
            if(visited[i]) {
               continue;
            }
            visited[i] = true;
            sum = sum + matchsticks[i];
            if(sum == target && backtrack(count + 1, target) ) {
                return true;
            } else if(sum > target) {
                visited[i] = false;
                return false;
            } else {
               return backtrack(count, target);
            }
        }
        return false;
    }

    public boolean makesquare(int[] matchsticks) {
        int sum = 0;
        for (int l : matchsticks) {
            sum = sum + l;
        }
        if (sum % 4 != 0) {
            return false;
        }
        this.visited = new boolean[matchsticks.length];
        this.matchsticks = matchsticks;
        return backtrack(0, sum / 4);
    }

}
