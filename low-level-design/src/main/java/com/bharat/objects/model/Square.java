package com.bharat.objects.model;

public class Square implements TwoDimensionalShape {

    private final int edge;

    public Square(int edge) {
        this.edge = edge;
    }

    @Override
    public float area() {
        return this.edge * this.edge;
    }

    @Override
    public float perimeter() {
        return 4 * this.edge;
    }
}
