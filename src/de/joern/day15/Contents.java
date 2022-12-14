package de.joern.day15;

enum Contents {
    EMPTY("."),
    SENSOR("S"),
    BEACON("B");

    private final String display;

    Contents(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
