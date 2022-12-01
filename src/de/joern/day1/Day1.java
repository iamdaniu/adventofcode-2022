package de.joern.day1;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 implements ProblemSolver {
    private Elf currentElf;
    private final List<Elf> elves = new ArrayList<>();
    private static final Comparator<Elf> BY_CALORIES = Comparator.comparing(Elf::totalCalories);

    private final Function<Collection<Elf>, Long> collector;

    private Day1(Function<Collection<Elf>, Long> collect) {
        collector = collect;
        currentElf = new Elf();
        elves.add(currentElf);
    }

    public static ProblemSolver day1_1() {
        return new Day1(Day1::day1_1_finish);
    }

    public static ProblemSolver day1_2() {
        return new Day1(Day1::day1_2_finish);
    }

    public void consider(String line) {
        if (line.isBlank()) {
            finishElf();
        } else {
            currentElf.add(Integer.parseInt(line.trim()));
        }
    }

    public long finished() {
        finishElf();
        return collector.apply(elves);
    }

    private static long day1_1_finish(Collection<Elf> elves) {
        return elves.stream()
                .max(BY_CALORIES)
                .map(Elf::totalCalories)
                .orElse(0L);
    }
    private static long day1_2_finish(Collection<Elf> elves) {
        return elves.stream()
                .sorted(BY_CALORIES.reversed())
                .limit(3)
                .mapToLong(Elf::totalCalories)
                .sum();
    }

    private void finishElf() {
        elves.add(currentElf);
        currentElf = new Elf();
    }

    static class Elf {
        private final List<Integer> foodPackages = new ArrayList<>();
        private long totalCalories;

        public void add(int calories) {
            foodPackages.add(calories);
            totalCalories += calories;
        }

        long totalCalories() {
            return totalCalories;
        }
    }
}
