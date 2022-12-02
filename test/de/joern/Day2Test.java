package de.joern;

import de.joern.day2.Day2;
import org.junit.jupiter.api.Test;

public class Day2Test extends DayTest {
    protected Day2Test() {
        super(2);
    }

    @Test
    void day2_1() {
        test(Day2.day2_1(), 15L);
    }

    @Test
    void day2_2() {
        test(Day2.day2_2(), 12L);
    }
}
