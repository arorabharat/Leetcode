public class Item_1 {
    // consider static factory methods

    public static void main(String[] args) {
        // prefer static factory method over the constructor style
        PairConstructorStyle pairConstructorStyle = new PairConstructorStyle(1, 2);
        PairStaticFactoryStyle pairStaticFactoryStyle = PairStaticFactoryStyle.createPairFromInt(1, 2);
        // factory style is more descriptive in nature

        // In constructor style every time constructor is called a new object will be created.
        // on the other hand , in static style pattern - you can use lazy loading , caching like flyweight pattern

        // flyweight pattern example - here value of function do not create a new object every time
        Boolean condition = Boolean.valueOf("false");

        // static factory provide instance control
        // instance controlled classes are the classes which have control over there number of instances
        // Example singleton class or non instantiable class
        // immutable classes like string are classes which ensure that there does not exist a string such that
        // a.equals(b) and a != b ( reference comparison), this is also an example of instance control class


    }

    static class PairConstructorStyle {
        int x;
        int y;

        public PairConstructorStyle(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public PairConstructorStyle(String pair) throws NumberFormatException {
            String[] arr = pair.split(" ");
            if (arr.length != 2) {
                throw new NumberFormatException("String format should be : (<x><space><y>)");
            }
            this.x = Integer.parseInt(arr[0]);
            this.y = Integer.parseInt(arr[1]);
        }
    }

    static class PairStaticFactoryStyle {
        int x;
        int y;

        public static PairStaticFactoryStyle createPairFromInt(int x, int y) {
            PairStaticFactoryStyle pairStaticFactoryStyle = new PairStaticFactoryStyle();
            pairStaticFactoryStyle.setX(x);
            pairStaticFactoryStyle.setY(y);
            return pairStaticFactoryStyle;
        }

        public static PairStaticFactoryStyle parsePair(String pair) throws NumberFormatException {
            String[] arr = pair.split(" ");
            if (arr.length != 2) {
                throw new NumberFormatException("String format should be : (<x><space><y>)");
            }
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            PairStaticFactoryStyle pairStaticFactoryStyle = new PairStaticFactoryStyle();
            pairStaticFactoryStyle.setX(x);
            pairStaticFactoryStyle.setY(y);
            return pairStaticFactoryStyle;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}

/*

EnumSet
JumboEnumSet
Collections
Integer and other wrapper classes
StringBuilder
StringBuffer
List
Map
Set
Comparable
Comparator

 */
