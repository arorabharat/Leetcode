import java.util.*;

public class PrimMSTDense {

    public static int primDense(int[][] weightMatrix) {

        int n = weightMatrix.length;

        boolean[] inMST = new boolean[n];
        int[] minDist = new int[n];

        Arrays.fill(minDist, Integer.MAX_VALUE);

        minDist[0] = 0;

        int totalCost = 0;

        for (int i = 0; i < n; i++) {

            int u = -1;

            for (int v = 0; v < n; v++) {
                if (!inMST[v] && (u == -1 || minDist[v] < minDist[u])) {
                    u = v;
                }
            }

            inMST[u] = true;
            totalCost += minDist[u];

            for (int v = 0; v < n; v++) {

                if (!inMST[v] && weightMatrix[u][v] != 0) {

                    minDist[v] =
                            Math.min(minDist[v], weightMatrix[u][v]);
                }
            }
        }

        return totalCost;
    }
}