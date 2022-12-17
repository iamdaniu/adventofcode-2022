package de.joern.day9;

import de.joern.Coordinate;
import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Day9 implements ProblemSolver<Integer> {
    private Coordinate currentHead = new Coordinate(0, 0);
    private final List<Coordinate> currentTails = new ArrayList<>();
    private final Set<Coordinate> visitedTail1 = new HashSet<>();
    private final Set<Coordinate> visitedTail9 = new HashSet<>();
    private final Function<Day9, Integer> resultExtractor;

    static final Pattern LINE_PATTERN = Pattern.compile("([UDLR]) ([0-9]+)");

    public Day9(Function<Day9, Integer> resultExtractor) {
        this.resultExtractor = resultExtractor;
        for (int i = 0; i < 9; i++) {
            currentTails.add(currentHead);
        }
    }

    public static ProblemSolver<Integer> day9_1() {
        return new Day9(d -> d.visitedTail1.size());
    }

    public static ProblemSolver<Integer> day9_2() {
        return new Day9(d -> d.visitedTail9.size());
    }

    @Override
    public void consider(String line) {
        var matcher = LINE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Could not parse line %s".formatted(line));
        }
        Movement move = Movement.from(matcher.group(1));
        int moveCount = Integer.parseInt(matcher.group(2));
        for (int j = 0; j < moveCount; j++) {
            currentHead = move.apply(currentHead);
            Coordinate currentTail = moveTowards(currentHead, currentTails.get(0));
            visitedTail1.add(currentTail);

            currentTails.set(0, currentTail);
            for (int i = 1; i < currentTails.size(); i++) {
                Coordinate current = moveTowards(currentTails.get(i-1), currentTails.get(i));
                currentTails.set(i, current);
            }
            if (currentTails.size() == 9) {
                visitedTail9.add(currentTails.get(8));
            }
        }
    }

    @Override
    public Integer finished() {
        return resultExtractor.apply(this);
    }

    static Coordinate moveTowards(Coordinate head, Coordinate tail) {
        var xDistance = Math.abs(head.x() - tail.x());
        var yDistance = Math.abs(head.y() - tail.y());
        // stay where it is
        if (xDistance < 2 && yDistance < 2) {
            return tail;
        }
        var newX = adjust(tail.x(), head.x());
        var newY = adjust(tail.y(), head.y());
        return new Coordinate(newX, newY);
    }

    private static long adjust(long from, long to) {
        if (from == to) {
            return from;
        }
        return (to < from)
                ? from - 1
                : from + 1;
    }
}
