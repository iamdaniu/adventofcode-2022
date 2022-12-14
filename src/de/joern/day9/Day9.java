package de.joern.day9;

import de.joern.Coordinate;
import de.joern.ProblemSolver;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Day9 implements ProblemSolver<Integer> {
    private Coordinate currentHead = new Coordinate(0, 0);
    private Coordinate currentTail = new Coordinate(0, 0);
    private final Set<Coordinate> visited = new HashSet<>();

    static final Pattern LINE_PATTERN = Pattern.compile("([UDLR]) ([0-9]+)");

    public static ProblemSolver<Integer> day9_1() {
        return new Day9();
    }

    @Override
    public void consider(String line) {
        var matcher = LINE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Could not parse line %s".formatted(line));
        }
        Movement move = Movement.from(matcher.group(1));
        int moveCount = Integer.parseInt(matcher.group(2));
        for (int i = 0; i < moveCount; i++) {
            currentHead = move.apply(currentHead);
            currentTail = moveTowards(currentHead, currentTail);
            visited.add(currentTail);
        }
    }

    @Override
    public Integer finished() {
        return visited.size();
    }

    static Coordinate moveTowards(Coordinate head, Coordinate tail) {
        int xDistance = Math.abs(head.x() - tail.x());
        int yDistance = Math.abs(head.y() - tail.y());
        // stay where it is
        if (xDistance < 2 && yDistance < 2) {
            return tail;
        }
        int newX = adjust(tail.x(), head.x());
        int newY = adjust(tail.y(), head.y());
        return new Coordinate(newX, newY);
    }

    private static int adjust(int from, int to) {
        if (from == to) {
            return from;
        }
        return (to < from)
                ? from - 1
                : from + 1;
    }
}
