import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Solution_1071 {

    boolean isPrimeFactor(String s, String t) {
        if (t.equals(s)) return true;
        do {
            t = t + t;
        } while (t.length() < s.length() && s.contains(t));
        return t.equals(s);
    }


    public String gcdOfStrings(String str1, String str2) {
        PriorityQueue<String> primeFactor1 = getPrimeFactors(str1);
        while (!primeFactor1.isEmpty()) {
            String primeFactor = primeFactor1.poll();
            if(isPrimeFactor(str2 , primeFactor)) {
                return primeFactor;
            }
        }
        return "";
    }

    private PriorityQueue<String> getPrimeFactors(String str) {
        int n = str.length();
        PriorityQueue<String> primeFactor = new PriorityQueue<>((a,b) -> b.length() - a.length());
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String t = str.substring(i, j + 1);
                if (isPrimeFactor(str, t)) {
                    primeFactor.add(t);
                }
            }
        }
        return primeFactor;
    }
}
