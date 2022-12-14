package de.joern;

import de.joern.day11.Day11;
import org.junit.jupiter.api.Test;

public class Day11Test extends DayTest {
    protected Day11Test() {
        super(11);
    }


    @Test
    void day11_1() {
        test(Day11.day11_1(), 10605L);
    }
    @Test
    void day11_2() {
        test(Day11.day11_2(), 2713310158L);
    }
}
