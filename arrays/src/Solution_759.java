import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution_759 {
    class Solution1 {

        class Stamp {
            int v;
            int o;

            Stamp(int v, int o) {
                this.v = v;
                this.o = o;
            }
        }

        class Interval {
            public int start;
            public int end;

            public Interval() {
            }

            public Interval(int _start, int _end) {
                start = _start;
                end = _end;
            }
        }


        public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
            List<Stamp> flat = new ArrayList<>();

            for (List<Interval> emp : schedule) {
                for (Interval i : emp) {
                    flat.add(new Stamp(i.start, 1));
                    flat.add(new Stamp(i.end, -1));
                }
            }
            Collections.sort(flat, (a, b) -> a.v == b.v ? Integer.compare(b.o, a.o) : Integer.compare(a.v, b.v));
            List<Interval> free = new ArrayList<>();
            int start = -1;
            int end = -1;
            int count = 0;
            int prevCount = -1;
            for (Stamp s : flat) {

                count = count + s.o;
                if (count == 0 && start == -1) {
                    start = s.v;
                }
                if (prevCount == 0 && count > 0) {
                    end = s.v;
                }
                prevCount = count;

                if (start != -1 && end != -1) {
                    free.add(new Interval(start, end));
                    start = -1;
                    end = -1;
                }
            }
            return free;
        }
    }
}
