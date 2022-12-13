package de.joern.day7;

import de.joern.ProblemSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

public class Day7 implements ProblemSolver<Long> {
    private final FSDirectory root = FSDirectory.root();
    private FSDirectory pwd;

    public static ProblemSolver<Long> day7_1() {
        return new Day7();
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
        List<FSDirectory> matching = new ArrayList<>();
        directories.add(root);
        while (!directories.isEmpty()) {
            var current = directories.pop();
            var size = current.size();
            if (size < 100_000) {
                matching.add(current);
            }
            directories.addAll(current.subdirectories());
        }
        return matching.stream()
                .mapToLong(FSEntry::size)
                .sum();
    }
}
