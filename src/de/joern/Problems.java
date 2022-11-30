package de.joern;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum Problems {
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
