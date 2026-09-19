import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution_1146 {

    class SnapshotArray1 {

        List<int[]> snapshots;
        int[] arr;
        int length;

        public SnapshotArray1(int length) {
            snapshots = new ArrayList<>();
            arr = new int[length];
            this.length = length;
        }

        public void set(int index, int val) {
            arr[index] = val;
        }

        public int snap() {
            int[] arrCopy = new int[this.length];
            for (int i = 0; i < this.length; i++) {
                arrCopy[i] = arr[i];
            }
            snapshots.add(arrCopy);
            return this.snapshots.size() - 1;
        }

        public int get(int index, int snap_id) {
            return this.snapshots.get(snap_id)[index];
        }
    }

    class SnapshotArray2 {

        Map<Integer, Map<Integer, Integer>> snapshotIndex2Value;
        int snapshotCount;
        int[] arr;
        int length;
        Map<Integer, Integer> delta;

        public void SnapshotArray(int length) {
            this.snapshotIndex2Value = new HashMap<>();
            this.delta = new HashMap<>();
//            arr = new int[length];
//            this.length = length;
        }

        public void set(int index, int val) {
            this.delta.put(index, val);
        }

        public int snap() {
            this.snapshotCount++;
            // update the values which are changed
            for(int key : delta.keySet()) {
                snapshotIndex2Value.
            }
            return this.snapshotCount - 1;
        }

        public int get(int index, int snap_id) {
            Map<Integer, Integer> index2Value = this.snapshotIndex2Value.get(snap_id);
            if(index2Value == null) {
                return 0;
            }
            Integer val = index2Value.get(index);
            return val == null ? 0 : val;
        }
    }

}
