package de.joern;

import de.joern.day1.Day1;
import de.joern.day10.Day10;
import de.joern.day11.Day11;
import de.joern.day2.Day2;
import de.joern.day3.Day3;
import de.joern.day4.Day4;
import de.joern.day5.Day5;
import de.joern.day6.Day6;
import de.joern.day7.Day7;
import de.joern.day8.Day8;
import de.joern.day9.Day9;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum Problems {
        DAY1(1, de.joern.day1.Day1::day1_1, Day1::day1_2),
        DAY2(2, Day2::day2_1, Day2::day2_2),
        DAY3(3, Day3::day3_1, Day3::day3_2),
        DAY4(4, Day4::day4_1, Day4::day4_2),
        DAY5(5, Day5::day5_1, Day5::day5_2),
        DAY6(6, Day6::day6_1, Day6::day6_2),
        DAY7(7, Day7::day7_1, Day7::day7_2),
        DAY8(8, Day8::day8_1, Day8::day8_2),
        DAY9(9, Day9::day9_1, Day9::day9_2),
        DAY10(10, Day10::new),
        DAY11(11, Day11::day11_1)
    ;

    public final int day;

    private final List<Supplier<ProblemSolver<?>>> solver;

    @SafeVarargs
    Problems(int day, Supplier<ProblemSolver<?>>... solver) {
        this.day = day;
        this.solver = Arrays.asList(solver);
    }

    public int getDay() {
        return day;
    }

    public List<ProblemSolver<?>> getSolvers() {
        return solver.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
    }
}
