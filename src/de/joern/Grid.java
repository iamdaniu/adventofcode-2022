package de.joern;

import de.joern.Coordinate;
import lombok.Getter;

public class Grid<T> {
    @SuppressWarnings("unchecked")
    private final T[][] grid = (T[][]) new Object[1_000][1_000];
    private int highestY = Integer.MAX_VALUE;
    private int leftmostX = Integer.MAX_VALUE, rightmostX;
    @Getter
    private int lowestY;

    public Grid(T defaultValue) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = defaultValue;
            }
        }
    }

    public void setContent(Coordinate coord, T content) {
        var x = coord.x();
        var y = coord.y();
        grid[x][y] = content;
        lowestY = Math.max(lowestY, y);
        highestY = Math.min(highestY, y);
        leftmostX = Math.min(x, leftmostX);
        rightmostX = Math.max(x, rightmostX);
    }

    public void draw() {
        System.out.printf("top left: (%d/%d), bottom right: (%d/%d)%n", leftmostX, highestY, rightmostX, lowestY);
        for (int y = highestY; y < lowestY + 1; y++) {
            for (int x = leftmostX; x < rightmostX + 1; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
    }

    public T contentsAt(int x, int y) {
        return grid[x][y];
    }
}
