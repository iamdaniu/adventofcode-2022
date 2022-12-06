package de.joern;

import de.joern.day6.Day6;
import org.junit.jupiter.api.Test;

public class Day6Test extends DayTest {
    protected Day6Test() {
        super(6);
    }

    @Test
    void day6_1() {
        test(Day6.day6_1(), 5);
    }

//    @Test
//    void day6_2() {
//        test(Day5.day5_2(), "MCD".hashCode());
//    }
}
