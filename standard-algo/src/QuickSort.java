import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    // Public method to sort an array using Quick Sort
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    // Internal recursive method
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    // Lomuto partition scheme: pivot = arr[high]
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Utility method to swap two elements in the array
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // Optional: Quick Sort with randomized pivot to avoid worst-case on already sorted input
    public static void quickSortRandomized(int[] arr) {
        Random rand = new Random();
        quickSortRandomized(arr, 0, arr.length - 1, rand);
    }

    private static void quickSortRandomized(int[] arr, int low, int high, Random rand) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high, rand);
            quickSortRandomized(arr, low, pivotIndex - 1, rand);
            quickSortRandomized(arr, pivotIndex + 1, high, rand);
        }
    }

    private static int randomizedPartition(int[] arr, int low, int high, Random rand) {
        int randomIndex = low + rand.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        return partition(arr, low, high);
    }

    // Example usage
    public static void main(String[] args) {
        int[] data = {9, 3, 4, 2, 7, 5, 8, 1, 6};

        System.out.println("Original array: " + Arrays.toString(data));

        // Uncomment one of the following lines to choose the sorting method:

        // Basic Quick Sort (Lomuto partition)
        quickSort(data);

        // Quick Sort with randomized pivot
        // quickSortRandomized(data);

        System.out.println("Sorted array:   " + Arrays.toString(data));
    }
}
