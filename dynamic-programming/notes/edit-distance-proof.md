## Goal

Show why **matching the last equal character immediately is always optimal**, and delaying that match can only increase cost.

Example:

```text
A = XYZABCD
B = XYZDBCD
```

Last character:

```text
D == D
```

Claim:

Matching these two `D` immediately can never be worse than matching them later.

---

# Key Idea

Assume the last characters match:

```text
A[i] = B[j] = D
```

Suppose an optimal sequence **does not match these two D immediately**, and instead matches this `D` in B with some earlier `D` in A.

Let:

```text
B[k] = D   where k < j
```

So the algorithm delays matching the last D of A with some earlier D in B.

Then the suffix:

```text
B[k+1 ... j]
```

must still be produced using edits.

Number of characters in this suffix:

```text
j - k
```

Each of these characters requires ≥ 1 operation (insert/delete/replace).

So extra cost:

```text
≥ (j - k)
```

---

# Compare with direct matching

If we directly match the last D:

remaining problem:

```text
A[1..i-1] → B[1..j-1]
```

cost:

```text
OPT(i-1, j-1)
```

No extra operations required for the last character.

---

# If we delay the match

Match:

```text
A[i] with B[k]
```

Remaining work:

```text
A[1..i-1] → B[1..k-1]
```

AND we still must generate:

```text
B[k+1 ... j]
```

which costs at least:

```text
j - k
```

Total cost ≥

```text
OPT(i-1,k-1) + (j-k)
```

Since:

```text
j-k ≥ 1
```

this cannot be smaller than:

```text
OPT(i-1,j-1)
```

because:

```text
OPT(i-1,j-1)
```

already considers the best alignment of prefixes.

---

# Concrete Example

```text
A = XYZABCD
B = XYZDBCD
```

Correct alignment:

```text
XYZABCD
XYZDBCD
      ↑ match D immediately
```

Remaining problem:

```text
XYZABC → XYZDBC
```

only 1 replace needed:

```text
A → D
```

cost = 1

---

Suppose we delay matching last D:

match A[7] with earlier D in B:

```text
XYZABCD
XYZDBCD
   ↑ match earlier D
```

Now we still must generate:

```text
BCD
```

which costs ≥ 3 operations.

Clearly worse.

---

# Formal inequality

If last characters match:

Direct match cost:

```text
OPT(i-1,j-1)
```

Delayed match cost:

```text
OPT(i-1,k-1) + (j-k)
```

Since:

```text
j-k ≥ 1
```

we get:

```text
OPT(i-1,k-1) + (j-k) ≥ OPT(i-1,j-1)
```

Thus delaying the match cannot improve the solution.

---

# Conclusion

If last characters match:

* matching immediately gives zero cost for this character
* delaying match forces extra edits for intervening characters
* those edits cost ≥ 1 each
* therefore delaying can never reduce total cost

Hence optimal solution always matches equal last characters:

OPT(i,j)=OPT(i-1,j-1) \text{ when } A[i]=B[j]
