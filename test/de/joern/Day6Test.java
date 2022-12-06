package de.joern;

import de.joern.day6.Day6;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day6Test extends DayTest {
    protected Day6Test() {
        super(6);
    }

    @Test
    void day6_1() {
        test(Day6.day6_1(), List.of(5));
    }
    @Test
    void day6_2() {
        test(Day6.day6_2(), List.of(23));
    }
}
