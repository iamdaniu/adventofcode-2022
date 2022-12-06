package de.joern;

public interface ProblemSolver<T> {
    void consider(String line);

    T finished();
}
