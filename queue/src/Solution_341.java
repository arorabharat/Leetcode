import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


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
}
