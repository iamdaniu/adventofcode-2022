package de.joern;

import de.joern.day4.Day4;
import org.junit.jupiter.api.Test;

public class Day4Test extends DayTest {
    protected Day4Test() {
        super(4);
    }

    @Test
    void day4_1() {
        test(Day4.day4_1(), 2L);
    }

    @Test
    void day4_2() {
        test(Day4.day4_2(), 4L);
    }
}
