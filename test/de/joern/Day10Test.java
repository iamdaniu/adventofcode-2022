package de.joern;

import de.joern.day10.Day10;
import de.joern.day9.Day9;
import org.junit.jupiter.api.Test;

public class Day10Test extends DayTest {
    protected Day10Test() {
        super(10);
    }


    @Test
    void day10_1() {
        test(Day10.day10_1(), 13140);
    }

//    @Test
//    void day9_2() {
//        test(Day9.day9_2(), 1);
//    }
}
