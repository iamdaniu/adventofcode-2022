package de.joern.day11;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LineExecutor {
    private final Pattern pattern;
    private final Consumer<Matcher> matcherExecutor;

    LineExecutor(String pattern, Consumer<Matcher> matcherExecutor) {
        this.pattern = Pattern.compile(pattern);
        this.matcherExecutor = matcherExecutor;
    }

    public boolean consider(String line) {
        var matcher = pattern.matcher(line);
        if (matcher.matches()) {
            matcherExecutor.accept(matcher);
            return true;
        }
        return false;
    }
}
