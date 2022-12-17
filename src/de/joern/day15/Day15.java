package de.joern.day15;

import de.joern.Coordinate;
import de.joern.Grid;
import de.joern.ProblemSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day15 implements ProblemSolver<Long> {
    static final Pattern SENSOR_PATTERN = Pattern.compile("Sensor at x=([0-9]+), y=([0-9]+):.*");
    static final Pattern BEACON_PATTERN = Pattern.compile(".*closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)");
    private final Grid<Contents> grid = new Grid<>(Contents.EMPTY);
    private final Function<Day15, Long> getResult;
    private final BiConsumer<Day15, Coordinate> sensorHandler;

    private final Map<Coordinate, Long> sensorToDistance = new HashMap<>();

    public Day15(Function<Day15, Long> getResult, BiConsumer<Day15, Coordinate> sensorHandler) {
        this.getResult = getResult;
        this.sensorHandler = sensorHandler;
    }

    public static ProblemSolver<Long> day15_1(int row) {
        return new Day15(d -> d.countKnownNoBeacons(row), s -> {});
    }

    public static ProblemSolver<Long> day15_2(long min, long max) {
        return new Day15(d -> d.countUncoveredCoordinates(min, max), (d, s) -> d.markCovered(s, min, max));
    }

    private static void markCovered(Coordinate sensor, long min, long max) {
        //        System.out.printf("marking coverage for %s%n", sensor);
//        for (long atDistance = -distance; atDistance <= distance; atDistance++) {
//            for (long col = 0; Math.abs(atDistance) + col <= distance; col++) {
//                var covered = new Coordinate(sensor.x() + atDistance, sensor.y() + col);
//                if (grid.contentsAt(covered) == Contents.EMPTY) {
//                    grid.setContent(covered, Contents.COVERED);
//                }
//                covered = new Coordinate(sensor.x() + atDistance, sensor.y() - col);
//                if (grid.contentsAt(covered) == Contents.EMPTY) {
//                    grid.setContent(covered, Contents.COVERED);
//                }
//            }
//        }
    }

    @Override
    public void consider(String line) {
        Coordinate sensor = Coordinate.parse(SENSOR_PATTERN, line);
        grid.setContent(sensor, Contents.SENSOR);

        Coordinate beacon = Coordinate.parse(BEACON_PATTERN, line);
        grid.setContent(beacon, Contents.BEACON);

        var distance = distance(sensor, beacon);
        sensorToDistance.put(sensor, distance);

        // mark covered fields
    }

    private Long countKnownNoBeacons(long row) {
//        grid.draw();
        var maxDistance = sensorToDistance.values().stream()
                .mapToLong(Long::intValue)
                .max()
                .orElse(0);

        var startX = grid.getLeftmostX() - maxDistance;
        var endX = grid.getRightmostX() + maxDistance;

        long result = row(row, startX, endX)
                .filter(this::knownNoBeacon)
                .count();
        return result;
    }

    private Long countUncoveredCoordinates(long min, long max) {
        Optional<Coordinate> uncovered = Optional.empty();
        for (long y = min; y <= max; y++) {
            uncovered = row(y, min, max).filter(this::uncovered)
                    .findAny();
            if (uncovered.isPresent()) {
//                System.out.printf("found uncovered at %s%n", uncovered.get());
                break;
            }
        }
        return uncovered.map(Day15::frequency)
                .orElse(0L);
    }

    private static long frequency(Coordinate coordinate) {
        return coordinate.x() * 4_000_000 + coordinate.y();
    }

    private static long distance(Coordinate coord1, Coordinate coord2) {
        return Math.abs(coord1.x() - coord2.x()) + Math.abs(coord1.y() - coord2.y());
    }

    @Override
    public Long finished() {
        return getResult.apply(this);
    }

    private boolean knownNoBeacon(Coordinate toCheck) {
        if (grid.contentsAt(toCheck.x(), toCheck.y()) != Contents.EMPTY) {
            return false;
        }
        boolean result = false;
        for (Map.Entry<Coordinate, Long> sensorEntry : sensorToDistance.entrySet()) {
            var distance = distance(sensorEntry.getKey(), toCheck);
            if (distance <= sensorEntry.getValue()) {
                result = true;
                break;
            }
        }
        return result;
    }
    private boolean uncovered(Coordinate toCheck) {
        if (grid.contentsAt(toCheck) != Contents.EMPTY) {
//            System.out.printf("%s contains %s%n", toCheck, grid.contentsAt(toCheck));
            return false;
        }
        boolean result = true;
        for (Map.Entry<Coordinate, Long> sensorEntry : sensorToDistance.entrySet()) {
            var distance = distance(sensorEntry.getKey(), toCheck);
            if (distance <= sensorEntry.getValue()) {
//                System.out.printf("%s covered by %s%n", toCheck, sensorEntry.getKey());
                result = false;
                break;
            }
        }
//        if (result) {
//            System.out.printf("uncovered position: %s%n", toCheck);
//        }
        return result;
    }

    public static Stream<Coordinate> row(long row, long startX, long endX) {
//        System.out.printf("creating row %d from %d to %d%n", row, startX, endX);
        return Stream.iterate(startX, x -> x <= endX, x -> x+1)
                .map(x -> new Coordinate(x, row));
    }
}
