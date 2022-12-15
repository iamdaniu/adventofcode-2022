package de.joern.day15;

import de.joern.Coordinate;
import de.joern.Grid;
import de.joern.ProblemSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
        return row(checkRow)
                .filter(this::knownNoBeacon)
                .count();
    }

    private boolean knownNoBeacon(Coordinate toCheck) {
        if (grid.contentsAt(toCheck.x(), toCheck.y()) != Contents.EMPTY) {
            return false;
        }
        boolean result = false;
        for (Map.Entry<Coordinate, Integer> sensorEntry : sensorToDistance.entrySet()) {
            final var distance = distance(sensorEntry.getKey(), toCheck);
            if (distance <= sensorEntry.getValue()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Stream<Coordinate> row(int row) {
        int maxDistance = sensorToDistance.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        int startX = grid.getLeftmostX() - maxDistance;
        int endX = grid.getRightmostX() + maxDistance;
        return Stream.iterate(startX, x -> x <= endX, x -> x+1)
                .map(x -> new Coordinate(x, row));
    }
}
