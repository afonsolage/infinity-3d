package com.lagecompany.infinity.utils

import com.lagecompany.infinity.math.Vector3I

enum class Side {
    FRONT,
    RIGHT,
    BACK,
    LEFT,
    UP,
    DOWN,

    ;

    fun toDirection(): Vector3I {
        return allDirections[ordinal]
    }

    companion object {
        val allSides = Side.values()
        val allDirections = arrayOf(
                Vector3I(0, 0, 1), //FRONT
                Vector3I(1, 0, 0), //RIGHT
                Vector3I(0, 0, -1), //BACK
                Vector3I(-1, 0, 0), //LEFT
                Vector3I(0, 1, 0), //UP
                Vector3I(0, -1, 0) //DOWN
        )
    }
}