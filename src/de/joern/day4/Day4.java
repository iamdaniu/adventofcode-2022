package de.joern.day4;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day4 implements ProblemSolver {
    private int fullOverlaps;

    public static ProblemSolver day4_1() {
        return new Day4();
    }

    @Override
    public void consider(String line) {
        var split = line.split(",");
        Range range1 = parse(split[0]);
        Range range2 = parse(split[1]);
        if (range1.fullyContains(range2) ||
                range2.fullyContains(range1)) {
            fullOverlaps++;
        }
    }

    @Override
    public long finished() {
        return fullOverlaps;
    }

    static Range parse(String range) {
        var split = range.split("-");
        int start = Integer.parseInt(split[0]);
        int end = Integer.parseInt(split[1]);
        return new Range(start, end);
    }
    static record Range(List<Integer> contained) {
        public Range(int start, int end) {
            this(IntStream.range(start, end+1)
                    .boxed()
                    .toList());
        }
        boolean fullyContains(Range r) {
            return intersection(r).size() == contained.size();
        }
        List<Integer> intersection(Range r) {
            List<Integer> result = new ArrayList<>(contained);
            result.retainAll(r.contained);
            return result;
        }
    }
}
