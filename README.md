# IDEA EDU Course

Implemented in the <b>Java Desktop Application Developer</b> Track of hyperskill.org JetBrain Academy.  

Project goal is to beta test this new Jetbrains project and give feedback to the authors.

## Technology / External Libraries

- Java 20
- Lombok
- Slf4j
- Tests with Junit-Jupiter and Mockito
- Awaitility
- Gradle 8.1.1

## Program description

The application will provide a simplified version of a traffic lights system for a road junction in which many roads
converge to one.

## Project completion

Project was completed on 14.05.23.

## Repository Contents

Sources for all project tasks (6 stages) with tests and configurations.

## Progress

13.05.23 Project started. Setup of build and repo with gradle on Kotlin basis.

13.05.23 Stage 1 completed. Just print outs.

13.05.23 Stage 2 completed. A simple integer value menu and introduced a model class for traffic lights.

13.05.23 Stage 3 completed. Input validation with regex and invalidAction-Runnable on Integer-queries.
Console clearing via OS-ProcessBuilder.

13.05.23 Stage 4 completed. Added SystemTimer on basis of java.util.Timer. Used Awaitility-library for timing
unit tests

13.05.23 Stage 5 completed. Circular queue for the roads implemented with ArrayDeque and added to TrafficLights.
SystemTimer outputs roads in its state display.

14.05.23 Final Stage 6 completed. Road-dependent interval count-downs. Complicated timer logic implemented
in TrafficLights using inner Road class. Extensive unit tests.