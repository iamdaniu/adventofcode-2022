package de.joern.day15;

import de.joern.Coordinate;
import de.joern.Grid;
import de.joern.ProblemSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day15 implements ProblemSolver<Long> {
    static final Pattern SENSOR_PATTERN = Pattern.compile("Sensor at x=([0-9]+), y=([0-9]+):.*");
    static final Pattern BEACON_PATTERN = Pattern.compile(".*closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)");
    private final Grid<Contents> grid = new Grid<>(Contents.EMPTY);

    private final Map<Coordinate, Integer> sensorToDistance = new HashMap<>();
    private final int checkRow;

    public Day15(int row) {
        checkRow = row;
    }

    public static ProblemSolver<Long> day15_1(int row) {
        return new Day15(row);
    }

    @Override
    public void consider(String line) {
        Coordinate sensor = Coordinate.parse(SENSOR_PATTERN, line);
        grid.setContent(sensor, Contents.SENSOR);

        Coordinate beacon = Coordinate.parse(BEACON_PATTERN, line);
        grid.setContent(beacon, Contents.BEACON);

        var distance = distance(sensor, beacon);
        sensorToDistance.put(sensor, distance);
    }

    private static int distance(Coordinate coord1, Coordinate coord2) {
        return Math.abs(coord1.x() - coord2.x()) + Math.abs(coord1.y() - coord2.y());
    }

    @Override
    public Long finished() {
        grid.draw();
        return grid.row(checkRow)
                .filter(this::knownNoBeacon)
                .count();
    }

    private boolean knownNoBeacon(Coordinate toCheck) {
//        System.out.println("checking " + toCheck);
        if (grid.contentsAt(toCheck.x(), toCheck.y()) != Contents.EMPTY) {
//            System.out.print(grid.contentsAt(toCheck.x(), toCheck.y()));
            return false;
        }
        boolean result = false;
        for (Map.Entry<Coordinate, Integer> sensorEntry : sensorToDistance.entrySet()) {
            final var distance = distance(sensorEntry.getKey(), toCheck);
            if (distance <= sensorEntry.getValue()) {
//                System.out.printf("found sensor covering: %s (distance %d, has beacon at %s)%n", sensorEntry.getKey(), distance, sensorEntry.getValue());
                result = true;
                break;
            }
        }
//        System.out.println("not covered by sensor");
//        System.out.print(result ? "#" : ".");
        return result;
//        return sensorToDistance.entrySet().stream()
//                .anyMatch(sensorEntry -> distance(toCheck, sensorEntry.getKey()) < sensorEntry.getValue());
    }
}
