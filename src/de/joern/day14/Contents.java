package de.joern.day14;

enum Contents {
    EMPTY("."),
    SOLID("#"),
    SAND("o");

    private final String display;

    Contents(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
