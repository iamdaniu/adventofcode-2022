package de.joern;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Grid<T> {
    private final Map<Coordinate, T> contentMap = new HashMap<>();
    private final T defaultValue;

    private long highestY = Long.MAX_VALUE;
    @Getter
    private long leftmostX = Long.MAX_VALUE, rightmostX;
    @Getter
    private long lowestY;

    public Grid(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setContent(Coordinate coord, T content) {
        contentMap.put(coord, content);
        var x = coord.x();
        var y = coord.y();
        lowestY = Math.max(lowestY, y);
        highestY = Math.min(highestY, y);
        leftmostX = Math.min(x, leftmostX);
        rightmostX = Math.max(x, rightmostX);
    }

    public void draw() {
        System.out.printf("top left: (%d/%d), bottom right: (%d/%d)%n", leftmostX, highestY, rightmostX, lowestY);
        for (long y = highestY; y < lowestY + 1; y++) {
            System.out.printf("%4d:", y);
            for (long x = leftmostX; x < rightmostX + 1; x++) {
                System.out.print(contentsAt(x, y));
            }
            System.out.println();
        }
    }

    public T contentsAt(long x, long y) {
        return contentsAt(new Coordinate(x, y));
    }

    public T contentsAt(Coordinate at) {
        return contentMap.getOrDefault(at, defaultValue);
    }
}
