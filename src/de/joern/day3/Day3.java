package de.joern.day3;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

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

    @Override
    public void consider(String line) {
        var rucksack = line.chars()
                .mapToObj(i -> (char) i)
                .collect(collectingAndThen(toList(), Rucksack::new));
        rucksacks.add(rucksack);
        Set<Character> compartment1 = new HashSet<>(rucksack.getCompartment(0));
        compartment1.retainAll(rucksack.getCompartment(1));
        for (char c : compartment1) {
            singleScore += priority(c);
        }
    }

    @Override
    public long finished() {
        return score.apply(this);
    }

    private static long scoreGroup(List<Rucksack> partition) {
        long result = 0;
        Set<Character> commonElements = new HashSet<>(partition.get(0).items());
        commonElements.retainAll(partition.get(1).items());
        commonElements.retainAll(partition.get(2).items());
        for (Character c : commonElements) {
            result += priority(c);
        }
        return result;
    }

    private static int priority(char c) {
        int correction = Character.isLowerCase(c)
                ? 'a' - 1
                : 'A' - 27;
        return c - correction;
    }

    static record Rucksack(List<Character> items) {
        List<Character> getCompartment(int i) {
            return (i == 0)
                    ? items.subList(0, items.size()/2)
                    : items.subList(items.size()/2, items.size());
        }
    }
}
