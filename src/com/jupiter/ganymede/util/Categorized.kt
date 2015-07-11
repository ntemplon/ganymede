package com.jupiter.ganymede.util

/**
 * Created by nathan on 5/21/15.
 */
public interface Categorized<out T : Category<T>> {
    public val category: T
}
