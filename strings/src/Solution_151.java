import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution_151 {


    // brute force
    class Approach_1 {

        private boolean isVowel(char c) {
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }

        public int beautifulSubstrings(String s, int k) {
            int res = 0;
            int n = s.length();
            for (int i = 0; i < n; i++) {
                int c = 0;
                int v = 0;
                for (int j = i; j < n; j++) {
                    if (isVowel(s.charAt(j))) {
                        v++;
                    } else {
                        c++;
                    }
                    if (c == v && ((c * v) % k) == 0) {
                        res++;
                    }
                }
            }
            return res;
        }
    }

    // prefix sum + hashmap
    class Approach_2 {

        private boolean isVowel(char c) {
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }

        public int beautifulSubstrings(String s, int k) {
            int res = 0;
            int n = s.length();
            Map<Integer, List<Integer>> sc = new HashMap<>();
            int[] c = new int[n + 1];
            int[] v = new int[n + 1];
            sc.put(0, new ArrayList<>(List.of(0)));
            for (int i = 1; i <= n; i++) {
                if (isVowel(s.charAt(i - 1))) {
                    v[i] = v[i - 1] + 1;
                    c[i] = c[i - 1];
                } else {
                    v[i] = v[i - 1];
                    c[i] = c[i - 1] + 1;
                }
                int df = v[i] - c[i];
                if (sc.containsKey(df)) {
                    for (int it : sc.get(df)) {
                        int vc = (i - it) / 2;
                        if ((vc * vc) % k == 0) {
                            res++;
                        }
                    }
                    sc.get(df).add(i);
                } else {
                    sc.put(df, new ArrayList<>(List.of(i)));
                }
            }
            return res;
        }
    }
}