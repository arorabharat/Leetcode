import java.util.ArrayList;
import java.util.List;

public class Solution_46 {


    class Solution1 {

        private final List<List<Integer>> permutationList = new ArrayList<>();

        void dfs(int[] nums, List<Integer> bucket, boolean[] visited) {
            if (bucket.size() == nums.length) {
                permutationList.add(new ArrayList<>(bucket));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (visited[i]) {
                    continue;
                }
                visited[i] = true;
                bucket.add(nums[i]);
                dfs(nums, bucket, visited);
                visited[i] = false;
                bucket.removeLast();
            }
        }

        public List<List<Integer>> permute(int[] nums) {
            dfs(nums, new ArrayList<>(), new boolean[nums.length]);
            return permutationList;
        }
    }
}

