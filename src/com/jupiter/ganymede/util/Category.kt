package com.jupiter.ganymede.util

/**
 * Created by nathan on 6/11/15.
 */
public interface Category<out T : Category<T>> {
    public val name: String
    public val parent: T
}