class Solution_125 {

    public boolean isPalindrome(String s) {
        if (s == null) return true;
        char[] c = s.toCharArray();
        int n = c.length;
        int i = 0;
        int j = n - 1;
        while (i < j) {
            if (!(Character.isAlphabetic(c[i]) || Character.isDigit(c[i]))) {
                i++;
            } else if (!(Character.isAlphabetic(c[j]) || Character.isDigit(c[j]))) {
                j--;
            } else if (Character.toLowerCase(c[i]) != Character.toLowerCase(c[j])) {
                return false;
            } else {
                i++;
                j--;
            }
        }
        return true;
    }
}