---

## ⚖️ **1. The Big Picture**

In Java, exceptions are divided into **two main categories**:

| Type                      | Must be handled?                     | Examples                                                                        | Extends            |
| ------------------------- | ------------------------------------ | ------------------------------------------------------------------------------- | ------------------ |
| ✅ **Checked Exception**   | Yes (compiler checks)                | `IOException`, `SQLException`, `FileNotFoundException`                          | `Exception`        |
| ❌ **Unchecked Exception** | No (compiler doesn’t force handling) | `NullPointerException`, `ArithmeticException`, `ArrayIndexOutOfBoundsException` | `RuntimeException` |

---

## 🧠 **2. Checked Exceptions (Compile-time)**

### 🔍 Meaning:

* These are **checked at compile-time**.
* The compiler forces you to either **handle them with `try-catch`** or **declare them with `throws`**.

### 💡 Example:

```java
import java.io.*;

public class CheckedExample {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("abc.txt"); // file may not exist
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
```

If you **don’t handle** this exception, the compiler will show:

```
error: unreported exception FileNotFoundException; must be caught or declared to be thrown
```

✅ Checked exceptions represent **recoverable problems** — something that could go wrong but can be fixed (like retrying a file read).

---

## ⚡ **3. Unchecked Exceptions (Runtime)**

### 🔍 Meaning:

* These are **not checked by the compiler**.
* You can handle them — but you’re **not forced** to.
* They occur due to **logical/programming errors**.

### 💡 Example:

```java
public class UncheckedExample {
    public static void main(String[] args) {
        int a = 10 / 0;  // ArithmeticException
        System.out.println("This line will never run");
    }
}
```

No compile-time error — the program just crashes at runtime:

```
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

✅ Unchecked exceptions usually indicate **bugs** that should be fixed in code, not recovered at runtime.

---

## 🧱 **4. The Hierarchy (Visual)**

```
Throwable
 ├── Exception  ← Checked
 │    ├── IOException
 │    ├── SQLException
 │    └── etc.
 └── RuntimeException  ← Unchecked
      ├── NullPointerException
      ├── ArithmeticException
      ├── ArrayIndexOutOfBoundsException
      └── etc.
```

---

## 📋 **5. Quick Comparison Table**

| Aspect               | Checked                              | Unchecked                                     |
| -------------------- | ------------------------------------ | --------------------------------------------- |
| When detected        | Compile-time                         | Runtime                                       |
| Must handle/declare? | ✅ Yes                                | ❌ No                                          |
| Common cause         | External issues (files, DB, network) | Logic errors (null, divide by zero)           |
| Parent class         | `Exception`                          | `RuntimeException`                            |
| Example              | `IOException`, `SQLException`        | `NullPointerException`, `ArithmeticException` |

---

## 💬 **6. Rule of Thumb**

* If the program **can recover** → Use a **checked** exception.
* If it’s a **bug in your logic** → It’ll throw an **unchecked** exception.

---

Would you like me to show you a **side-by-side example** — one checked and one unchecked — in the same program to see how the compiler treats them differently?
