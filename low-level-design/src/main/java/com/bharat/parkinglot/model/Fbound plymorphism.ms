public interface ParkingStrategy<T extends ParkingStrategy<T>> {
    T strategy(String strategy);
    String strategy();
}

public class MinHeapParkingStrategy implements ParkingStrategy<MinHeapParkingStrategy>{
    @Override
    public MinHeapParkingStrategy strategy(String strategy) {
        return null;
    }

    @Override
    public String strategy() {
        return null;
    }
}