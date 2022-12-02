package de.joern.day2;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Day2 implements ProblemSolver {
    private final BiFunction<String, String, Long> create;
    private final List<Long> moves = new ArrayList<>();

    private Day2(BiFunction<String, String, Long> create) {
        this.create = create;
    }

    public static ProblemSolver day2_1() {
        return new Day2((s0, s1) -> play(
                RPS.from(s0),
                RPS.from(s1)
        ));
    }

    public static ProblemSolver day2_2() {
        return new Day2((s0, s1) -> fromDesiredResult(
                RPS.from(s0),
                Result.from(s1))
        );
    }

    public void consider(String line) {
        String[] currentMoves = line.split(" ");
        moves.add(create.apply(currentMoves[0], currentMoves[1]));
    }

    public long finished() {
        return moves.stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private static long play(RPS opponent, RPS self) {
        long result = self.value;
        if (opponent == self) {
            result += 3;
        } else if (self.winsAgainst() == opponent) {
            result += 6;
        }
        return result;
    }

    private static long fromDesiredResult(RPS opponent, Result desiredResult) {
        RPS myMove = switch (desiredResult) {
            case DRAW -> opponent;
            case WIN -> opponent.losesAgainst();
            default -> opponent.winsAgainst();
        };
        return play(opponent, myMove);
    }
}
