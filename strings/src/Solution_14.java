import java.nio.charset.StandardCharsets;

public class Solution_14 {
    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 1) return strs[0];
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen,str.length());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < minLen; i++) {
            char c = strs[0].charAt(i);
            boolean matched = true;
            for (int j = 1; j < strs.length; j++) {
                if(c != strs[j].charAt(i)) {
                    matched = false;
                    break;
                }
            }
            if(matched) {
                sb.append(c);
            }
            else{
                break;
            }

        }
        return sb.toString();
    }
}