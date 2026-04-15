The exact question you mentioned appears in multiple interview archives (especially Confluent / API design style screens). Below is the **canonical version** of the problem along with variants, constraints, and interviewer expectations.

---

# Canonical Interview Question (Phone Screen)

## Problem Statement

Design a function library that supports registering functions with typed parameters and retrieving all functions that match a given list of argument types.

Each function has:

* function name
* ordered list of parameter types
* flag indicating whether the function is variadic (last argument can repeat)

Return all compatible functions for a given input argument list.

---

## Exact Version (commonly cited)

A frequently referenced formulation:

```java
class Function {
    String name;
    List<String> argumentTypes;
    boolean isVariadic;
}

class FunctionLibrary {

    public void register(Set<Function> functionSet) {
        // store functions
    }

    public List<Function> findMatches(List<String> argumentTypes) {
        // return all compatible functions
    }
}
```

Rules:

1. Order matters
2. Types must match exactly
3. If `isVariadic = true`, last parameter type can repeat 1..N times
4. Return all functions that match the provided argument types

Example dataset:

| Function | Signature                   | Variadic |
| -------- | --------------------------- | -------- |
| FuncA    | [String, Integer, Integer]  | false    |
| FuncB    | [String, Integer]           | true     |
| FuncC    | [Integer]                   | true     |
| FuncD    | [Integer, Integer]          | true     |
| FuncE    | [Integer, Integer, Integer] | false    |
| FuncF    | [String]                    | false    |
| FuncG    | [Integer]                   | false    |

Queries:

```
findMatches([String]) 
→ [FuncF]

findMatches([Integer]) 
→ [FuncC, FuncG]

findMatches([Integer, Integer, Integer, Integer]) 
→ [FuncC, FuncD]

findMatches([String, Integer, Integer, Integer]) 
→ [FuncB]

findMatches([String, Integer, Integer]) 
→ [FuncA, FuncB]
```

Source reference: ([LeetCode][1])

---

# How Interviewers Usually Phrase It

### Version 1 (API design style)

Design an API that supports:

```
register(functionName, paramTypes[], isVariadic)

search(inputTypes[])
```

Return matching function overloads.

---

### Version 2 (compiler style)

Given multiple overloaded function signatures, implement resolution logic similar to how compilers choose the correct method overload.

---

### Version 3 (type system focus)

Given a list of function signatures:

```
foo(int)
foo(string)
foo(int, int)
foo(int...)
foo(string, int...)
```

Find which overloads match:

```
foo(int, int, int)
```

---

### Version 4 (object-oriented flavor)

Implement function overloading resolution engine.

Focus:

* signature parsing
* compatibility rules
* variadic handling
* efficient lookup

---



# Core Concepts Being Tested

### 1. Type compatibility

Exact match vs flexible match

Example rules:

| Input            | Signature       | Match |
| ---------------- | --------------- | ----- |
| [int]            | [int]           | yes   |
| [int,int,int]    | [int...]        | yes   |
| [int,string]     | [int...]        | no    |
| [string,int,int] | [string,int...] | yes   |

---

### 2. Variadic matching logic

Variadic functions allow last parameter to repeat multiple times. ([Naukri][2])

Example:

```
func(String, Integer...)
```

matches:

```
(String, Integer)
(String, Integer, Integer)
(String, Integer, Integer, Integer)
```

---

### 3. Signature parsing

Convert function representation into structured form:

```
"foo(int,string,int)"
→ ["int","string","int"]
```

---

### 4. Edge cases interviewers test

#### Edge case 1

Empty arguments

```
func()
func(int...)
```

Does variadic allow zero args?

Clarify assumption.

---

#### Edge case 2

Multiple variadic overloads

```
func(int...)
func(int,int...)
```

Which takes precedence?

---

#### Edge case 3

Ambiguous matches
Return:

* all matches
* best match
* priority ranking

depending on interviewer requirement.

---

# Expected Solution Approach

## Brute force (expected in phone screen)

For each function:

```
for each function f:
    if matches(f.signature, input):
        add to result
```

Matching logic:

```
if not variadic:
    size must match exactly

if variadic:
    input length >= fixed part length
    remaining arguments must match last type
```

Time complexity:

```
O(F * P)

F = number of functions
P = number of parameters
```

---

## Optimized approach (follow-up discussion)

Index functions by:

```
Map<paramCount, List<Function>>
Map<firstParamType, List<Function>>
Trie based on parameter types
```

Reduces search space.

---

# Similar Interview Sources / Variants

### Confluent phone screen problem

Design function library with variadic matching. ([hack2hire.com][3])

### Type system interviews

Often connected to:

* generics
* function overloading
* type inference ([DataCamp][4])

---

# How Interviewers Evaluate

| Skill                | What they observe                          |
| -------------------- | ------------------------------------------ |
| problem modeling     | ability to convert signatures to structure |
| clean matching logic | handling edge cases                        |
| extensibility        | generics, inheritance                      |
| complexity awareness | avoiding exponential checks                |
| communication        | clarifying ambiguous cases                 |

---

# If useful, I can also provide

1. Full optimal Java solution
2. Follow-up variations asked in FAANG
3. Similar LeetCode-style problems
4. Trie-based optimized design
5. Extension supporting subtyping (Number → Integer)
6. Real interview experiences where this appeared

[1]: https://leetcode.com/discuss/interview-question/759611/confluent-senior-software-engineer-phone-interview/?utm_source=chatgpt.com "Confluent | Senior Software Engineer | Phone interview"
[2]: https://www.naukri.com/code360/library/go-variadic-function?utm_source=chatgpt.com "Variadic functions - Naukri Code 360"
[3]: https://www.hack2hire.com/companies/confluent/coding-questions/677b2d9378175fce2ecdfc0f/practice?utm_source=chatgpt.com "Variadic Function Matching | Confluent"
[4]: https://www.datacamp.com/blog/typescript-interview-questions?utm_source=chatgpt.com "Top 40 TypeScript Interview Questions and Answers for 2026"
