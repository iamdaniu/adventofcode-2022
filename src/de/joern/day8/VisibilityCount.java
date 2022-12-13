package de.joern.day8;

import java.util.List;
import java.util.function.BiFunction;

class VisibilityCount extends GridBasedScoreGenerator {
    private int visible = 0;

    VisibilityCount(Grid grid) {
        super(grid);
    }

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
}
