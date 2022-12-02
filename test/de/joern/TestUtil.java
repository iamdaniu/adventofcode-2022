package de.joern;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUtil {
    static void test(ProblemSolver solver, int day, long expected) {
        try {
            var values = fromFile(day);
            values.forEach(solver::consider);
            long result = solver.finished();
            assertEquals(expected, result);
        } catch (IOException x) {
            fail(x.getMessage());
        }
    }

    private static Stream<String> fromFile(int day) throws IOException {
        String filename = String.format("day%d.txt", day);
        Path path = Paths.get("data/test", filename);
        return Files.lines(path);
    }
}
