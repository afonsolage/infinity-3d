package com.lagecompany.infinity.utils

import kotlin.math.max
import kotlin.math.min

fun <E> MutableList<E>.addAndReturn(value: E): E {
    this.add(value)
    return value
}

fun Float.clamp(min: Float, max: Float): Float {
    return max(min, min(max, this))
}