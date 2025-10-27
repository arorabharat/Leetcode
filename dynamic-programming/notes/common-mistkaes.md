Excellent question — and honestly, this is where many developers (even experienced ones) slip up.
Here are the **most common mistakes engineers make with exceptions** — along with what to do instead 👇

---

## 🚫 1. Catching Generic `Exception`

```java
try {
    // some code
} catch (Exception e) {
    e.printStackTrace();
}
```

**Why it’s bad:**

* It hides the real cause of the error.
* You can’t handle different exception types differently.
* It can catch things you didn’t mean to (like programming bugs).

✅ **Better:**

```java
try {
    // code
} catch (FileNotFoundException e) {
    // specific handling
} catch (IOException e) {
    // handle IO
}
```

---

## 🚫 2. Swallowing Exceptions (Empty catch blocks)

```java
try {
    // risky code
} catch (IOException e) {
    // do nothing
}
```

**Why it’s bad:**

* The program silently fails — debugging becomes a nightmare.

✅ **Better:**
Always log or rethrow.

```java
catch (IOException e) {
    System.err.println("Error reading file: " + e.getMessage());
}
```

---

## 🚫 3. Overusing Checked Exceptions

Too many custom checked exceptions make APIs painful to use — forcing callers to add unnecessary `try-catch` blocks.

✅ **Rule:**
Use **checked** exceptions only when the caller can **recover** from the error.
Otherwise, make it **unchecked** (extend `RuntimeException`).

---

## 🚫 4. Ignoring the Root Cause

```java
catch (SQLException e) {
    throw new RuntimeException("Database error");
}
```

**Why it’s bad:**
You lose the original stack trace.

✅ **Better:**

```java
catch (SQLException e) {
    throw new RuntimeException("Database error", e);
}
```

---

## 🚫 5. Using Exceptions for Flow Control

```java
try {
    int val = Integer.parseInt("abc");
} catch (NumberFormatException e) {
    val = 0; // using exception for normal logic
}
```

**Why it’s bad:**

* Exceptions are expensive (they create stack traces).
* It makes code harder to follow.

✅ **Better:**
Use validation before risky operations.

```java
if (isNumeric(input)) {
    val = Integer.parseInt(input);
}
```

---

## 🚫 6. Throwing Generic Exceptions

```java
throw new Exception("Something went wrong");
```

**Why it’s bad:**

* It doesn’t tell *what* went wrong.

✅ **Better:**
Use meaningful exception types.

```java
throw new InvalidUserInputException("User ID cannot be empty");
```

---

## 🚫 7. Forgetting `finally` or `try-with-resources`

Leaving files, streams, or DB connections open:

```java
FileReader fr = new FileReader("data.txt");
fr.close(); // never reached if exception happens
```

✅ **Better:**

```java
try (FileReader fr = new FileReader("data.txt")) {
    // read file
}
```

This ensures the resource is **always closed**, even if an exception occurs.

---

## 🚫 8. Mixing Business Logic with Exception Logic

```java
try {
    placeOrder();
} catch (Exception e) {
    cancelOrder();
}
```

**Why it’s bad:**

* Makes it unclear what’s “normal” vs “error” flow.
  ✅ **Better:**
  Separate **error handling** from **business logic** where possible.

---

## 🚫 9. Rethrowing Exceptions Without Context

```java
catch (IOException e) {
    throw e;
}
```

✅ **Better:**
Add helpful context before rethrowing.

```java
catch (IOException e) {
    throw new IOException("Failed to read config file", e);
}
```

---

## 🚫 10. Ignoring `InterruptedException`

When working with threads:

```java
catch (InterruptedException e) {
    // ignore
}
```

**Why it’s bad:**
You break thread interruption protocols.

✅ **Better:**

```java
catch (InterruptedException e) {
    Thread.currentThread().interrupt(); // preserve interrupt status
}
```

---

## 🧭 Summary

| Mistake                       | Impact         | Fix                             |
| ----------------------------- | -------------- | ------------------------------- |
| Catching `Exception`          | Hides details  | Catch specific exceptions       |
| Empty catch block             | Silent failure | Log or rethrow                  |
| Overusing checked exceptions  | Noisy APIs     | Use only for recoverable errors |
| Throwing generic exceptions   | No context     | Define meaningful ones          |
| Using exceptions for flow     | Inefficient    | Validate inputs                 |
| Ignoring finally/resources    | Resource leaks | Use try-with-resources          |
| Ignoring InterruptedException | Thread issues  | Re-interrupt thread             |

---

Would you like me to show a **“bad vs good” side-by-side code example** that demonstrates 3–4 of these mistakes together (like what many devs accidentally write in real projects)?
