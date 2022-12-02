package de.joern.day2;

import java.util.function.LongSupplier;

record DesiredMove(RPS opponent, Result desiredResult) implements LongSupplier {
    public long getAsLong() {
        RPS myMove = switch (desiredResult) {
            case DRAW -> opponent;
            case WIN -> opponent.losesAgainst();
            default -> opponent.winsAgainst();
        };
        Play play = new Play(opponent, myMove);
        return play.getAsLong();
    }
}
