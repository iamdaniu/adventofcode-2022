package de.joern.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class FSEntry {
    private final String name;

    protected FSEntry(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    abstract long size();
}

class FSFile extends FSEntry {
    private final long size;

    public FSFile(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    long size() {
        return size;
    }
}

class FSDirectory extends FSEntry {
    private final FSDirectory parent;
    private final List<FSEntry> entries = new ArrayList<>();

    private FSDirectory(String name, FSDirectory parent) {
        super(name);
        this.parent = parent;
    }
    public static FSDirectory root() {
        return new FSDirectory("/", null);
    }
    public FSDirectory subdirectory(String name) {
        final var result = new FSDirectory(name, this);
        entries.add(result);
        return result;
    }
    public FSDirectory parent() {
        return parent;
    }

    @Override
    long size() {
        return entries.stream()
                .mapToLong(FSEntry::size)
                .sum();
    }

//    public List<FSEntry> getEntries() {
//        return entries;
//    }

    void add(FSEntry entry) {
        entries.add(entry);
    }

    Optional<FSDirectory> findSubdirectory(String name) {
        return entries.stream()
                .filter(e -> name.equals(e.name()))
                .filter(e -> e instanceof FSDirectory)
                .map(e -> (FSDirectory) e)
                .findFirst();
    }

    List<FSDirectory> subdirectories() {
        return entries.stream()
                .filter(e -> e instanceof FSDirectory)
                .map(e -> (FSDirectory) e)
                .toList();
    }
}