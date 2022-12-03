package de.joern;

import de.joern.day3.Day3;
import org.junit.jupiter.api.Test;

public class Day3Test extends DayTest {
    protected Day3Test() {
        super(3);
    }

    @Test
    void day3_1() {
        test(Day3.day3_1(), 157L);
    }

    @Test
    void day3_2() {
        test(Day3.day3_2(), 70L);
    }
}
