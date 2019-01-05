package com.lagecompany.infinity.renderer

import com.badlogic.gdx.math.Vector3

/*
 *
 *      v7+-------+v6   y
 *       /|      /|	    |
 *    v3+-------+v2	    |
 *      | +-----|-+	    +-- X
 *      |/ v4   |/ v5    \
 *      +-------+	      Z
 *     v0       v1
 */

class Cube(val x: Float, val y: Float, val z: Float) {

    companion object {
        val frontIdxs = shortArrayOf(0, 1, 2, 2, 3, 0)
        val rightIdxs = shortArrayOf(1, 5, 6, 6, 2, 1)
        val backIdxs = shortArrayOf(5, 4, 7, 7, 6, 5)
        val leftIdxs = shortArrayOf(4, 0, 3, 3, 7, 0)
        val upIdxs = shortArrayOf(3, 2, 6, 6, 7, 3)
        val downIdxs = shortArrayOf(4, 5, 1, 1, 0, 4)

        val allIdxs = shortArrayOf(*frontIdxs, *rightIdxs, *backIdxs, *leftIdxs, *upIdxs, *downIdxs)
    }

    val v0 = Vector3(x, y, z + 1f)
    val v1 = Vector3(x + 1f, y, z + 1f)
    val v2 = Vector3(x + 1f, y + 1f, z + 1f)
    val v3 = Vector3(x, y + 0.5f, z + 1f)
    val v4 = Vector3(x, y, z)
    val v5 = Vector3(x + 1f, y, z)
    val v6 = Vector3(x + 1f, y + 1f, z)
    val v7 = Vector3(x, y + 1f, z)
}

fun Vector3.append(arr: FloatArray, index: Int) {
    arr[index] = this.x
    arr[index + 1] = this.y
    arr[index + 2] = this.z
}