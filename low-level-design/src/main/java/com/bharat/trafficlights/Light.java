package com.bharat.trafficlights;

public class Light {

    private final Color color;
    private boolean status;

    public Light(Color color) {
        this.color = color;
        this.status = false;
    }

    public Color getColor() {
        return color;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean on) {
        status = on;
    }
}
