package de.joern;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
        System.out.printf("solving %d problems%n", Problems.values().length);
        for (Problems p : Problems.values()) {
            execute(p);
        }
    }

    private static void execute(Problems problem) {
        System.out.printf("Day %d:%n", problem.day);
        try {
            List<ProblemSolver> solvers = problem.getSolvers();
            var input = getFile(problem);
            for (ProblemSolver solver : solvers) {
                Files.lines(input).forEach(solver::consider);
                System.out.println(solver.finished());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getFile(Problems problem) {
        String filename = String.format("day%d.txt", problem.getDay());
        return Paths.get("data", filename);
    }
}

