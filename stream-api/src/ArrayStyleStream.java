import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class ArrayStyleStream {

    public static void printNumbersUsingStream() {
        System.out.println("printNumbersUsingStream :");
        IntStream.range(0, 3).forEach(i -> System.out.println(i));
    }

    public static void printNumbersUsingStreamUsingIntConsumer() {
        System.out.println("printNumbersUsingStreamUsingIntConsumer :");
        IntStream.range(0, 3).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        }.andThen(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println("After :" + value);
            }
        }));
    }

    public static void printNumbersUsingStreamAndLambda() {
        System.out.println("printNumbersUsingStreamAndLambda :");
        IntStream.range(0, 3).forEach(System.out::println);
    }

    public static void main(String[] args) {
        printNumbersUsingStreamUsingIntConsumer();
        printNumbersUsingStream();
        printNumbersUsingStreamAndLambda();
    }
}
