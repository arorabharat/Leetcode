import java.util.Arrays;

/**
 * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
 */
class Solution_673 {

    /**
     * lenOfSeq[i] represent the length of the longest subsequence if the sequence end at ith index.
     * lenOfSeq[i] = max of { lenOfSeq[i] , lenOfSeq[j]+1 } where j range from 0,1-1 and nums[j] < nums[i] ( to keep sequence strictly increasing )
     * then we will finally find the max sub sequence assuming the sequence could at any index
     */
    public int findNumberOfLIS(int[] nums) {

        int n = nums.length;
        int[] lenOfSeq = new int[n];
        Arrays.fill(lenOfSeq, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    lenOfSeq[i] = Math.max(lenOfSeq[i], lenOfSeq[j] + 1);
                }
            }
        }

        int maxSeqLen = 1;
        for (int i = 0; i < n; i++) {
            maxSeqLen = Math.max(lenOfSeq[i], maxSeqLen);
        }

        int[] noOfSeq = new int[n];
        for (int i = 0; i < n; i++) {
            if (lenOfSeq[i] == 1) {
                noOfSeq[i] = 1;
            } else {
                for (int j = 0; j < i; j++) {
                    if (lenOfSeq[i] == lenOfSeq[j] + 1 && nums[i] > nums[j]) {
                        noOfSeq[i] = noOfSeq[i] + noOfSeq[j];
                    }
                }
            }
        }

        int noOfMaxSeq = 0;
        for (int i = 0; i < n; i++) {
            if (maxSeqLen == lenOfSeq[i]) {
                noOfMaxSeq = noOfMaxSeq + noOfSeq[i];
            }
        }
        return noOfMaxSeq;
    }
}
