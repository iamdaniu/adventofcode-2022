package de.joern.test;

import de.joern.ProblemSolver;
import de.joern.Problems;
import de.joern.day1.Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Test {
    private static final Map<Problems, List<Long>> EXPECTED_RESULTS = new EnumMap<>(Problems.class);

    static {
        EXPECTED_RESULTS.put(Problems.DAY1, List.of(24_000L, 45_000L));
    }

    public static void main(String[] args) throws IOException {
        boolean result = true;

        for (int i = 0; i < Problems.values().length; i++) {
            System.out.printf("Day %d%n", i + 1);
            Problems problem = Problems.values()[i];
            List<Long> expected = EXPECTED_RESULTS.get(problem);

            List<ProblemSolver> solvers = problem.getSolvers();
            for (int solve = 0; solve < solvers.size(); solve++) {
                result &= test(solvers.get(solve), fromFile(i + 1), expected.get(solve));
            }
        }
        System.out.println(result ? "all tests successful" : "test(s) failed");
    }

    private static boolean test(ProblemSolver solver, Stream<String> values, long expected) {
        values.forEach(solver::consider);
        long result = solver.finished();
        boolean correct = result == expected;
        System.out.println(correct ? "ok" : String.format("failed! expected %d, got %d", expected, result));
        return correct;
    }

    private static Stream<String> fromFile(int day) throws IOException {
        String filename = String.format("day%d.txt", day);
        Path path = Paths.get("data/test", filename);
        return Files.lines(path);
    }
}
