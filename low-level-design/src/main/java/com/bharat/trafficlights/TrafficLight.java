package com.bharat.trafficlights;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// can two different lights be on at the same time
public abstract class TrafficLight {

    protected Map<Color, Light> lights;

    public List<Light> getOnLights() {
        List<Light> onLights = new ArrayList<>();
        for (Color color : lights.keySet()) {
            if (lights.get(color).isStatus()) {
                onLights.add(lights.get(color));
            }
        }
        return onLights;
    }

    protected void setLightStatus(Color color, boolean status) {
        if (!lights.containsKey(color)) {
            throw new IllegalArgumentException("Color is not supported");
        }
        lights.get(color).setStatus(status);
    }
}
