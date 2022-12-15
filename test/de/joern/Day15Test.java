package de.joern;

import de.joern.day15.Day15;
import org.junit.jupiter.api.Test;

public class Day15Test extends DayTest {
    protected Day15Test() {
        super(15);
    }

    @Test
    void day15_1() {
        test(Day15.day15_1(10), 26L);
    }

//    @Test
//    void day11_2() {
//        test(Day11.day11_2(), 2713310158L);
//    }
}
