package de.joern.day8;

import java.util.ArrayList;
import java.util.List;

abstract class GridBasedScoreGenerator implements Day8.ScoreGenerator {
    protected final Grid grid;

    GridBasedScoreGenerator(Grid grid) {
        this.grid = grid;
    }

    List<Integer> heightsToLeft(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int x = queryX - 1; x >= 0; x--) {
            result.add(grid.valueAt(x, queryY));
        }
        return result;
    }

    List<Integer> heightsToRight(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int x = queryX + 1; x < grid.maxX(); x++) {
            result.add(grid.valueAt(x, queryY));
        }
        return result;
    }

    List<Integer> heightsToTop(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int y = queryY - 1; y >= 0; y--) {
            result.add(grid.valueAt(queryX, y));
        }
        return result;
    }

    List<Integer> heightsToBottom(int queryX, int queryY) {
        List<Integer> result = new ArrayList<>();
        for (int y = queryY + 1; y < grid.maxY(); y++) {
            result.add(grid.valueAt(queryX, y));
        }
        return result;
    }
}
