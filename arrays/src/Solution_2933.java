import java.util.*;

/**
 * https://leetcode.com/problems/high-access-employees/?envType=company&envId=atlassian&favoriteSlug=atlassian-more-than-six-months
 */
public class Solution_2933 {


    // approach 1
    class Solution1 {

        static class Time implements Comparable<Time> {

            Integer hh;
            Integer mm;

            public Time(Integer hh, Integer mm) {
                this.hh = hh;
                this.mm = mm;
            }

            public Time(String time) {
                if (time.length() != 4) {
                    throw new RuntimeException();
                }
                this.hh = Integer.valueOf(time.substring(0, 2));
                this.mm = Integer.valueOf(time.substring(2, 4));
            }

            @Override
            public String toString() {
                return "Time{" +
                        "hh=" + hh +
                        ", mm=" + mm +
                        '}';
            }

            @Override
            public int compareTo(Time o) {
                if (o.hh.equals(this.hh)) {
                    return Integer.compare(this.mm, o.mm);
                } else {
                    return Integer.compare(this.hh, o.hh);
                }
            }
        }

        private final Map<String, List<Time>> accessMap = new HashMap<>();

        private boolean withOneHour(Time t1, Time t2) {
            if (t1.hh.equals(t2.hh)) {
                return true;
            } else if (t1.hh.equals(t2.hh - 1)) {
                return (60 - t1.mm) + t2.mm < 60;
            } else {
                return false;
            }
        }

        public List<String> findHighAccessEmployees(List<List<String>> access_times) {

            for (List<String> access : access_times) {
                if (access.isEmpty()) {
                    continue;
                }
                String id = access.get(0);
                int n = access.size();
                accessMap.putIfAbsent(id, new ArrayList<>());
                for (int i = 1; i < n; i++) {
                    accessMap.get(id).add(new Time(access.get(i)));
                }
            }
            accessMap.keySet().forEach(id -> Collections.sort(accessMap.get(id)));
            List<String> result = new ArrayList<>();
            for (String id : accessMap.keySet()) {
                Deque<Time> q = new LinkedList<>();
                for (Time time : accessMap.get(id)) {
                    while (!q.isEmpty() && !withOneHour(q.getFirst(), time)) {
                        q.removeFirst();
                    }
                    q.add(time);
                    if (q.size() > 2) {
                        result.add(id);
                        break;
                    }
                }
            }
            return result;
        }
    }

    class Solution2 {

        private final Map<String, List<Integer>> accessMap = new HashMap<>();

        Integer converTime(String time) {
            int hh = Integer.parseInt(time.substring(0, 2));
            int mm = Integer.parseInt(time.substring(2, 4));
            return 20*hh + mm;
        }

        public List<String> findHighAccessEmployees(List<List<String>> access_times) {

           for (List<String> access : access_times) {
               if(access.isEmpty()){
                   continue;
               }
               String id = access.get(0);
               accessMap.putIfAbsent(id, new ArrayList<>());
               for (int i = 1; i < access.size(); i++) {
                   int time = converTime(access.get(i));
                   accessMap.get(id).add(time);
               }
           }
           accessMap.keySet().forEach(id -> Collections.sort(accessMap.get(id)));
           accessMap.keySet().forEach(System.out::println);
           List<String> highAccessEmployees = new ArrayList<>();
           for(String id : accessMap.keySet()){
               Queue<Integer> q = new LinkedList<>();
               for(Integer time : accessMap.get(id)) {
                   while ( !q.isEmpty() && (time - q.peek()) >= 60 ) {
                       q.remove();
                   }
                   q.add(time);
                   if(q.size() > 2) {
                       highAccessEmployees.add(id);
                   }
               }
           }
           return highAccessEmployees;
        }
    }
}
