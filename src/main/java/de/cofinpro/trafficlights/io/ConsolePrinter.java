package de.cofinpro.trafficlights.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * test-friendly (since capturable, mockable) wrapper class to the Logger for console printing.
 */
@Slf4j
public class ConsolePrinter {

    public final void printInfo(String message, Object... arguments) {
        log.info(message, arguments);
    }

    public final void printInfoAndWaitForReturn(Scanner scanner, String message, Object... arguments) {
        log.info(message, arguments);
        scanner.nextLine();
    }

    /**
     * Take care! The clear command is NOOP inside (Intellij) IDE. If you want to see it work, use gradle
     * task `installDist`, cd into the build->install-><project>->bin folder from a shell terminal window outside IDE
     * and run by the script there.
     */
    @SneakyThrows
    public void clear() {
        var clearCommand = System.getProperty("os.name").contains("Windows")
                ? new ProcessBuilder("cmd", "/c", "cls")
                : new ProcessBuilder("clear");
        clearCommand.inheritIO().start().waitFor();
    }

    public void clearAndPrintMenu(String menuText) {
        clear();
        printInfo(menuText);
    }
}
