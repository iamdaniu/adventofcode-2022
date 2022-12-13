package de.joern;

import de.joern.day7.Day7;
import org.junit.jupiter.api.Test;

public class Day7Test extends DayTest {
    protected Day7Test() {
        super(7);
    }


    @Test
    void day7_1() {
        test(Day7.day7_1(), 95_437L);
    }

    @Test
    void day7_2() {
        test(Day7.day7_2(), 24_933_642L);
    }
}
