class Solution_1603 {

    /**
     * Bad solution
     */
    static class ParkingSystem {

        private int small;
        private int medium;
        private int big;


        public ParkingSystem(int big, int medium, int small) {
            this.small = small;
            this.medium = medium;
            this.big = big;
        }

        public boolean addCar(int carType) {
            switch (carType) {
                case 1:
                    if (big > 0) {
                        big--;
                        return true;
                    } else {
                        return false;
                    }
                case 2:
                    if (medium > 0) {
                        medium--;
                        return true;
                    } else {
                        return false;
                    }
                case 3:
                    if (small > 0) {
                        small--;
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }
        }
    }

    /**
     * Decent solution
     */
    static class ParkingSystem1 {

        private final int[] counts;

        public ParkingSystem1(int big, int medium, int small) {
            this.counts = new int[3];
            this.counts[0] = big;
            this.counts[1] = medium;
            this.counts[2] = small;
        }

        public boolean addCar(int carType) {
            if (this.counts[carType - 1] > 0) {
                this.counts[carType - 1]--;
                return true;
            }
            return false;
        }
    }
}

