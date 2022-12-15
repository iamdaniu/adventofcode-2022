package de.joern;

import java.util.regex.Pattern;

public record Coordinate(int x, int y) {
    public Coordinate(String x, String y) {
        this (Integer.parseInt(x), Integer.parseInt(y));
    }
    public static Coordinate parse(Pattern pattern, String toParse) {
        var matcher = pattern.matcher(toParse);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("could not parse " + toParse);
        }
        return new Coordinate(matcher.group(1), matcher.group(2));
    }

    public String toString() {
        return "(%d/%d)".formatted(x, y);
    }
}
