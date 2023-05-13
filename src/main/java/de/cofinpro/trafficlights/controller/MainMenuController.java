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
        printer.printInfoAndWaitForReturn(scanner,"Road added");
    }

    private void deleteRoad() {
        trafficLights.deleteRoad();
        printer.printInfoAndWaitForReturn(scanner, "Road deleted");
    }

    private void openSystem() {
        printer.printInfoAndWaitForReturn(scanner, "System opened");
    }

    private Choice getMenuChoice() {
        printer.clearAndPrintMenu(MENU_TEXT);
        var menuIndex = scanIntegerValidated("[0-3]", () -> {
            printer.printInfoAndWaitForReturn(scanner, "Incorrect option");
            printer.clearAndPrintMenu(MENU_TEXT);
        });
        return Choice.values()[menuIndex];
    }

    private void setupTrafficLights() {
        var numberOfRoads = queryNumber("Input the number of roads: ");
        var interval = queryNumber("Input the interval: ");
        trafficLights = new TrafficLights(numberOfRoads, interval);
    }

    private int queryNumber(String queryMessage) {
        printer.printInfo(queryMessage);
        return scanIntegerValidated("[1-9][0-9]{0,8}",
                () -> printer.printInfo("Incorrect Input. Try again:"));
    }

    private int scanIntegerValidated(String regex, Runnable invalidAction) {
        var input = scanner.nextLine();
        while (!input.matches(regex)) {
            invalidAction.run();
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    private enum Choice {
        QUIT, ADD_ROAD, DELETE_ROAD, OPEN_SYSTEM
    }
}
