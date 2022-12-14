package de.joern;

public record Coordinate(int x, int y) {
    public String toString() {
        return "(%d/%d)".formatted(x, y);
    }
}
