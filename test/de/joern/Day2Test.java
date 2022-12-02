package de.joern;

import de.joern.day2.Day2;
import org.junit.jupiter.api.Test;

public class Day2Test {
    @Test
    void day2_1() {
        TestUtil.test(Day2.day2_1(), 2, 15L);
    }

    @Test
    void day2_2() {
        TestUtil.test(Day2.day2_2(), 2, 12L);
    }
}
