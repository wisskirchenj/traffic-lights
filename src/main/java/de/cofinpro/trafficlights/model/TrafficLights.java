package de.cofinpro.trafficlights.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Traffic Lights model class.
 */
@Data
public class TrafficLights {

    private final List<Road> roads = new ArrayList<>();
    private final int roadCapacity;
    private final int interval;
    private int remainingInInterval;

    public TrafficLights(int roadCapacity, int interval) {
        this.roadCapacity = roadCapacity;
        this.interval = interval;
    }

    public String getRoadsLines() {
        return roads.stream().map(Road::getState).collect(Collectors.joining("\n"));
    }

    public String addRoad(String road) {
        if (roads.size() == roadCapacity) {
            return "The queue is full";
        }
        if (roads.isEmpty()) { // start first road with full interval
            remainingInInterval = interval + 1;
        }
        roads.add(new Road(road, remainingInInterval + roads.size() * interval));
        return "%s added".formatted(road);
    }

    public String deleteRoad() {
        if (roads.isEmpty()) {
            return "The queue is empty";
        }
        var message = "%s deleted".formatted(roads.getFirst().getName());
        adjustCountersIfClosedRoadDeleted();
        roads.removeFirst();
        return message;
    }

    /**
     * implements the weird requirement to adjust the countdowns if closed road is deleted
     * and there is an open road left...
     */
    private void adjustCountersIfClosedRoadDeleted() {
        if (roads.stream().noneMatch(road -> road.secondsCounter<= interval)) {
            return;
        }
        var secondsOnRoadToDelete = roads.getFirst().getSecondsCounter();
        if (secondsOnRoadToDelete > interval) { // road is closed
            roads.stream()
                    .filter(road -> road.secondsCounter > secondsOnRoadToDelete)
                    .forEach(Road::subtractInterval);
        }
    }

    public void notifySecondPassed() {
        roads.forEach(Road::countDown);
        remainingInInterval = remainingInInterval > 1 ? remainingInInterval - 1 : interval;
    }

    @Data
    @AllArgsConstructor
    private class Road {

        private static final String ANSI_RED = "\u001B[31m";
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_GREEN = "\u001B[32m";

        private final String name;
        private int secondsCounter;

        private void countDown() {
            secondsCounter = secondsCounter == 1 ? roads.size() * interval : secondsCounter - 1;
        }

        private void subtractInterval() {
            secondsCounter -= interval;
        }

        String getState() {
            return secondsCounter <= interval // condition true means road open
                    ? "%s is %sopen for %ds.%s".formatted(name, ANSI_GREEN, secondsCounter, ANSI_RESET)
                    : "%s is %sclosed for %ds.%s".formatted(name, ANSI_RED, secondsCounter - interval, ANSI_RESET);
        }
    }
}
