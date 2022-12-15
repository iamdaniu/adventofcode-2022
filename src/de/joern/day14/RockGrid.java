package de.joern.day14;

import lombok.Getter;

import static de.joern.day14.Contents.*;

class RockGrid {
    private final Contents[][] grid = new Contents[1_000][1_000];
    private int highestY = Integer.MAX_VALUE;
    private int leftmostX = Integer.MAX_VALUE, rightmostX;
    @Getter
    private int lowestY;

    @Getter
    private int sandCount;

    RockGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    public void addSolid(int x, int y) {
        grid[x][y] = SOLID;
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

    public Contents contentsAt(int x, int y) {
        return grid[x][y];
    }

    public void addSand(int x, int y) {
        grid[x][y] = SAND;
        sandCount++;
    }
}
