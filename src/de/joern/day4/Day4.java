package de.joern.day4;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day4 implements ProblemSolver<Long> {
    private long fullOverlaps;
    private long totalOverlaps;
    private final Function<Day4, Long> score;

    public static ProblemSolver<Long> day4_1() {
        return new Day4(d -> d.fullOverlaps);
    }

    public static ProblemSolver<Long> day4_2() {
        return new Day4(d -> d.totalOverlaps);
    }

    private Day4(Function<Day4, Long> score) {
        this.score = score;
    }

    @Override
    public void consider(String line) {
        var split = line.split(",");
        Range range1 = parse(split[0]);
        Range range2 = parse(split[1]);
        var intersection = range1.intersection(range2);
        if (intersection.size() != 0) {
            totalOverlaps++;

            if (intersection.size() == range1.contained.size()
                    || intersection.size() == range2.contained.size()) {
                fullOverlaps++;
            }
        }
    }

    @Override
    public Long finished() {
        return score.apply(this);
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
        List<Integer> intersection(Range r) {
            List<Integer> result = new ArrayList<>(contained);
            result.retainAll(r.contained);
            return result;
        }
    }
}
