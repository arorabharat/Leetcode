import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution_759 {

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

    class Solution1 {

        class Stamp {
            int v;

            int o;

            Stamp(int v, int o) {
                this.v = v;
                this.o = o;
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

    class Solution2 {

        class Stamp {
            int time;
            int delta;

            Stamp(int t, int d) {
                time = t;
                delta = d;
            }
        }

        public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {

            List<Stamp> events = new ArrayList<>();

            for (List<Interval> emp : schedule) {
                for (Interval in : emp) {
                    events.add(new Stamp(in.start, 1));
                    events.add(new Stamp(in.end, -1));
                }
            }

            // OPEN before CLOSE when same time
            Collections.sort(events, (a, b) ->
                    a.time == b.time
                            ? b.delta - a.delta
                            : a.time - b.time
            );

            List<Interval> res = new ArrayList<>();

            int count = 0;
            Integer freeStart = null;

            for (Stamp e : events) {

                int prev = count;
                count += e.delta;

                if (count == 0)
                    freeStart = e.time;

                else if (prev == 0 && freeStart != null)
                    res.add(new Interval(freeStart, e.time));
            }

            return res;
        }
    }
}
