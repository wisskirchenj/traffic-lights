package de.cofinpro.trafficlights.controller;

import de.cofinpro.trafficlights.io.ConsolePrinter;

/**
 * Controller Class for the traffic lights manager app providing the main menu functionality.
 */
public class MainMenuController implements Runnable {

    private static final String MENU_TEXT = """
            Welcome to the traffic management system!
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit""";

    private final ConsolePrinter printer;

    public MainMenuController(ConsolePrinter printer) {
        this.printer = printer;
    }

    /**
     * entry point method doing the menu loop and triggering the actions chosen.
     */
    @Override
    public void run() {
        printer.printInfo(MENU_TEXT);
    }
}
