package de.joern.day8;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Day8 implements ProblemSolver<Integer> {
    private final Grid grid = new Grid();
    private final ScoreGenerator scoreGenerator;

    public Day8(Function<Day8, ScoreGenerator> getScoreGenerator) {
        scoreGenerator = getScoreGenerator.apply(this);
    }

    public static ProblemSolver<Integer> day8_1() {
        return new Day8(d -> new VisibilityCount(d.grid));
    }

    public static ProblemSolver<Integer> day8_2() {
        return new Day8(d -> new MaxScenicView(d.grid));
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
}
