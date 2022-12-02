package de.joern.day2;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.joern.day2.RPS.PAPER;
import static de.joern.day2.RPS.ROCK;
import static de.joern.day2.RPS.SCISSORS;

public class Day2<T> implements ProblemSolver {
    private final BiFunction<String, String, T> create;
    private final Function<T, Long> evaluator;
    private final List<T> moves = new ArrayList<>();

    private Day2(BiFunction<String, String, T> create, Function<T, Long> evaluator) {
        this.create = create;
        this.evaluator = evaluator;
    }

    public static ProblemSolver day2_1() {
        return new Day2<>((s0, s1) -> new Move(
                RPS.from(s0),
                RPS.from(s1)
        ), Move::evaluate);
    }

    public static ProblemSolver day2_2() {
        return new Day2<>((s0, s1) -> new DesiredMove(
                RPS.from(s0),
                Result.from(s1)),
                DesiredMove::evaluate
        );
    }

    public void consider(String line) {
        String[] currentMoves = line.split(" ");
        moves.add(create.apply(currentMoves[0], currentMoves[1]));
    }

    public long finished() {
        return moves.stream()
                .map(evaluator)
                .mapToLong(Long::longValue)
                .sum();
    }

    static record DesiredMove(RPS opponent, Result desiredResult) {
        long evaluate() {
            RPS myMove = switch (desiredResult) {
                case DRAW:
                    yield opponent;
                case WIN:
                    yield switch (opponent) {
                        case ROCK:
                            yield PAPER;
                        case PAPER:
                            yield SCISSORS;
                        case SCISSORS:
                            yield ROCK;
                    };
                default:
                    yield switch (opponent) {
                        case ROCK:
                            yield SCISSORS;
                        case PAPER:
                            yield ROCK;
                        case SCISSORS:
                            yield PAPER;
                    };
            };
            Move move = new Move(opponent, myMove);
            return move.evaluate();
        }
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
}
