import java.util.Arrays;
import java.util.TreeMap;

class Solution_1170 {

    int frequency(String word) {
        int count = 0;
        int minChar = 26;
        for (int i = 0; i < word.length(); i++) {
            int currChar = word.charAt(i) - 'a';
            if (currChar < minChar) {
                minChar = currChar;
                count = 1;
            } else if (currChar == minChar) {
                count++;
            }
        }
        return count;
    }

    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int nw = words.length;
        int[] frequencies = new int[nw];
        for (int i = 0; i < nw; i++) {
            frequencies[i] = frequency(words[i]);
        }
        int nq = queries.length;
        int[] qr = new int[nq];
        for (int i = 0; i < nq; i++) {
            int fr = frequency(queries[i]);
            int count = 0;
            for (int fw : frequencies) {
                if (fr < fw) count++;
            }
            qr[i] = count;
        }
        return qr;
    }

    /**
     * Optimisation :
     * we could simply find the upper bound in the frequencies array. To get the number of elements whose frequency is greater than query
     */
    public int[] numSmallerByFrequency2(String[] queries, String[] words) {
        int nw = words.length;
        int[] frequencies = new int[nw];
        for (int i = 0; i < nw; i++) {
            frequencies[i] = frequency(words[i]);
        }
        Arrays.sort(frequencies);
        int nq = queries.length;
        int[] qr = new int[nq];
        for (int i = 0; i < nq; i++) {
            int fr = frequency(queries[i]);
            int index = Arrays.binarySearch(frequencies, fr);
            if (index < 0) {
                // this is a shifted insertion point ie index = - insertion point - 1
                // it is shifted by -1 because if the insertion point is not shifted then if its value is zero we would not be  able to differentiate if element was found or not
                index = index + 1; // shift by 1
                index = -index; // revert sign will give the insertion point. insertion point is the index of the first element which is greater than current element.
            }
            while (index < nw && frequencies[index] == fr) {
                index++;
            }
            qr[i] = nw - index;
        }
        return qr;
    }

    public int[] numSmallerByFrequency3(String[] queries, String[] words) {
        TreeMap<Integer, Integer> frequencies = new TreeMap<>();
        for (String word : words) {
            int fr = frequency(word);
            frequencies.put(fr, frequencies.getOrDefault(fr, 0) + 1);
        }
        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int queryFreq = frequency(queries[i]);
            for (Integer key : frequencies.tailMap(queryFreq, false).keySet()) {
                result[i] = result[i] + frequencies.get(key);
            }
        }
        return result;
    }

}