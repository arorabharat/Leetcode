It **waits**. It does not fail, throw an exception, or return a "false" status.

In Java, when a thread encounters a `synchronized` block that is already held by another thread, it is moved into a **BLOCKED** state.

---

### The Lifecycle of the Second Thread

1.  **Entry Attempt:** Thread B tries to enter the `synchronized(lock)` block.
2.  **Check:** The JVM checks the "Monitor" of the lock object. It sees Thread A is already holding the key.
3.  **Blocking:** Thread B is immediately suspended. It is moved to the **Entry Set** (a waiting area) for that specific lock.
4.  **No CPU Cycles:** While Thread B is BLOCKED, it does not consume CPU power. It effectively "sleeps" while waiting for its turn.
5.  **Release & Contention:** When Thread A exits the block, the JVM notifies the threads in the Entry Set.
6.  **Re-activation:** Thread B becomes "Runnable" again and tries to grab the lock. If there are multiple threads waiting, the JVM (usually) doesn't guarantee which one gets it next—it's a bit of a free-for-all unless "fairness" is involved (which `synchronized` doesn't support).



---

### Key Differences to Remember

* **`synchronized` (Implicit):** Always waits forever. If Thread A deadlocks and never leaves, Thread B will wait until the application is killed.
* **`Lock.tryLock()` (Explicit):** This is the alternative you use if you *want* the thread to fail or do something else instead of waiting. You can say: *"Try to get the lock, but if someone else has it, move on immediately."*

### A Simple Analogy
Think of a **single-person restroom with a manual bolt**.
* **Thread A** goes in and slides the bolt (Acquires the lock).
* **Thread B** tries the handle. It’s locked.
* Thread B doesn't go home (Fail); it stands in line outside the door (Wait/Blocked).
* When Thread A comes out, Thread B immediately sees the door is free and goes in.

Does that help clarify the "waiting" behavior, or were you thinking of a scenario where you might *want* it to fail instead?

-------------------------------------------------------------------------


You’ve hit on one of the classic trade-offs in multi-threaded programming: **Fairness vs. Throughput.**

Strictly speaking, yes, it can feel "unfair." The default behavior of the `synchronized` keyword is **non-fair**. When the lock is released, the JVM doesn't care who waited the longest; it just picks one thread from the "Entry Set" to grant access.

Here is why Java works this way and how you can get FIFO if you really need it.

---

## 1. Why Non-Fair (Default) is usually "Better"
It sounds counter-intuitive, but non-fair locks often result in **higher performance**.

* **The "Barging" Effect:** If Thread A releases a lock, and Thread B is already "awake" and running on a CPU core, it can grab the lock immediately and finish its work.
* **The Cost of FIFO:** If the JVM was forced to use FIFO, and the "next" thread in line (Thread C) is currently suspended or "asleep," the CPU has to wait for Thread C to be woken up and context-switched back into action. During those few microseconds of waking up Thread C, the lock sits empty and useless.
* **Throughput:** By allowing "barging," the JVM ensures the lock is almost always held by *someone* who is ready to work, rather than waiting for a specific person to wake up.

---

## 2. When "Unfairness" becomes a Problem: Starvation
The downside of this approach is **Thread Starvation**.

If you have a constant stream of new, "fast" threads arriving, a "slow" thread that has been waiting for a long time might keep getting jumped in line. In extreme cases, that thread might wait indefinitely because it's never the "lucky" one picked when the lock opens.

---

## 3. How to get FIFO (The Fair Lock)
If your specific use case requires that threads are served in the exact order they arrived, you cannot use the `synchronized` keyword. You must use **`ReentrantLock`** with the fairness flag set to `true`.

```java
// This ensures FIFO (First-In-First-Out)
private final ReentrantLock lock = new ReentrantLock(true); 

public void doWork() {
    lock.lock();
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
}
```



---

## 4. Comparison Summary

| Feature | `synchronized` | `ReentrantLock(false)` | `ReentrantLock(true)` |
| :--- | :--- | :--- | :--- |
| **Ordering** | Non-Fair (Random-ish) | Non-Fair (Barging allowed) | **FIFO (Strict Order)** |
| **Performance** | High | High | Lower (Context switch overhead) |
| **Starvation Risk** | Possible | Possible | **Eliminated** |

### Which should you choose?
* **Use `synchronized`** for 95% of cases. The JVM's "unfair" optimization is usually what you want for a fast, responsive app.
* **Use `ReentrantLock(true)`** only if you have a very specific requirement where a thread *must* be processed in order, or if you've detected that certain threads are actually starving and never getting a turn.

Does your current project involve a queue where order is critical, or are you mostly worried about threads getting "stuck" forever?

------------------------------------------------------------------------------------------------------------------------------

