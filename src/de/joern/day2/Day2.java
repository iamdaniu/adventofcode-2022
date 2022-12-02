package de.joern.day2;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.LongSupplier;

public class Day2<T extends LongSupplier> implements ProblemSolver {
    private final BiFunction<String, String, T> create;
    private final List<T> moves = new ArrayList<>();

    private Day2(BiFunction<String, String, T> create) {
        this.create = create;
    }

    public static ProblemSolver day2_1() {
        return new Day2<>((s0, s1) -> new Play(
                RPS.from(s0),
                RPS.from(s1)
        ));
    }

    public static ProblemSolver day2_2() {
        return new Day2<>((s0, s1) -> new DesiredMove(
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
                .mapToLong(LongSupplier::getAsLong)
                .sum();
    }

}
