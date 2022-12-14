package de.joern.day11;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;

public class Day11 implements ProblemSolver<Integer> {
    private final List<Monkey> monkeys = new ArrayList<>();
    private Monkey.MonkeyBuilder currentMonkeyBuilder;

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
    public Integer finished() {
        if (currentMonkeyBuilder != null) {
            monkeys.add(currentMonkeyBuilder.build());
        }
        for (int i = 0; i < 20; i++) {
            inspectAllMonkeys();
            final int j = 0;
        }
        monkeys.sort(Comparator.comparing(Monkey::getInspections).reversed());
        return monkeys.get(0).getInspections() * monkeys.get(1).getInspections();
    }

    private void setTestResult(Matcher m) {
        Consumer<Integer> throwTarget = "true".equals(m.group(1))
                ? currentMonkeyBuilder::targetOnTrue
                : currentMonkeyBuilder::targetOnFalse;
        throwTarget.accept(Integer.parseInt(m.group(2)));
    }

    private static Function<Integer, Integer> parseOperation(Matcher matcher) {
        var group1 = matcher.group(1);
        UnaryOperator<Integer> left = "old".equals(group1)
                ? i -> i
                : i -> Integer.parseInt(group1);
        var group3 = matcher.group(3);
        UnaryOperator<Integer> right = "old".equals(group3)
                ? i -> i
                : i -> Integer.parseInt(group3);
        return "+".equals(matcher.group(2))
                ? i -> left.apply(i) + right.apply(i)
                : i -> left.apply(i) * right.apply(i);
    }

    private static List<Integer> parseList(String group) {
        return new ArrayList<>(Arrays.stream(group.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList());
    }

    private void inspectAllMonkeys() {
        final int i = 0;
        for (Monkey m : monkeys) {
            for (int item : m.getItems()) {
                int newItem = m.getOperation().apply(item);
                newItem /= 3;
                int throwTo;
                if (newItem % m.getDivisibilityTest() == 0) {
                    throwTo = m.getTargetOnTrue();
                } else {
                    throwTo = m.getTargetOnFalse();
                }
                monkeys.get(throwTo).getItems().add(newItem);
                m.setInspections(m.getInspections()+1);
            }
            m.setItems(new ArrayList<>());
        }
    }
}
