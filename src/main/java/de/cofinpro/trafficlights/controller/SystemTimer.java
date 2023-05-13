package de.cofinpro.trafficlights.controller;

import de.cofinpro.trafficlights.io.ConsolePrinter;
import de.cofinpro.trafficlights.model.TrafficLights;
import lombok.Getter;
import lombok.Setter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * SystemTimer, that starts on instantiation a named Timer daemon thread (not exceeding application run time).
 * Its TimerTask is run every second and displays system info  - iff field inSystemState is set to true.
 */
class SystemTimer {

    private final ConsolePrinter printer;
    private final TrafficLights trafficLights;
    private final Timer timer;
    @Setter
    private boolean inSystemState = false;
    @Getter  // getter only needed for Awaitility-testing
    private int secondsPassed;

    public SystemTimer(ConsolePrinter printer, TrafficLights trafficLights) {
        this.printer = printer;
        this.trafficLights = trafficLights;
        this.timer = new Timer("QueueThread", true);
        this.timer.scheduleAtFixedRate(new DisplayTask(), 0, 1000);
    }

    String getSystemInfo() {
        return """
                ! %ds. have passed since system startup !
                ! Number of roads: %d !
                ! Interval: %d !
                
                %s
                
                ! Press "Enter" to open menu !"""
                .formatted(secondsPassed, trafficLights.getRoadCapacity(), trafficLights.getInterval(),
                        trafficLights.getRoadsLines());
    }

    void purge() {
        timer.cancel();
        timer.purge();
    }

    private class DisplayTask extends TimerTask {

        @Override
        public void run() {
            secondsPassed++;
            if (!inSystemState) {
                return;
            }
            printer.clear();
            printer.printInfo(getSystemInfo());
        }
    }
}
