import java.util.*;


public class Solution_341 {


    class Solution1 {


        public class NestedIterator implements Iterator<Integer> {

            private final Queue<Integer> queue = new LinkedList<>();
            private final Iterator<Integer> iterator;

            public NestedIterator(List<NestedInteger> nestedList) {
                flatList(nestedList);
                iterator = queue.iterator();
            }

            private void flatList(List<NestedInteger> nestedList) {
                for (NestedInteger nestedInteger : nestedList) {
                    if (nestedInteger.isInteger()) {
                        queue.add(nestedInteger.getInteger());
                    } else {
                        List<NestedInteger> subNestedList = nestedInteger.getList();
                        flatList(subNestedList);
                    }
                }
            }

            @Override
            public Integer next() {
                return iterator.next();
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
        }
    }

    class Solution2 {

        public class NestedIterator implements Iterator<Integer> {

            private final Queue<NestedInteger> queue = new LinkedList<>();
            private final Iterator<NestedInteger> iterator;

            public NestedIterator(List<NestedInteger> nestedList) {
                this.iterator = nestedList.iterator();
            }

            @Override
            public Integer next() {
                NestedInteger nextInteger;
                while (hasNext()) {
                    nextInteger = (queue.isEmpty()) ? iterator.next() : queue.poll();
                    if (nextInteger.isInteger()) {
                        return nextInteger.getInteger();
                    } else {
                        List<NestedInteger> list = nextInteger.getList();
                        if (!list.isEmpty()) {
                            queue.addAll(list);
                        }
                    }
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty() || iterator.hasNext();
            }
        }
    }

    class Solution3 {

        public interface NestedInteger {
            // @return true if this NestedInteger holds a single integer, rather than a nested list.
            public boolean isInteger();

            // @return the single integer that this NestedInteger holds, if it holds a single integer
            // Return null if this NestedInteger holds a nested list
            public Integer getInteger();

            // @return the nested list that this NestedInteger holds, if it holds a nested list
            // Return empty list if this NestedInteger holds a single integer
            public List<NestedInteger> getList();
        }

        public class NestedIterator implements Iterator<Integer> {

            Stack<List<NestedInteger>> listsStack;
            Stack<Integer> listIteratorStack;

            public NestedIterator(List<NestedInteger> nestedList) {
                this.listsStack = new Stack<>();
                this.listIteratorStack = new Stack<>();
                if (!nestedList.isEmpty()) {
                    listsStack.add(nestedList);
                    listIteratorStack.add(0);
                }
            }

            private boolean _hasNext() {
                if (listsStack.isEmpty()) {
                    return false;
                }
                List<NestedInteger> nestedList = listsStack.peek();
                Integer it = listIteratorStack.peek();
                NestedInteger nestedInteger = nestedList.get(it);
                if (nestedInteger.isInteger()) {
                    return true;
                } else {
                    it++;
                    listIteratorStack.pop();
                    if (it == nestedList.size()) {
                        listsStack.pop();
                    } else {
                        listIteratorStack.push(it);
                    }
                    List<NestedInteger> nestedSubList = nestedInteger.getList();
                    if (!nestedSubList.isEmpty()) {
                        listsStack.add(nestedSubList);
                        listIteratorStack.add(0);
                    }
                    return _hasNext();
                }
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    return null;
                }
                List<NestedInteger> nestedList = listsStack.peek();
                Integer it = listIteratorStack.pop();
                NestedInteger nestedInteger = nestedList.get(it);
                it++;
                if (it == nestedList.size()) {
                    listsStack.pop();
                } else {
                    listIteratorStack.push(it);
                }
                if (nestedInteger.isInteger()) {
                    return nestedInteger.getInteger();
                } else {
                    throw new RuntimeException("Invalid state");
                }
            }

            @Override
            public boolean hasNext() {
                return _hasNext();
            }
        }

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
    }
}
