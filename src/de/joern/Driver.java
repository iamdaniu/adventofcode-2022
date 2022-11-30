package de.joern;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
        for (Problems p : Problems.values()) {
            execute(p);
        }
    }

    public static void execute(Problems problem) {
        try {
            List<ProblemSolver> solvers = problem.getSolvers();
            var input = getFile(problem);
            for (ProblemSolver solver : solvers) {
                Files.lines(input).forEach(solver::consider);
                solver.finished();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path getFile(Problems problem) {
        String filename = String.format("day%d.txt", problem.getDay());
        return Paths.get("data", filename);
    }
}

