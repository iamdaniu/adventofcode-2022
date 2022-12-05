package de.joern;

import de.joern.day5.Day5;
import org.junit.jupiter.api.Test;

public class Day5Test extends DayTest {
    protected Day5Test() {
        super(5);
    }

    @Test
    void day5_1() {
        test(Day5.day5_1(), "CMZ".hashCode());
    }

    @Test
    void day5_2() {
        test(Day5.day5_2(), "MCD".hashCode());
    }
}
