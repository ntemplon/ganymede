package com.jupiter.ganymede.util

/**
 * Created by nathan on 8/28/15.
 */
public fun <T> T.apply(operation: (T) -> Unit): T {
    operation.invoke(this)
    return this
}