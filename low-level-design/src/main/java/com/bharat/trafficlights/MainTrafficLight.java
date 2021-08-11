package com.bharat.trafficlights;

import java.util.HashMap;

public class MainTrafficLight extends TrafficLight {

    MainTrafficLight() {
        lights = new HashMap<>();
        lights.put(Color.RED, new Light(Color.RED));
        lights.put(Color.YELLOW, new Light(Color.YELLOW));
        lights.put(Color.GREEN, new Light(Color.GREEN));
    }

    public boolean turnOnRed() {
        setLightStatus(Color.RED, true);
        setLightStatus(Color.YELLOW, false);
        setLightStatus(Color.GREEN, false);
        return true;
    }

    public boolean turnOnYellow() {
        setLightStatus(Color.RED, false);
        setLightStatus(Color.YELLOW, true);
        setLightStatus(Color.GREEN, false);
        return true;
    }

    public boolean turnOnGreen() {
        setLightStatus(Color.RED, false);
        setLightStatus(Color.YELLOW, false);
        setLightStatus(Color.GREEN, true);
        return true;
    }
}
