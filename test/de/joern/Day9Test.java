package de.joern;

import de.joern.day9.Day9;
import org.junit.jupiter.api.Test;

public class Day9Test extends DayTest {
    protected Day9Test() {
        super(9);
    }


    @Test
    void day9_1() {
        test(Day9.day9_1(), 13);
    }

    @Test
    void day9_2() {
        test(Day9.day9_2(), 1);
    }
}
