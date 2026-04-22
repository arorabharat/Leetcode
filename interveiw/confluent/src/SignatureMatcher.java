import java.util.*;

public class SignatureMatcher {

    // ---------------------------------------------------------
    // Helper Classes
    // ---------------------------------------------------------
    static class VarargEntry {
        String functionName;
        String varargType;

        VarargEntry(String functionName, String varargType) {
            this.functionName = functionName;
            this.varargType = varargType;
        }
    }

    static class TrieNode {
        Map<String, TrieNode> children = new HashMap<>();
        List<String> functions = new ArrayList<>(); // Exact matches ending here
        List<VarargEntry> varargFunctions = new ArrayList<>(); // Varargs starting here
    }

    private final TrieNode root = new TrieNode();

    // ---------------------------------------------------------
    // ADD FUNCTION
    // ---------------------------------------------------------
    /**
     * @param name Name of the function
     * @param params List of parameter types
     * @param isVarArg Whether the last parameter is a vararg (e.g., int...)
     */
    public void addFunction(String name, List<String> params, boolean isVarArg) {
        TrieNode node = root;

        if (isVarArg) {
            if (params.isEmpty()) {
                throw new IllegalArgumentException("Vararg must have at least one type.");
            }
            // Traverse up to the node BEFORE the vararg type
            for (int i = 0; i < params.size() - 1; i++) {
                String type = params.get(i);
                node.children.putIfAbsent(type, new TrieNode());
                node = node.children.get(type);
            }
            // Store the vararg rule at the parent node
            String varargType = params.get(params.size() - 1);
            node.varargFunctions.add(new VarargEntry(name, varargType));
        } else {
            // Standard exact match traversal
            for (String type : params) {
                node.children.putIfAbsent(type, new TrieNode());
                node = node.children.get(type);
            }
            node.functions.add(name);
        }
    }

    // ---------------------------------------------------------
    // SEARCH
    // ---------------------------------------------------------
    public List<String> search(List<String> queryParams) {
        List<String> result = new ArrayList<>();
        dfs(root, queryParams, 0, result);
        return result;
    }

    private void dfs(TrieNode node, List<String> query, int index, List<String> result) {
        // 1. Check Vararg Matches (Greedy)
        // This handles 0, 1, or N instances of the vararg type.
        for (VarargEntry v : node.varargFunctions) {
            if (matchesVararg(v.varargType, query, index)) {
                result.add(v.functionName);
            }
        }

        // 2. Base Case: End of query reached
        if (index == query.size()) {
            result.addAll(node.functions);
            return;
        }

        // 3. Recursive Step: Continue exact path traversal
        String currentType = query.get(index);
        if (node.children.containsKey(currentType)) {
            dfs(node.children.get(currentType), query, index + 1, result);
        }
    }

    private boolean matchesVararg(String varargType, List<String> query, int index) {
        // Check if all remaining query types match the vararg type
        for (int i = index; i < query.size(); i++) {
            if (!query.get(i).equals(varargType)) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------
    // DRIVER CODE
    // ---------------------------------------------------------
    public static void main(String[] args) {
        SignatureMatcher matcher = new SignatureMatcher();

        // Registering functions
        matcher.addFunction("foo1", Arrays.asList("int", "String"), false);
        matcher.addFunction("foo2", Arrays.asList("int"), true);       // int...
        matcher.addFunction("foo3", Arrays.asList("String"), true);    // String...
        matcher.addFunction("foo4", Arrays.asList("int", "int"), false);
        matcher.addFunction("foo5", new ArrayList<>(), false);        // zero-arg function

        System.out.println("--- Test Results ---");

        // Case 1: Multiple matches (Exact and Vararg)
        // Search: (int, int) -> Matches foo4 (exact) and foo2 (int...)
        System.out.println("Search [int, int]: " + matcher.search(Arrays.asList("int", "int")));

        // Case 2: Zero-length Vararg
        // Search: (empty) -> Matches foo2 (int... zero times) and foo3 (String... zero times) and foo5 (exact)
        System.out.println("Search []: " + matcher.search(new ArrayList<>()));

        // Case 3: Mixed types (Vararg should NOT match)
        // Search: (int, String) -> Matches foo1 (exact). foo2 (int...) fails because 'String' isn't 'int'.
        System.out.println("Search [int, String]: " + matcher.search(Arrays.asList("int", "String")));

        // Case 4: Long Vararg
        // Search: (String, String, String) -> Matches foo3
        System.out.println("Search [String, String, String]: " + matcher.search(Arrays.asList("String", "String", "String")));
    }
}