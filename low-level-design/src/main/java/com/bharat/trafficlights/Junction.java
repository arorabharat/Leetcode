package com.bharat.trafficlights;

import java.util.ArrayList;
import java.util.List;

public class Junction {

    private List<GroupTrafficLight> trafficLights;

    private static final int TOTAL_LIGHTS = 4;

    public Junction(List<GroupTrafficLight> trafficLights) {
        if (trafficLights.size() != TOTAL_LIGHTS)
            throw new IllegalArgumentException("Invalid number of traffic lights");
        this.trafficLights = trafficLights;
    }

    public Junction() {
        this.trafficLights = new ArrayList<>();
        for (int i = 0; i < TOTAL_LIGHTS; i++) {
            MainTrafficLight mainTrafficLight = new MainTrafficLight();
            PedestrianTrafficLight pedestrianTrafficLight = new PedestrianTrafficLight();
            this.trafficLights.add(new GroupTrafficLight(mainTrafficLight, pedestrianTrafficLight));
        }
    }


}
