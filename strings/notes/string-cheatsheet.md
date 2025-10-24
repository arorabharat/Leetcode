Sure — here’s a clean **Markdown version** of the 10-point Java String Methods Cheatsheet 👇

---

# 🧠 Java String Methods Cheatsheet for DSA

## 1. Creation & Basic Info

```java
String s = "hello";
new

String("hello");
s.

length(); // 5
```

---

## 2. Character Access

```java
s.charAt(i);           // Get character at index i
s.

toCharArray();        // Convert to char array
```

---

## 3. Comparison

```java
s1.equals(s2);                 // Exact match
s1.

equalsIgnoreCase(s2);       // Ignore case
s1.

compareTo(s2);              // Lexicographic compare (<0, =0, >0)
s1.

compareToIgnoreCase(s2);    // Case-insensitive compare
```

---

## 4. Searching

```java
s.contains("sub");       // true if substring found
s.

indexOf("l");          // First index of 'l'
s.

lastIndexOf("l");      // Last index of 'l'
s.

startsWith("he");      // true
s.

endsWith("lo");        // true
```

---

## 5. Substrings

```java
s.substring(start);           // From index to end
s.

substring(start, end);      // From start to end-1
```

---

## 6. Modification (New String Returned)

```java
s.toLowerCase();
s.

toUpperCase();
s.

trim();
s.

replace('a','b');
s.

replace("old","new");
```

---

## 7. Splitting & Joining

```java
s.split(" ");                      // Split by delimiter
String.

join("-","a","b","c");   // "a-b-c"
```

---

## 8. Conversions

```java
String.valueOf(123);         // "123"
Integer.

parseInt("42");      // 42
Long.

parseLong("12345");     // 12345L
Character.

getNumericValue('5'); // 5
```

---

## 9. Checking Characters (Character Class)

```java
Character.isDigit(c);
Character.

isLetter(c);
Character.

isLowerCase(c);
Character.

isUpperCase(c);
Character.

toLowerCase(c);
Character.

toUpperCase(c);
```

---

## 10. Useful Tricks

```java
new StringBuilder(s).

reverse().

toString();   // Reverse string
sb.

append("abc");                            // Efficient concatenation
s.

equals(new StringBuilder(s).

reverse().

toString()); // Palindrome check
char[] ch = s.toCharArray();
Arrays.

sort(ch);                             // Sort characters (for anagrams)
```

---

Would you like me to turn this into a **printable PDF cheatsheet** (formatted and styled for notes)?
