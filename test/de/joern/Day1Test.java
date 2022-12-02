package de.joern;

import de.joern.day1.Day1;
import org.junit.jupiter.api.Test;

public class Day1Test extends DayTest {

    protected Day1Test() {
        super(1);
    }

    @Test
    void day1_1() {
        test(Day1.day1_1(), 24_000L);
    }

    @Test
    void day1_2() {
        test(Day1.day1_2(), 45_000L);
    }
}
