class Solution_9 {

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int rev = reverse(x);
        return rev == x;
    }

    // convert number to array
    public boolean isPalindrome1(int x) {
        if (x < 0) return false;
        int arr[] = new int[20];
        int i = 0;
        while (x > 0) {
            arr[i++] = x % 10;
            x = x / 10;
        }
        int j = 0;
        int k = i - 1;
        while (j < k) {
            if (arr[j] != arr[k]) {
                return false;
            }
            j++;
            k--;
        }
        return true;
    }

    // using the String buffer
    public boolean isPalindrome2(int x) {
        String str = String.valueOf(x);
        String rev = new StringBuffer(str).reverse().toString();
        if (str.equals(rev)) {
            return true;
        }
        return false;
    }

    // elegant way to reverse a number
    public int reverse(int n) {
        int res = 0;
        while (n != 0) {
            res = res * 10 + n % 10;
            n /= 10;
        }
        return res;
    }
}