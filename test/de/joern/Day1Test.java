package de.joern;

import de.joern.day1.Day1;
import org.junit.jupiter.api.Test;

public class Day1Test {
    @Test
    void day1_1() {
        TestUtil.test(Day1.day1_1(), 1, 24_000L);
    }

    @Test
    void day1_2() {
        TestUtil.test(Day1.day1_2(), 1, 45_000L);
    }
}
