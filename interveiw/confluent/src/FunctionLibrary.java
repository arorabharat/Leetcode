import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


class Function {
    String name;
    List<String> argumentTypes;
    boolean isVariadic;
}

public class FunctionLibrary {

    class Node {
        Map<String, Node> children = new ConcurrentHashMap<>();
        boolean isVariadicNode;
        boolean isEndNode;
        List<Function> functionNames = new ArrayList<>();
    }

    class Trie {

        private final Node root = new Node();

        void insert(Function function) {
            Node tr = root;
            for (String type : function.argumentTypes) {
                if (!tr.children.containsKey(type)) {
                    tr.children.put(type, new Node());
                }
                tr = tr.children.get(type);
            }
            tr.isEndNode = true;
            tr.functionNames.add(function);
            tr.isVariadicNode = function.isVariadic;
        }

        void dfs(Node tr, List<String> types, List<Function> functions, int t, String lastMatchedType) {
            if (t == types.size()) {
                if (tr.isEndNode) {
                    functions.addAll(tr.functionNames);
                }
            }
            if (tr.isVariadicNode) {
                int nt = t;
                while (nt < types.size() && types.get(nt).equals(lastMatchedType)) {
                    nt++;
                }
                if (nt == types.size()) {
                    functions.addAll(tr.functionNames);
                }
            }
            String currType = types.get(t);
            if (!tr.children.containsKey(currType)) {
                return;
            }
            dfs(tr.children.get(currType), types, functions, t + 1, currType);
        }

        List<Function> find(List<String> types) {
            List<Function> functions = new ArrayList<>();
            dfs(root, types, functions, 0, null);
            return functions;
        }
    }

    private final Trie trie = new Trie();

    public void register(Set<Function> functionSet) {
        // store functions
        for (Function function : functionSet) {
            trie.insert(function);
        }
    }

    public List<Function> findMatches(List<String> argumentTypes) {
        // return all compatible functions
        return trie.find(argumentTypes);
    }
}
