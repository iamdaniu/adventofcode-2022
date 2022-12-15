package de.joern;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Grid<T> {
    private final Map<Coordinate, T> contentMap = new HashMap<>();
    private final T defaultValue;

    private int highestY = Integer.MAX_VALUE;
    private int leftmostX = Integer.MAX_VALUE, rightmostX;
    @Getter
    private int lowestY;

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
        for (int y = highestY; y < lowestY + 1; y++) {
            for (int x = leftmostX; x < rightmostX + 1; x++) {
                System.out.print(contentsAt(x, y));
            }
            System.out.println();
        }
    }

    public T contentsAt(int x, int y) {
        return contentMap.getOrDefault(new Coordinate(x, y), defaultValue);
    }

    public Stream<Coordinate> row(int row) {
        int startX = leftmostX;
        int endX = rightmostX;
        return Stream.iterate(startX, x -> x <= endX, x -> x+1)
                .map(x -> new Coordinate(x, row));
    }
}
