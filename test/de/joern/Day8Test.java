package de.joern;

import de.joern.day8.Day8;
import org.junit.jupiter.api.Test;

public class Day8Test extends DayTest {
    protected Day8Test() {
        super(8);
    }


    @Test
    void day8_1() {
        test(Day8.day8_1(), 21);
    }

    @Test
    void day8_2() {
        test(Day8.day8_2(), 8);
    }
}
