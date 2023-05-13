package de.cofinpro.trafficlights.controller;

import de.cofinpro.trafficlights.io.ConsolePrinter;
import de.cofinpro.trafficlights.model.TrafficLights;

import java.util.Map;
import java.util.Scanner;

/**
 * Controller Class for the traffic lights manager app providing the main menu functionality.
 */
public class MainMenuController implements Runnable {

    private static final String WELCOME_TEXT = "Welcome to the traffic management system!";
    private static final String MENU_TEXT = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""";

    private final ConsolePrinter printer;
    private final Scanner scanner;
    private TrafficLights trafficLights;

    public MainMenuController(ConsolePrinter printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    /**
     * entry point method doing the menu loop and triggering the actions chosen.
     */
    @Override
    public void run() {
        printer.printInfo(WELCOME_TEXT);
        setupTrafficLights();
        mainMenuLoop();
        printer.printInfo("Bye!");
    }

    private void mainMenuLoop() {
        var choice = getMenuChoice();
        while (choice != Choice.QUIT) {
            getMenuAction(choice).run();
            choice = getMenuChoice();
        }
    }

    private Runnable getMenuAction(Choice choice) {
        return Map.<Choice, Runnable>of(
                Choice.ADD_ROAD, this::addRoad,
                Choice.DELETE_ROAD, this::deleteRoad,
                Choice.OPEN_SYSTEM, this::openSystem
        ).get(choice);
    }

    private void addRoad() {
        trafficLights.addRoad();
        printer.printInfo("Road added");
    }

    private void deleteRoad() {
        trafficLights.deleteRoad();
        printer.printInfo("Road deleted");
    }

    private void openSystem() {
        printer.printInfo("System opened");
    }

    private Choice getMenuChoice() {
        printer.printInfo(MENU_TEXT);
        return Choice.values()[Integer.parseInt(scanner.nextLine())];
    }

    private void setupTrafficLights() {
        var numberOfRoads = queryNumber("Input the number of roads: ");
        var interval = queryNumber("Input the interval: ");
        trafficLights = new TrafficLights(numberOfRoads, interval);
    }

    private int queryNumber(String queryMessage) {
        printer.printInfo(queryMessage);
        return Integer.parseInt(scanner.nextLine());
    }

    private enum Choice {
        QUIT, ADD_ROAD, DELETE_ROAD, OPEN_SYSTEM
    }
}
