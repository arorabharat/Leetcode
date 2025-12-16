Here’s a **clean, structured Markdown summary** you can drop straight into your notes.
It captures the *why*, not just the *how*, and keeps the mental models intact.

---

# Java Concurrency Notes: ReentrantLock & Condition

## 1. What a Lock Is (and Is Not)

### Purpose of a Lock

A `ReentrantLock` provides **mutual exclusion**.

It guarantees:

* Only one thread executes a critical section at a time
* Atomicity of shared-state updates
* Memory visibility between threads

It does **not** provide:

* Waiting for state
* Thread coordination
* Notification mechanisms

**Lock answers:**

> “Who is allowed to enter right now?”

---

## 2. Plain Lock Usage (No Condition)

### When a lock alone is sufficient

Use only a lock when:

* Threads can always proceed
* No thread needs to wait for another
* You just need to protect shared data

Typical use cases:

* Counters
* Caches
* Shared in-memory structures

### Pattern

```java
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

Golden rule: **always unlock in `finally`**.

---

## 3. Why a Lock Alone Is Not Enough

Many problems require this behavior:

> “Wait until some shared state becomes true.”

Examples:

* Consumer waits for data
* Producer adds data
* Consumer resumes

With only a lock, your options are:

* Busy waiting (CPU waste)
* Sleeping and polling (latency + bugs)

Neither is correct or efficient.

---

## 4. What a Condition Adds

A `Condition` provides **thread communication**.

It allows a thread to:

* Sleep efficiently
* Release the lock while waiting
* Resume when state *might* have changed

**Condition answers:**

> “When may I proceed?”

---

## 5. How Condition Works (Key Semantics)

### `await()`

Atomically:

1. Releases the lock
2. Suspends the thread
3. Adds thread to the condition wait set
4. Re-acquires the lock before returning

### `signal()`

* Wakes *one* waiting thread
* Does **not** transfer the lock
* Does **not** guarantee condition is true

---

## 6. Canonical Condition Pattern

```java
lock.lock();
try {
    while (!conditionIsTrue) {
        condition.await();
    }
    // proceed safely
} finally {
    lock.unlock();
}
```

This pattern is **mandatory**, not stylistic.

---

## 7. Why `while`, Not `if`

### Reason 1: Spurious wakeups

Threads may wake up **without a signal**.

### Reason 2: Multiple waiting threads

A signal may wake:

* The wrong thread
* More than one thread (with `signalAll()`)

### Reason 3: Lost timing

Between `signal()` and lock acquisition:

* Other threads may change state

**Conclusion:**
Wakeup ≠ permission to proceed.

---

## 8. Mental Models (Critical for Mastery)

### Lock vs Condition

* Lock → protects **state**
* Condition → waits for **state change**

### Signal meaning

> “Something may have changed. Re-check.”

### Await mindset

> “I woke up. I trust nothing. Verify everything.”

---

## 9. Invariant-Based Thinking

The `while` loop enforces an invariant:

> “I may proceed only if this condition is true.”

Every wakeup re-validates that invariant.

---

## 10. Relation to `synchronized`

| `synchronized`  | `ReentrantLock`     |
| --------------- | ------------------- |
| Implicit lock   | Explicit lock       |
| Single wait set | Multiple Conditions |
| `wait/notify`   | `await/signal`      |
| Less flexible   | More control        |

`ReentrantLock` separates **locking** from **waiting**, which is more powerful.

---

## 11. One-Line Rules to Remember

* Locks prevent races
* Conditions enable waiting
* Signals are hints, not guarantees
* Always wait in a `while`
* State is truth, notifications are noise

---

## 12. When to Use Condition

Use `Condition` when you hear:

* “Wait until…”
* “Only proceed when…”
* “Pause until data arrives…”

If not, a plain lock is enough.

---

Concurrency becomes manageable once you stop thinking in terms of **events** and start thinking in terms of **state and invariants**.
