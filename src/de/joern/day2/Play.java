package de.joern.day2;

import java.util.function.LongSupplier;

record Play(RPS opponent, RPS self) implements LongSupplier {
    public long getAsLong() {
        long result = self.value;
        if (opponent == self) {
            result += 3;
        } else if (self.winsAgainst() == opponent) {
            result += 6;
        }
        return result;
    }
}
