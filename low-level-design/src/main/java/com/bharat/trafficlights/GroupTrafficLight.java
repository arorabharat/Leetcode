package com.bharat.trafficlights;

public class GroupTrafficLight {

    MainTrafficLight mainTrafficLight;
    PedestrianTrafficLight pedestrianTrafficLight;

    public GroupTrafficLight(MainTrafficLight mainTrafficLight, PedestrianTrafficLight pedestrianTrafficLight) {
        this.mainTrafficLight = mainTrafficLight;
        this.pedestrianTrafficLight = pedestrianTrafficLight;
    }

    public TrafficLight getMainTrafficLight() {
        return mainTrafficLight;
    }

    public TrafficLight getPedestrianTrafficLight() {
        return pedestrianTrafficLight;
    }

    public void setMainTrafficLightToRed() {
        mainTrafficLight.turnOnRed();
        pedestrianTrafficLight.turnOnGreen();
    }

    public void setMainTrafficLightToYellow() {
        mainTrafficLight.turnOnYellow();
        pedestrianTrafficLight.turnOnGreen();
    }

    public void setMainTrafficLightToGreen() {
        mainTrafficLight.turnOnGreen();
        pedestrianTrafficLight.turnOnRed();
    }

}
