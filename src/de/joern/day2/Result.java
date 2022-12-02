package de.joern.day2;

import java.util.stream.Stream;

enum Result {
    WIN("Z"),
    LOSE("X"),
    DRAW("Y");

    private final String parsed;

    Result(String parsed) {
        this.parsed = parsed;
    }

    static Result from(String parse) {
        return Stream.of(Result.values())
                .filter(r -> parse.equals(r.parsed))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid value %s".formatted(parse)));
    }
}
