package de.joern.day9;

import de.joern.Coordinate;

import java.util.stream.Stream;

enum Movement {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int yMove;
    private final int xMove;

    Movement(int yMove, int xMove) {
        this.yMove = yMove;
        this.xMove = xMove;
    }

    Coordinate apply(Coordinate original) {
        return new Coordinate(original.x() + xMove, original.y() + yMove);
    }

    static Movement from(String shortMove) {
        return Stream.of(Movement.values())
                .filter(m -> m.name().startsWith(shortMove))
                .findFirst()
                .orElseThrow();
    }
}
