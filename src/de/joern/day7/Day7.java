package de.joern.day7;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Day7 implements ProblemSolver<Long> {
    private final FSDirectory root = FSDirectory.root();
    private FSDirectory pwd;
    private final DirectoryVisitor resultCreator;

    public Day7(DirectoryVisitor resultCreator) {
        this.resultCreator = resultCreator;
    }

    public static ProblemSolver<Long> day7_1() {
        return new Day7(new MaxSizeFinder());
    }

    public static ProblemSolver<Long> day7_2() {
        return new Day7(new MinSufficientFinder());
    }

    @Override
    public void consider(String line) {
        if (line.startsWith("$")) {
            if (line.startsWith("$ cd ")) {
                String targetDir = line.substring(5);
                if ("/".equals(targetDir)) {
                    pwd = root;
                } else if ("..".equals(targetDir)) {
                    pwd = pwd.parent();
                } else {
                    pwd = pwd.findSubdirectory(targetDir)
                            .orElseGet(() -> pwd.subdirectory(targetDir));
                }
            } else if (!line.startsWith("$ ls")) {
                throw new IllegalArgumentException("could not handle: %s".formatted(line));
            }
        } else if (!line.startsWith("dir ")){
            Pattern pattern = Pattern.compile("([0-9]+) (.*)");
            var matcher = pattern.matcher(line);
            if (matcher.matches()) {
                FSFile file = new FSFile(matcher.group(2), Long.parseLong(matcher.group(1)));
                pwd.add(file);
            } else {
                throw new IllegalArgumentException("could not handle: %s".formatted(line));
            }
        }
    }

    @Override
    public Long finished() {
        Deque<FSDirectory> directories = new ArrayDeque<>();
        directories.add(root);
        while (!directories.isEmpty()) {
            var current = directories.pop();
            resultCreator.accept(current);
            directories.addAll(current.subdirectories());
        }
        return resultCreator.get();
    }

    interface DirectoryVisitor extends Consumer<FSDirectory>, Supplier<Long> { }

    static class MaxSizeFinder implements DirectoryVisitor {
        private final List<FSDirectory> matching = new ArrayList<>();

        @Override
        public void accept(FSDirectory directory) {
            if (directory.size() < 100_000) {
                matching.add(directory);
            }
        }

        @Override
        public Long get() {
            return matching.stream()
                    .mapToLong(FSEntry::size)
                    .sum();
        }
    }

    static class MinSufficientFinder implements DirectoryVisitor {
        private FSDirectory toDelete;
        private long removeTarget;

        @Override
        public void accept(FSDirectory directory) {
            var directorySize = directory.size();
            if (toDelete == null && directory.parent() == null) {
                long currentFree = 70_000_000L - directorySize;
                removeTarget = 30_000_000L - currentFree;
                toDelete = directory;
            } else if (directorySize > removeTarget) {
                if (toDelete.size() > directorySize) {
                    toDelete = directory;
                }
            }
        }

        @Override
        public Long get() {
            return Optional.ofNullable(toDelete)
                    .map(FSEntry::size)
                    .orElse(0L);
        }
    }
}
