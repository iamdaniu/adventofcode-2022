package de.joern.day11;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@Data
@Builder
class Monkey {
    private List<Long> items;
    private UnaryOperator<Long> operation;
    private Integer divisibilityTest;
    private int targetOnTrue, targetOnFalse;
    private long inspections;
}
