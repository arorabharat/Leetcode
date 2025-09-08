Here’s a clean Java solution plus several alternative approaches, with pros/cons and complexity for each.

# ✅ Best approach: Bitwise XOR

**Idea:** XOR cancels pairs: `a ^ a = 0`, `a ^ 0 = a`. Since every number appears twice except one, XOR of all elements
leaves the unique number.

**Time:** `O(n)`
**Space:** `O(1)`
**Why best:** Single pass, constant extra space, no sorting or hashing.

```java
class Solution {
    public int singleNumber(int[] nums) {
        int x = 0;
        for (int v : nums) {
            x ^= v;
        }
        return x;
    }
}
```

---

## Other valid approaches

### 1) HashSet add/remove trick

**Idea:** Add number if new; remove if seen again. Leftover is the answer.

```java
import java.util.HashSet;

class SolutionHashSet {
    public int singleNumber(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int v : nums) {
            if (!set.add(v)) set.remove(v);
        }
        // Only one element remains
        return set.iterator().next();
    }
}
```

**Time:** `O(n)` average
**Space:** `O(n)`
**Notes:** Simple but extra memory.

---

### 2) Sorting then scan

**Idea:** Sort; equal pairs sit together. Walk by steps of 2; the one that breaks the pairing is the answer.

```java
import java.util.Arrays;

class SolutionSort {
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) return nums[i];
        }
        return nums[nums.length - 1]; // last one is single
    }
}
```

**Time:** `O(n log n)` (sorting)
**Space:** `O(1)` extra (if we allow in-place sort; Java’s TimSort uses `O(log n)` stack)
**Notes:** Slower than XOR due to sort.

---

### 3) HashMap counts

**Idea:** Count frequency, return key with count 1.

```java
import java.util.HashMap;
import java.util.Map;

class SolutionMap {
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int v : nums) cnt.put(v, cnt.getOrDefault(v, 0) + 1);
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            if (e.getValue() == 1) return e.getKey();
        }
        throw new IllegalStateException("Invalid input");
    }
}
```

**Time:** `O(n)`
**Space:** `O(n)`
**Notes:** Works but heavier than XOR.

---

### 4) Math identity (use with care)

**Idea:** `2 * sum(set(nums)) - sum(nums)` equals the single number.

```java
import java.util.HashSet;

class SolutionMath {
    public int singleNumber(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        long sumSet = 0, sumAll = 0; // long to avoid overflow
        for (int v : nums) {
            if (set.add(v)) sumSet += v;
            sumAll += v;
        }
        return (int) (2 * sumSet - sumAll);
    }
}
```

**Time:** `O(n)`
**Space:** `O(n)`
**Notes:** Needs a set and careful overflow handling; less elegant.

---

## Final recommendation

Use the **XOR solution**—it’s optimal (`O(n)` time, `O(1)` space), simple, and tailor-made for the “every element twice
except one” pattern.
