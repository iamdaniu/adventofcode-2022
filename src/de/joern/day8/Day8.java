package de.joern.day8;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class Day8 implements ProblemSolver<Integer> {
    private final Grid grid = new Grid();

    public static ProblemSolver<Integer> day8_1() {
        return new Day8();
    }

    public static ProblemSolver<Integer> day8_2() {
        return new Day8();
    }

    @Override
    public void consider(String line) {
        List<Integer> row = new ArrayList<>(line.length());
        for (int i = 0; i < line.length(); i++) {
            row.add(line.charAt(i) - '0');
        }
        grid.addRow(row);
    }

    @Override
    public Integer finished() {
        int visibleCount = 0;
        for (int y = 0; y < grid.maxY(); y++) {
            for (int x = 0; x < grid.maxX(); x++) {
                if (isVisible(x, y)) {
                    visibleCount++;
                }
            }
        }
        return visibleCount;
    }

    List<Integer> heightsToLeft(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int x = queryX-1; x >= 0; x--) {
            result.add(grid.valueAt(x, queryY));
        }
        return result;
    }
    List<Integer> heightsToRight(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int x = queryX+1; x < grid.maxX(); x++) {
            result.add(grid.valueAt(x, queryY));
        }
        return result;
    }
    List<Integer> heightsToTop(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int y = queryY-1; y >= 0; y--) {
            result.add(grid.valueAt(queryX, y));
        }
        return result;
    }
    List<Integer> heightsToBottom(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int y = queryY+1; y < grid.maxY(); y++) {
            result.add(grid.valueAt(queryX, y));
        }
        return result;
    }

    boolean isVisible(int queryX, int queryY) {
        int value = grid.valueAt(queryX, queryY);
        boolean visible = true;
        List<BiFunction<Integer, Integer, List<Integer>>> sightsGenerators =
                List.of(this::heightsToLeft, this::heightsToRight, this::heightsToTop, this::heightsToBottom);
        for (BiFunction<Integer, Integer, List<Integer>> sightsGenerator : sightsGenerators) {
            final var blocking = sightsGenerator.apply(queryX, queryY).stream()
                    .filter(height -> height >= value)
                    .findFirst();
            if (blocking.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    static class Grid {
        private final List<List<Integer>> grid = new ArrayList<>();
        public int maxY() {
            return grid.size();
        }
        public int maxX() {
            return grid.get(0).size();
        }

        public void addRow(List<Integer> values) {
            grid.add(values);
        }

        public int valueAt(int x, int y) {
            return grid.get(y).get(x);
        }
    }
}
