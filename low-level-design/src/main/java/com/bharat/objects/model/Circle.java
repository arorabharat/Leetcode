package com.bharat.objects.model;

// immutable class are better
public class Circle implements TwoDimensionalShape {

    private final int radius;
    private final int x;

    public Circle(int radius) {
        this.radius = radius;
        // anonymous inner class has the reference to the this class
        // immutable constraint is only satisfied when class is properly constructed.
        new Thread(() -> System.out.println("bharat"));
        this.x = 5;
    }

    @Override
    public float area() {
        return this.radius * this.radius;
    }

    @Override
    public float perimeter() {
        return (float) (2 * 3.14 * this.radius);
    }
}
