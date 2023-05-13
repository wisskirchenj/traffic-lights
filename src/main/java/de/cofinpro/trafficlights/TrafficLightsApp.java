package de.cofinpro.trafficlights;

import de.cofinpro.trafficlights.controller.MainMenuController;
import de.cofinpro.trafficlights.io.ConsolePrinter;

import java.util.Scanner;

public class TrafficLightsApp {
    public static void main(String[] args) {
        new MainMenuController(new ConsolePrinter(), new Scanner(System.in)).run();
    }
}
