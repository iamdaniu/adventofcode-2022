package de.joern.day6;

import de.joern.ProblemSolver;

import java.util.*;

public class Day6 implements ProblemSolver<List<Integer>> {
    List<Integer> startingPerLine = new ArrayList<>();
    final int requiredLength;

    public Day6(int requiredLength) {
        this.requiredLength = requiredLength;
    }

    public static ProblemSolver<List<Integer>> day6_1() {
        return new Day6(4);
    }

    public static ProblemSolver<List<Integer>> day6_2() {
        return new Day6(14);
    }

    @Override
    public void consider(String line) {
        char current;
        Queue<Character> queue = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            current = line.charAt(i);
            queue.add(current);
            if (!allUnique(queue)) {
                queue.remove();
            }
            if (queue.size() == requiredLength) {
                startingPerLine.add(i+1);
                break;
            }
        }
    }

    private static boolean allUnique(Collection<Character> collection) {
        Set<Character> set = new HashSet<>(collection);
        return set.size() == collection.size();
    }

    @Override
    public List<Integer> finished() {
        return startingPerLine;
    }
}
