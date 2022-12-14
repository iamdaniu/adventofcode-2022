package de.joern.day10;

import de.joern.ProblemSolver;

import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class Day10 implements ProblemSolver<Integer> {
    private int currentCycle = 0;
    private int registerValue = 1;
    private int signalStrengthSum;

    private static final Pattern LINE_PATTERN = Pattern.compile("([a-z]+) ?([-0-9]*)");

    enum Command implements BiFunction<Integer, Integer, Integer> {
        ADDX(2) { public Integer apply(Integer original, Integer argument) { return original + argument; }},
        NOOP(1) { public Integer apply(Integer original, Integer argument) { return original; }}
        ;

        private final int cycles;

        Command(int cycles) {
            this.cycles = cycles;
        }

        static Command from(String command) {
            return switch (command) {
                case "noop" -> NOOP;
                case "addx" -> ADDX;
                default -> null;
            };
        }
    }

    @Override
    public void consider(String line) {
        var matcher = LINE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("could not parse " + line);
        }
        Command command = Command.from(matcher.group(1));
        for (int i = 0; i < command.cycles; i++) {
            step();
        }
        if (!matcher.group(2).isEmpty()) {
            Integer argument = Integer.parseInt(matcher.group(2));
            registerValue = command.apply(registerValue, argument);
        }
    }

    public Integer finished() {
        System.out.println();
        return signalStrengthSum;
    }

    private void step() {
        var crtPosition = currentCycle % 40;
        if (crtPosition == 0) {
            System.out.println();
        }
        System.out.print(Math.abs(crtPosition - registerValue) < 2
                ? "#"
                : ".");

        currentCycle++;
        if (currentCycle >= 20 && ((currentCycle - 20) % 40 == 0)) {
            int currentSignalStrength = currentCycle * registerValue;
            signalStrengthSum += currentSignalStrength;
        }
    }
}
