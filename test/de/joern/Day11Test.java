package de.joern;

import de.joern.day10.Day10;
import de.joern.day11.Day11;
import org.junit.jupiter.api.Test;

public class Day11Test extends DayTest {
    protected Day11Test() {
        super(11);
    }


    @Test
    void day11() {
        test(new Day11(), 10605);
    }
}
