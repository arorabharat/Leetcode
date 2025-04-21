public class Solution_622 {

    class MyCircularQueue {

        private final int[] arr;
        private final int size;
        private int front;
        private int rear;
        private int count;


        public MyCircularQueue(int k) {
            arr = new int[k];
            count = 0;
            size = k;
            rear = -1;
        }

        public boolean enQueue(int value) {
            if (isFull()) return false;
            rear = (rear == size - 1) ? 0 : rear + 1;
            arr[rear] = value;
            count++;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) return false;
            arr[front] = -1;
            front = (front == size - 1) ? 0 : front + 1;
            count--;
            return true;
        }

        public int Front() {
            if (isEmpty()) return -1;
            return arr[front];
        }

        public int Rear() {
            if (isEmpty()) return -1;
            return arr[rear];
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean isFull() {
            return count == size;
        }
    }
}
