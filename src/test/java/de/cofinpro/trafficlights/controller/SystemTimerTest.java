package de.cofinpro.trafficlights.controller;

import de.cofinpro.trafficlights.io.ConsolePrinter;
import de.cofinpro.trafficlights.model.TrafficLights;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@MockitoSettings
class SystemTimerTest {

    private static final String systemStatePattern = """
                ! %ds. have passed since system startup !
                ! Number of roads: %d !
                ! Interval: %d !
                
                %s
                
                ! Press "Enter" to open menu !""";

    @Mock
    ConsolePrinter printerMock;

    SystemTimer timer;

    TrafficLights trafficLights;

    @BeforeEach
    void setUp() {
        trafficLights = new TrafficLights(3, 2);
        timer = new SystemTimer(printerMock, trafficLights);
    }

    @AfterEach
    void tearDown() {
        timer.purge();
    }

    @Test
    void whenInstantiated_timerThreadRunning() {
        assertTrue(Thread.getAllStackTraces().keySet().stream().map(Thread::getName).anyMatch("QueueThread"::equals));
    }

    @Test
    void whenInSystemState_TimerDisplays() {
        timer.setInSystemState(true);
        Awaitility.await().until(() -> timer.getSecondsPassed() == 2);
        verify(printerMock).printInfo(systemStatePattern.formatted(2, 3, 2, ""));
        verify(printerMock, never()).printInfo(systemStatePattern.formatted(3, 3, 2, ""));
    }

    @Test
    void whenInSystemStateReset_TimerAdjustsDisplay() {
        Awaitility.await().until(() -> timer.getSecondsPassed() == 2);
        timer.setInSystemState(true);
        Awaitility.await().until(() -> timer.getSecondsPassed() == 3);
        verify(printerMock, never()).printInfo(systemStatePattern.formatted(2, 3, 2, ""));
        verify(printerMock).printInfo(systemStatePattern.formatted(3, 3, 2, ""));
    }

    @Test
    void whenTrafficLightAdjusted_systemInfoUpdates() {
        timer.setInSystemState(true);
        Awaitility.await().until(() -> timer.getSecondsPassed() == 2);
        trafficLights.addRoad("Test");
        trafficLights.addRoad("Second street");
        Awaitility.await().until(() -> timer.getSecondsPassed() == 3);
        Awaitility.await().until(() -> timer.getSecondsPassed() == 4);
        Awaitility.await().until(() -> timer.getSecondsPassed() == 5);
        var inOrder = Mockito.inOrder(printerMock);
        inOrder.verify(printerMock).printInfo(systemStatePattern.formatted(2, 3, 2, ""));
        inOrder.verify(printerMock).printInfo(systemStatePattern.formatted(3, 3, 2,
                "Test is \u001B[32mopen for 2s.\u001B[0m\nSecond street is \u001B[31mclosed for 2s.\u001B[0m"));
        inOrder.verify(printerMock).printInfo(systemStatePattern.formatted(4, 3, 2,
                "Test is \u001B[32mopen for 1s.\u001B[0m\nSecond street is \u001B[31mclosed for 1s.\u001B[0m"));
        inOrder.verify(printerMock).printInfo(systemStatePattern.formatted(5, 3, 2,
                "Test is \u001B[31mclosed for 2s.\u001B[0m\nSecond street is \u001B[32mopen for 2s.\u001B[0m"));
    }
}