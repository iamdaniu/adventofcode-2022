package de.joern;

import de.joern.day1.Day1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum Problems {
        DAY1(1, de.joern.day1.Day1::day1_1, Day1::day1_2)
    ;

    public final int day;

    private final List<Supplier<ProblemSolver>> solver;

    @SafeVarargs
    Problems(int day, Supplier<ProblemSolver>... solver) {
        this.day = day;
        this.solver = Arrays.asList(solver);
    }

    public int getDay() {
        return day;
    }

    public List<ProblemSolver> getSolvers() {
        return solver.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
    }
}
