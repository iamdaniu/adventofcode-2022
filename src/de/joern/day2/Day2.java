package de.joern.day2;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static de.joern.day2.Day2.RPS.ROCK;
import static de.joern.day2.Day2.RPS.SCISSORS;

public class Day2 implements ProblemSolver {
    private final List<Move> moves = new ArrayList<>();

    public static ProblemSolver day2_1() {
        return new Day2();
    }

    public void consider(String line) {
        String[] currentMoves = line.split(" ");
        Move currentMove = new Move(
                RPS.from(currentMoves[0]),
                RPS.from(currentMoves[1])
        );
        moves.add(currentMove);
    }

    public long finished() {
        return moves.stream()
                .mapToLong(Move::evaluate)
                .sum();
    }

    static record Move(RPS opponent, RPS self) {
        long evaluate() {
            long result = self.value;
            if (opponent == self) {
                return result + 3;
            }
            result += switch (self) {
                case ROCK -> opponent == SCISSORS
                        ? 6
                        : 0;
                case PAPER -> opponent == ROCK
                        ? 6
                        : 0;
                case SCISSORS -> opponent == RPS.PAPER
                        ? 6
                        : 0;
            };
            return result;
        }
    }

    enum RPS {
        ROCK(1, "A", "X"),
        PAPER(2, "B", "Y"),
        SCISSORS(3, "C", "Z");

        private final int value;
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


    }
}
