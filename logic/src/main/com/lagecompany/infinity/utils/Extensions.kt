package com.lagecompany.infinity.utils

import com.badlogic.gdx.math.Vector3

fun <E> MutableList<E>.addAndReturn(value: E): E {
    this.add(value)
    return value
}