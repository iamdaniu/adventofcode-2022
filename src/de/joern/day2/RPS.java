package de.joern.day2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

enum RPS {
    ROCK(1, "A", "X"),
    PAPER(2, "B", "Y"),
    SCISSORS(3, "C", "Z");

    public final int value;
    private final List<String> representations;

    RPS(int value, String... representations) {
        this.value = value;
        this.representations = Arrays.asList(representations);
    }

    static RPS from(String parse) {
        return Stream.of(RPS.values())
                .filter(rps -> rps.representations.contains(parse))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not a valid value: %s".formatted(parse)));
    }

    public RPS winsAgainst() {
        return switch (this) {
            case ROCK -> SCISSORS;
            case PAPER -> ROCK;
            case SCISSORS -> PAPER;
        };
    }
    public RPS losesAgainst() {
        return switch (this) {
            case ROCK -> PAPER;
            case PAPER -> SCISSORS;
            case SCISSORS -> ROCK;
        };
    }
}
