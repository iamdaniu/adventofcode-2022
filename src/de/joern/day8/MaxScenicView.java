package de.joern.day8;

import java.util.List;
import java.util.function.BiFunction;

class MaxScenicView extends GridBasedScoreGenerator {
    private int maxScenic;

    MaxScenicView(Grid grid) {
        super(grid);
    }

    @Override
    public void accept(Integer x, Integer y) {
        var scenicValue = scenicValue(x, y);
        if (scenicValue > maxScenic) {
            maxScenic = scenicValue;
        }
    }

    @Override
    public Integer get() {
        return maxScenic;
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
}
