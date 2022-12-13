package de.joern.day8;

import java.util.ArrayList;
import java.util.List;

class Grid {
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
