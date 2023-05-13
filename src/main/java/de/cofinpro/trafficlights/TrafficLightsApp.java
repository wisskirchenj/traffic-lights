package de.cofinpro.trafficlights;

import de.cofinpro.trafficlights.controller.MainMenuController;
import de.cofinpro.trafficlights.io.ConsolePrinter;

public class TrafficLightsApp {
    public static void main(String[] args) {
        new MainMenuController(new ConsolePrinter()).run();
    }
}
