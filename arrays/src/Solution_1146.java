import java.util.ArrayList;
import java.util.List;

public class Solution_1146 {

    class SnapshotArray1 {

        List<int[]> snapshots;
        int[] arr;
        int length;

        public void SnapshotArray(int length) {
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

}
