package com.bharat.trafficlights;

import java.util.HashMap;

public class PedestrianTrafficLight extends TrafficLight {

    private boolean isButtonPushed;

    PedestrianTrafficLight() {
        lights = new HashMap<>();
        lights.put(Color.RED, new Light(Color.RED));
        lights.put(Color.GREEN, new Light(Color.GREEN));
    }

    public boolean isButtonPushed() {
        return isButtonPushed;
    }

    public void setButtonPushed(boolean buttonPushed) {
        isButtonPushed = buttonPushed;
    }

    public boolean turnOnRed() {
        setLightStatus(Color.RED, true);
        setLightStatus(Color.GREEN, false);
        return true;
    }

    public boolean turnOnGreen() {
        setLightStatus(Color.GREEN, this.isButtonPushed());
        setLightStatus(Color.RED, !this.isButtonPushed());
        return this.isButtonPushed();
    }


}
