package de.cofinpro.trafficlights.controller;

import de.cofinpro.trafficlights.io.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.mockito.Mockito.verify;

@MockitoSettings
class MainMenuControllerTest {

    @Mock
    ConsolePrinter printerMock;

    MainMenuController controller;

    @BeforeEach
    void setUp() {
        controller = new MainMenuController(printerMock);
    }

    @Test
    void whenRun_correctMenuPrintout() {
        controller.run();
        verify(printerMock).printInfo("""
            Welcome to the traffic management system!
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit""");
    }
}