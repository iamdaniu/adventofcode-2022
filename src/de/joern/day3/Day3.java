package de.joern.day3;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Function;

public class Day3 implements ProblemSolver {
    private final List<Rucksack> rucksacks = new ArrayList<>();
    private long singleScore;
    private final Function<Day3, Long> score;

    public Day3(Function<Day3, Long> scoreRetriever) {
        score = scoreRetriever;
    }

    public static ProblemSolver day3_1() {
        return new Day3(d -> d.singleScore);
    }
    public static ProblemSolver day3_2() {
        return new Day3(Day3::groupedScore);
    }

    private long groupedScore() {
        long result = 0;
        List<Rucksack> partition = new ArrayList<>();
        for (Rucksack r : rucksacks) {
            partition.add(r);
            if (partition.size() == 3) {
                result += scoreGroup(partition);
                partition.clear();
            }
        }
        return result;
    }

    private long scoreGroup(List<Rucksack> partition) {
        long result = 0;
        Set<Character> commonElements = new HashSet<>(toSet(partition.get(0).items()));
        commonElements.retainAll(toSet(partition.get(1).items()));
        commonElements.retainAll(toSet(partition.get(2).items()));
        for (Character c : commonElements) {
            result += priority(c);
        }
        return result;
    }

    @Override
    public void consider(String line) {
        var rucksack = new Rucksack(line.toCharArray());
        rucksacks.add(rucksack);
        Set<Character> compartment1 = new HashSet<>(toSet(rucksack.getCompartment(0)));
        var compartment2 = toSet(rucksack.getCompartment(1));
        compartment1.retainAll(compartment2);
        for (char c : compartment1) {
            singleScore += priority(c);
        }
    }

    @Override
    public long finished() {
        return score.apply(this);
    }

    private static Set<Character> toSet(char[] compartment) {
        Set<Character> result = new HashSet<>();
        for (char c : compartment) {
            result.add(c);
        }
        return result;
    }

    private static int priority(char c) {
        int result;
        if (Character.isLowerCase(c)) {
            result = c - 'a' + 1;
        } else {
            result = c - 'A' + 27;
        }
        return result;
    }

    static record Rucksack(char[] items) {
        char[] getCompartment(int i) {
            return (i == 0)
                    ? Arrays.copyOf(items, items.length/2)
                    : Arrays.copyOfRange(items, items.length/2, items.length);
        }
    }
}
