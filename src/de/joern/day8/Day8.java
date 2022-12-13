package de.joern.day8;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Day8 implements ProblemSolver<Integer> {
    private final Grid grid = new Grid();
    private final ScoreGenerator scoreGenerator;

    public Day8(Function<Day8, ScoreGenerator> getScoreGenerator) {
        scoreGenerator = getScoreGenerator.apply(this);
    }

    public static ProblemSolver<Integer> day8_1() {
        return new Day8(d -> d.new VisibilityCount());
    }

    public static ProblemSolver<Integer> day8_2() {
        return new Day8(d -> d.new MaxScenicView());
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
        for (int y = 0; y < grid.maxY(); y++) {
            for (int x = 0; x < grid.maxX(); x++) {
                scoreGenerator.accept(x, y);
            }
        }
        return scoreGenerator.get();
    }

    interface ScoreGenerator extends BiConsumer<Integer, Integer>, Supplier<Integer> { }

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

    private class VisibilityCount implements ScoreGenerator {
        private int visible = 0;

        @Override
        public void accept(Integer x, Integer y) {
            if (isVisible(x, y)) {
                visible++;
            }
        }

        @Override
        public Integer get() {
            return visible;
        }
    }

    private class MaxScenicView implements ScoreGenerator {
        private int maxScenic;

        @Override
        public void accept(Integer x, Integer y) {
            final var scenicValue = scenicValue(x, y);
            if (scenicValue > maxScenic) {
                maxScenic = scenicValue;
            }
        }

        @Override
        public Integer get() {
            return maxScenic;
        }
    }

    boolean isVisible(int queryX, int queryY) {
        int value = grid.valueAt(queryX, queryY);
        List<BiFunction<Integer, Integer, List<Integer>>> sightsGenerators =
                List.of(this::heightsToLeft, this::heightsToRight, this::heightsToTop, this::heightsToBottom);
        for (BiFunction<Integer, Integer, List<Integer>> sightsGenerator : sightsGenerators) {
            var blocking = sightsGenerator.apply(queryX, queryY).stream()
                    .filter(height -> height >= value)
                    .findFirst();
            if (blocking.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    int scenicValue(int queryX, int queryY) {
        int result = 1;
        int value = grid.valueAt(queryX, queryY);
        List<BiFunction<Integer, Integer, List<Integer>>> sightsGenerators =
                List.of(this::heightsToLeft, this::heightsToRight, this::heightsToTop, this::heightsToBottom);
        for (BiFunction<Integer, Integer, List<Integer>> sightsGenerator : sightsGenerators) {
            result *= viewingDistance(sightsGenerator.apply(queryX, queryY), value);
        }
        return result;
    }
    static long viewingDistance(List<Integer> heights, int myHeight) {
        int distance = 0;
        for (Integer height : heights) {
            distance++;
            if (height >= myHeight) {
                break;
            }
        }
        return distance;
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
