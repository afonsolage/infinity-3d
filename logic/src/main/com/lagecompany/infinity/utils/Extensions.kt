package com.lagecompany.infinity.utils

fun <E> MutableList<E>.addAndReturn(value: E): E {
    this.add(value)
    return value
}