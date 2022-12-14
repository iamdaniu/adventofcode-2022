package de.joern;

import de.joern.day10.Day10;
import org.junit.jupiter.api.Test;

public class Day10Test extends DayTest {
    protected Day10Test() {
        super(10);
    }


    @Test
    void day10() {
        test(new Day10(), 13140);
    }
}
