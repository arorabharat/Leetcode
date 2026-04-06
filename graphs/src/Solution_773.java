import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Solution_773 {
    class Solution {

        Map<Integer, Integer> dp = new HashMap<>();

        public static int getBitMask(int[][] arr) {
            return arr[0][0] | arr[0][1] << 3 | arr[0][2] << 6
                    | arr[1][0] << 9 | arr[1][1] << 12 | arr[1][2] << 15;
        }

        public static int[][] getArray(int bitMask) {
            int[][] arr = new int[2][3];
            arr[0][0] = bitMask & 0b111;
            arr[0][1] = bitMask >> 3 & 0b111;
            arr[0][2] = bitMask >> 6 & 0b111;
            arr[1][0] = bitMask >> 9 & 0b111;
            arr[1][1] = bitMask >> 12 & 0b111;
            arr[1][2] = bitMask >> 15 & 0b111;
            return arr;
        }

        public static int[] getZeroIndex(int[][] arr) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (arr[i][j] == 0) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{-1, -1};
        }

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        boolean isValid(int r, int c) {
            return 0 <= r && r < 2 && 0 <= c && c < 3;
        }

        // 3*6
        public int slidingPuzzle(int[][] board) {
            int initStateMask = getBitMask(board);
            Queue<Integer> q = new LinkedList<>();
            q.add(initStateMask);
            int[][] targetState = getTargetState();
            int targetStateMask = getBitMask(targetState);
            int distance = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int currStateMask = q.remove();
                    int[][] currState = getArray(currStateMask);
                    int[] currStateZeroIndex = getZeroIndex(currState);
                    int cx = currStateZeroIndex[0];
                    int cy = currStateZeroIndex[1];
                    for (int k = 0; k < 4; k++) {
                        int nx = cx + dx[k];
                        int ny = cy + dy[k];
                        if (isValid(nx, ny)) {
                            currState[cx][cy] = currState[nx][ny];
                            currState[nx][ny] = 0;
                            int nextStateMask = getBitMask(currState);
                            currState[nx][ny] = currState[cx][cy];
                            currState[cx][cy] = 0;
                            if(nextStateMask == targetStateMask) {
                                return distance + 1;
                            }
                            if (!dp.containsKey(nextStateMask)) {
                                q.add(nextStateMask);
                                dp.put(nextStateMask, distance + 1);
                            }
                        }
                    }
                }
                distance++;
            }
            return -1;
        }

        private static int[][] getTargetState() {
            int[][] targetState = new int[2][3];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    targetState[i][j] = 3 * i + (j + 1);
                }
            }
            targetState[1][2] = 0;
            return targetState;
        }


    }

    /**
     * 101               000
     * 100           000 000
     * 11        000 000 000
     * 10    000 000 000 000
     * 1 000 000 000 000 000
     */

    public static int bitMask(int[][] arr) {
        return arr[0][0] | arr[0][1] << 3 | arr[0][2] << 6
                | arr[1][0] << 9 | arr[1][1] << 12 | arr[1][2] << 15;
    }

    public static void getArray(int bitMask, int[][] arr) {
        arr[0][0] = bitMask & 0b111;
        arr[0][1] = bitMask >> 3 & 0b111;
        arr[0][2] = bitMask >> 6 & 0b111;
        arr[1][0] = bitMask >> 9 & 0b111;
        arr[1][1] = bitMask >> 12 & 0b111;
        arr[1][2] = bitMask >> 15 & 0b111;
    }

    static void main() {
        int targetState = 0;
        for (int i = 1; i <= 5; i++) {
            int binaryRep = (5 - (i - 1)) << (3 * i);
            targetState = targetState | binaryRep;
        }
        // System.out.println(targetState);
        int[][] arr = new int[2][3];
        arr[0][0] = 1;
        arr[0][1] = 2;
        arr[0][2] = 3;
        arr[1][0] = 4;
        arr[1][1] = 5;
        arr[1][2] = 0;
        int x = bitMask(arr);
        System.out.println(x);
        getArray(x, arr);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
