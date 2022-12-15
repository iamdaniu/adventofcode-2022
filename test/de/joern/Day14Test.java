package de.joern;

import de.joern.day14.Day14;
import org.junit.jupiter.api.Test;

public class Day14Test extends DayTest {
    protected Day14Test() {
        super(14);
    }


    @Test
    void day14_1() {
        test(Day14.day14_1(), 24);
    }

//    @Test
//    void day11_2() {
//        test(Day11.day11_2(), 2713310158L);
//    }
}
