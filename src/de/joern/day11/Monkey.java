package de.joern.day11;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
@Builder
class Monkey {
    private List<Integer> items;
    private Function<Integer, Integer> operation;
    private Integer divisibilityTest;
    private int targetOnTrue, targetOnFalse;
    private int inspections;
}
