package de.joern.day11;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Day11 implements ProblemSolver<Long> {
    private final List<Monkey> monkeys = new ArrayList<>();
    private Monkey.MonkeyBuilder currentMonkeyBuilder;
    private final int rounds;
    private final UnaryOperator<Long> adjustWorryLevel;
    private long totalDivisor;

    private Day11(int rounds, UnaryOperator<Long> adjustWorryLevel) {
        this.rounds = rounds;
        this.adjustWorryLevel = adjustWorryLevel;
    }

    public static ProblemSolver<Long> day11_1() {
        return new Day11(20, l -> l/3);
    }

    public static ProblemSolver<Long> day11_2() {
        return new Day11(10_000, l -> l);
    }

    private final List<LineExecutor> lineExecutors = List.of(
            new LineExecutor("Monkey .*:", m -> currentMonkeyBuilder = Monkey.builder()),
            new LineExecutor(" *Starting items: (.*)", m -> currentMonkeyBuilder.items(parseList(m.group(1)))),
            new LineExecutor(" *Operation: new = (old|[0-9]+) ([+*]) (old|[0-9]+)", m -> currentMonkeyBuilder.operation(parseOperation(m))),
            new LineExecutor(" *Test: divisible by ([0-9]+)", m -> currentMonkeyBuilder.divisibilityTest(Integer.parseInt(m.group(1)))),
            new LineExecutor(" *If (true|false): throw to monkey ([0-9])+", this::setTestResult),
            new LineExecutor("^$", m -> {
                monkeys.add(currentMonkeyBuilder.build());
                currentMonkeyBuilder = null; })
    );

    @Override
    public void consider(String line) {
        for (LineExecutor executor : lineExecutors) {
            executor.consider(line);
        }
    }

    @Override
    public Long finished() {
        if (currentMonkeyBuilder != null) {
            monkeys.add(currentMonkeyBuilder.build());
        }
        totalDivisor = monkeys.stream()
                .mapToLong(Monkey::getDivisibilityTest)
                .reduce((l1, l2) -> l1*l2)
                .orElse(0L);
        for (int i = 0; i < rounds; i++) {
            inspectAllMonkeys();
            System.out.println("round " + i + ": " + monkeys.stream().map(Monkey::getItems).map(Objects::toString).collect(Collectors.joining(", ")));
        }
        System.out.println(monkeys.stream().map(Monkey::getInspections).map(Objects::toString).collect(Collectors.joining(", ")));
        monkeys.sort(Comparator.comparing(Monkey::getInspections).reversed());
        return monkeys.get(0).getInspections() * monkeys.get(1).getInspections();
    }

    private void setTestResult(Matcher m) {
        Consumer<Integer> throwTarget = "true".equals(m.group(1))
                ? currentMonkeyBuilder::targetOnTrue
                : currentMonkeyBuilder::targetOnFalse;
        throwTarget.accept(Integer.parseInt(m.group(2)));
    }

    private static UnaryOperator<Long> parseOperation(Matcher matcher) {
        var group1 = matcher.group(1);
        UnaryOperator<Long> left = "old".equals(group1)
                ? i -> i
                : i -> Long.parseLong(group1);
        var group3 = matcher.group(3);
        UnaryOperator<Long> right = "old".equals(group3)
                ? i -> i
                : i -> Long.parseLong(group3);
        return "+".equals(matcher.group(2))
                ? i -> left.apply(i) + right.apply(i)
                : i -> left.apply(i) * right.apply(i);
    }

    private static List<Long> parseList(String group) {
        return new ArrayList<>(Arrays.stream(group.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList());
    }

    private void inspectAllMonkeys() {
        for (Monkey m : monkeys) {
            for (long item : m.getItems()) {
                long newItem = m.getOperation().apply(item);
                newItem = adjustWorryLevel.apply(newItem);
                int throwTo;
                if (newItem % m.getDivisibilityTest() == 0) {
                    throwTo = m.getTargetOnTrue();
                } else {
                    throwTo = m.getTargetOnFalse();
                }
                newItem = newItem % totalDivisor;
                monkeys.get(throwTo).getItems().add(newItem);
                m.setInspections(m.getInspections()+1);
            }
            m.setItems(new ArrayList<>());
        }
    }
}
