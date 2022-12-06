package de.joern.day5;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class Day5 implements ProblemSolver<String> {
    private final List<Stack<Character>> stacks = new ArrayList<>();
    private boolean stacksFinished;
    private final BiConsumer<Day5, Move> mover;

    private Day5(BiConsumer<Day5, Move> mover) {
        this.mover = mover;
    }

    public static ProblemSolver<String> day5_1() {
        return new Day5(Day5::executeMove);
    }

    public static ProblemSolver<String> day5_2() {
        return new Day5(Day5::move9001);
    }

    @Override
    public void consider(String line) {
        if (line.isEmpty()) {
            for (int i = 0; i < stacks.size(); i++) {
                stacks.set(i, reverse(stacks.get(i)));
            }
            stacksFinished = true;
            return;
        }
        if (!stacksFinished) {
            fillStacks(line);
        } else {
            Optional.ofNullable(parseMove(line))
                            .ifPresent(move -> mover.accept(this, move));
        }
    }

    private void executeMove(Move move) {
        var fromStack = stacks.get(move.fromCrate-1);
        var toStack = stacks.get(move.toCrate-1);
        for (int i = 0; i < move.moveCount; i++) {
            toStack.push(fromStack.pop());
        }
    }
    private void move9001(Move move) {
        var fromStack = stacks.get(move.fromCrate-1);
        var toStack = stacks.get(move.toCrate-1);
        Stack<Character> intermediate = new Stack<>();
        for (int i = 0; i < move.moveCount; i++) {
            intermediate.push(fromStack.pop());
        }
        while (!intermediate.isEmpty()) {
            toStack.push(intermediate.pop());
        }
    }

    private void fillStacks(String line) {
        // stack number line
        if (!line.contains("[")) {
            return;
        }
        int index = 1;
        int stackNum = 0;
        while (index < line.length()) {
            if (stackNum >= stacks.size()) {
                stacks.add(new Stack<>());
            }
            char toAdd = line.charAt(index);
            if (toAdd != ' ') {
                stacks.get(stackNum).push(toAdd);
            }
            index += 4;
            stackNum++;
        }
    }

    static final Pattern moveRe = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");
    private static Move parseMove(String line) {
        Move result = null;
        var matcher = moveRe.matcher(line);
        if (matcher.matches()) {
            result = new Move(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        }
        return result;
    }

    private static Stack<Character> reverse(Stack<Character> toReverse) {
        Stack<Character> reversed = new Stack<>();
        while (!toReverse.isEmpty()) {
            reversed.push(toReverse.pop());
        }
        return reversed;
    }

    @Override
    public String finished() {
        StringBuilder topelements = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            topelements.append(stack.pop());
        }
        System.out.println(topelements);
        return topelements.toString();
    }

    static record Move(int moveCount, int fromCrate, int toCrate) {
    }
}
