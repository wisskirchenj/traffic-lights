package de.cofinpro.trafficlights.controller;

import de.cofinpro.trafficlights.io.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class MainMenuControllerTest {

    @Mock
    ConsolePrinter printerMock;

    @Mock
    Scanner scannerMock;

    MainMenuController controller;

    @BeforeEach
    void setUp() {
        controller = new MainMenuController(printerMock, scannerMock);
    }

    @Test
    void whenRunAllStages_correctPrintouts() {
        when(scannerMock.nextLine()).thenReturn("-1", "Hello", "0", "5", "-5", "World","0",
                "3", "4", "-1", "Hello world", "2", "0", "0");
        controller.run();
        verify(printerMock, times(5)).clearAndPrintMenu("""
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""");
        verify(printerMock, times(6)).printInfo("Incorrect Input. Try again:");
        verify(printerMock, times(3)).printInfoAndWaitForReturn(scannerMock,"Incorrect option");
        InOrder inOrder = Mockito.inOrder(printerMock);
        inOrder.verify(printerMock).printInfoAndWaitForReturn(scannerMock,"Road deleted");
        inOrder.verify(printerMock).printInfo("Bye!");
    }
}