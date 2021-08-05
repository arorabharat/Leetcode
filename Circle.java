class Circle {

    private final int radius;
    private final int x;

    public Circle(int radius) {
        this.radius = radius;
        // anonymous inner class
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("bharat");
            }
        });
        this.x = 5;
    }

    public float area() {
        return this.radius * this.radius;
    }

    public float perimeter() {
        return (float) (2 * 3.14 * this.radius);
    }
}
