package com.pastiche.pastiche.utils;

/**
 * Created by Aria Pahlavan on 1/1/17.
 */

/**
 * Represents an operation that accepts a single input argument and returns no
 * result.
 *
 * @param <T> the type of the input to the operation
 */
@FunctionalInterface
public interface PConsumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

}
