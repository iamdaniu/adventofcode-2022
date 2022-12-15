package de.joern.day14;

import de.joern.Coordinate;
import de.joern.Grid;
import de.joern.ProblemSolver;

import java.util.regex.Pattern;

import static de.joern.day14.Contents.*;

public class Day14 implements ProblemSolver<Integer> {
    private static final Pattern COORD_PATTERN = Pattern.compile("([0-9]+),([0-9]+)");
    private final Grid<Contents> grid = new Grid<>(EMPTY);
    private int sandCount;

    public static ProblemSolver<Integer> day14_1() {
        return new Day14();
    }

    @Override
    public void consider(String line) {
        var coords = line.split(" -> ");
        Coordinate start = parse(coords[0]);
        for (int i = 1; i < coords.length; i++) {
            Coordinate end = parse(coords[i]);
            makeSolid(start, end);
            start = end;
        }
    }

    @Override
    public Integer finished() {
        grid.draw();
        Coordinate sandPosition = new Coordinate(500, 0);
        while (sandPosition.y() < grid.getLowestY()) {
            // fall
            final var yBelow = sandPosition.y() +1;
            if (grid.contentsAt(sandPosition.x(), yBelow) == EMPTY) {
                sandPosition = new Coordinate(sandPosition.x(), yBelow);
            } else if (grid.contentsAt(sandPosition.x()-1, yBelow) == EMPTY) {
                sandPosition = new Coordinate(sandPosition.x()-1, yBelow);
            } else if (grid.contentsAt(sandPosition.x()+1, yBelow) == EMPTY) {
                sandPosition = new Coordinate(sandPosition.x()+1, yBelow);
            } else {
                // come to rest
                grid.setContent(sandPosition, SAND);
                sandPosition = new Coordinate(500, 0);
                sandCount++;
            }
        }
        grid.draw();
        return sandCount;
    }

    static Coordinate parse(String coord) {
        var matcher = COORD_PATTERN.matcher(coord);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("could not parse " + coord);
        }
        return new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    void makeSolid(Coordinate start, Coordinate end) {
        if (start.x() == end.x()) {
            for (int y = Math.min(start.y(), end.y()); y < Math.max(start.y(), end.y())+1; y++) {
                grid.setContent(new Coordinate(start.x(), y), SOLID);
            }
        } else if (start.y() == end.y()) {
            for (int x = Math.min(start.x(), end.x()); x < Math.max(start.x(), end.x())+1; x++) {
                grid.setContent(new Coordinate(x, start.y()), SOLID);
            }
        } else {
            throw new IllegalStateException("either x or y not equal: %s %s".formatted(start, end));
        }
    }

}
