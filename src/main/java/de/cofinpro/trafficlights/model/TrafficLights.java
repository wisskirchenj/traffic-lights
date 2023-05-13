package de.cofinpro.trafficlights.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Traffic Lights model class.
 */
@Data
@AllArgsConstructor
public class TrafficLights {
    private final Queue<String> roadQueue = new ArrayDeque<>();
    private int roadCapacity;
    private int interval;

    public String getRoadsLines() {
        return String.join("\n", roadQueue);
    }

    /**
     * add road to the circular queue, if the capacity allows.
     * @return info message on the result.
     */
    public String addRoad(String road) {
        if (roadQueue.size() == roadCapacity) {
            return "The queue is full";
        }
        roadQueue.offer(road);
        return "%s added".formatted(road);
    }

    /**
     * poll road from the circular queue, if it is not empty.
     * @return info message on the result.
     */
    public String deleteRoad() {
        var deletedRoad = roadQueue.poll();
        return deletedRoad != null
                ? "%s deleted".formatted(deletedRoad)
                : "The queue is empty";
    }
}
