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
        when(scannerMock.nextLine()).thenReturn("5", "3", "1", "2", "3", "0");
        controller.run();
        verify(printerMock, times(4)).printInfo("""
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit""");
        InOrder inOrder = Mockito.inOrder(printerMock);
        inOrder.verify(printerMock).printInfo("Road added");
        inOrder.verify(printerMock).printInfo("Road deleted");
        inOrder.verify(printerMock).printInfo("System opened");
        inOrder.verify(printerMock).printInfo("Bye!");
    }
}