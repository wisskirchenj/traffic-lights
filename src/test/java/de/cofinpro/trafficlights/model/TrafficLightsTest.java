package de.cofinpro.trafficlights.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightsTest {

    TrafficLights trafficLights;

    @BeforeEach
    void setUp() {
        trafficLights = new TrafficLights(4, 3);
    }

    @Test
    void when1RoadAdded_RoadAlwaysOpenCountdowns() {
        trafficLights.addRoad("Avenue");
        for (int i = 3; i > 0; i--) {
            advanceSeconds(1);
            assertEquals(getOpenLine("Avenue", i), trafficLights.getRoadsLines());
        }
        advanceSeconds(1);
        assertEquals(getOpenLine("Avenue", 3), trafficLights.getRoadsLines());
    }

    @Test
    void when2RoadsAdded_OpenSwitches() {
        trafficLights.addRoad("A");
        trafficLights.addRoad("B");
        for (int i = 3; i > 0; i--) {
            advanceSeconds(1);
            assertEquals(getOpenLine("A", i) + "\n" + getClosedLine("B", i),
                    trafficLights.getRoadsLines());
        }
        for (int i = 3; i > 0; i--) {
            advanceSeconds(1);
            assertEquals(getClosedLine("A", i) + "\n" + getOpenLine("B", i),
                    trafficLights.getRoadsLines());
        }
    }

    @Test
    void whenClosedRoadDeleted_RoadTimersAdjusted() {
        trafficLights.addRoad("A");
        trafficLights.addRoad("B");
        trafficLights.addRoad("C");
        advanceSeconds(8);
        var expected = String.join("\n", List.of(
                getClosedLine("A", 2),
                getClosedLine("B", 5),
                getOpenLine("C", 2)
        ));
        assertEquals(expected, trafficLights.getRoadsLines());
        trafficLights.deleteRoad();
        advanceSeconds(1);
        expected = getClosedLine("B", 1) + "\n" + getOpenLine("C", 1);
        assertEquals(expected, trafficLights.getRoadsLines());
    }

    @Test
    void whenOpenRoadDeletedOrClosedWithoutAnyOpenRoads_RoadTimersNotAdjusted() {
        trafficLights.addRoad("A");
        trafficLights.addRoad("B");
        trafficLights.addRoad("C");
        advanceSeconds(1);
        var expected = String.join("\n", List.of(
                getOpenLine("A", 3),
                getClosedLine("B", 3),
                getClosedLine("C", 6)
        ));
        assertEquals(expected, trafficLights.getRoadsLines());
        trafficLights.deleteRoad();
        advanceSeconds(1);
        expected = getClosedLine("B", 2) + "\n" + getClosedLine("C", 5);
        assertEquals(expected, trafficLights.getRoadsLines());
        trafficLights.deleteRoad();
        advanceSeconds(1);
        assertEquals(getClosedLine("C", 4), trafficLights.getRoadsLines());
    }

    private String getOpenLine(String road, int seconds) {
        return "%s is \u001B[32mopen for %ds.\u001B[0m".formatted(road, seconds);
    }

    private String getClosedLine(String road, int seconds) {
        return "%s is \u001B[31mclosed for %ds.\u001B[0m".formatted(road, seconds);
    }

    private void advanceSeconds(int seconds) {
        IntStream.rangeClosed(1, seconds).forEach(i -> trafficLights.notifySecondPassed());
    }
}