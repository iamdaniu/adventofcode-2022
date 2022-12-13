package de.joern.day8;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;

public class Day8 implements ProblemSolver<Integer> {
    private final Grid grid = new Grid();

    public static ProblemSolver<Integer> day8_1() {
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
                if (x == 0 || y == 0 || x == grid.maxX()-1 || y == grid.maxY()-1) {
                    visibleCount++;
                } else if (isVisible(x, y)) {
                    System.out.printf("%d/%d is visible%n", y, x);
                    visibleCount++;
                }
            }
        }
        return visibleCount;
    }

    boolean isVisible(int queryX, int queryY) {
        int value = grid.valueAt(queryX, queryY);
        boolean visible = true;
        // to left
        for (int x = 0; x < queryX; x++) {
            if (grid.valueAt(x, queryY) >= value) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        // to right
        visible = true;
        for (int x = queryX+1; x < grid.maxX(); x++) {
            if (grid.valueAt(x, queryY) >= value) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        // to top
        visible = true;
        for (int y = 0; y < queryY; y++) {
            if (grid.valueAt(queryX, y) >= value) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        // to bottom
        visible = true;
        for (int y = queryY+1; y < grid.maxY(); y++) {
            if (grid.valueAt(queryX, y) >= value) {
                visible = false;
                break;
            }
        }
        return visible;
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
