package com.lagecompany.infinity.game.renderer

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

object Cube {
    private val floatCache = ThreadLocal.withInitial { FloatArray(3)}

    val frontIdxs = shortArrayOf(0, 1, 2, 2, 3, 0)
    val rightIdxs = shortArrayOf(1, 5, 6, 6, 2, 1)
    val backIdxs = shortArrayOf(5, 4, 7, 7, 6, 5)
    val leftIdxs = shortArrayOf(4, 0, 3, 3, 7, 0)
    val upIdxs = shortArrayOf(3, 2, 6, 6, 7, 3)
    val downIdxs = shortArrayOf(4, 5, 1, 1, 0, 4)

    val allIdxs = shortArrayOf(*frontIdxs, *rightIdxs, *backIdxs, *leftIdxs, *upIdxs, *downIdxs)

    val frontNormal = floatArrayOf(0f, 0f, 1f)
    val rightNormal = floatArrayOf(1f, 0f, 0f)
    val backNormal = floatArrayOf(0f, 0f, -1f)
    val leftNormal = floatArrayOf(-1f, 0f, 0f)
    val upNormal = floatArrayOf(0f, 1f, 0f)
    val downNormal = floatArrayOf(0f, -1f, 0f)

    val normalArrays = arrayOf(frontNormal, rightNormal, backNormal, leftNormal, upNormal, downNormal)
//
//    fun v0(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 0f
//        cache[1] = y + 0f
//        cache[2] = z + 1f
//        return cache
//    }
//
//    fun v1(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 1f
//        cache[1] = y + 0f
//        cache[2] = z + 1f
//        return cache
//    }
//
//    fun v2(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 1f
//        cache[1] = y + 1f
//        cache[2] = z + 1f
//        return cache
//    }
//
//    fun v3(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 0f
//        cache[1] = y + 1f
//        cache[2] = z + 1f
//        return cache
//    }
//
//    fun v4(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 0f
//        cache[1] = y + 0f
//        cache[2] = z + 0f
//        return cache
//    }
//
//    fun v5(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 1f
//        cache[1] = y + 0f
//        cache[2] = z + 0f
//        return cache
//    }
//
//    fun v6(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 1f
//        cache[1] = y + 1f
//        cache[2] = z + 0f
//        return cache
//    }
//
//    fun v7(x: Int, y: Int, z: Int): FloatArray {
//        val cache = floatCache.get()
//        cache[0] = x + 0f
//        cache[1] = y + 1f
//        cache[2] = z + 0f
//        return cache
//    }

    private val v0 = Vector3(0f, 0f, 1f)
    private val v1 = Vector3(1f, 0f, 1f)
    private val v2 = Vector3(1f, 1f, 1f)
    private val v3 = Vector3(0f, 1f, 1f)
    private val v4 = Vector3(0f, 0f, 0f)
    private val v5 = Vector3(1f, 0f, 0f)
    private val v6 = Vector3(1f, 1f, 0f)
    private val v7 = Vector3(0f, 1f, 0f)

    private fun makeArray(x: Int, y: Int, z: Int, v1: Vector3, v2: Vector3, v3: Vector3, v4: Vector3): FloatArray {
        return floatArrayOf(
                v1.x + x, v1.y + y, v1.z + z,
                v2.x + x, v2.y + y, v2.z + z,
                v3.x + x, v3.y + y, v3.z + z,
                v4.x + x, v4.y + y, v4.z + z
        )
    }

    fun frontVertices(x: Int, y: Int, z: Int): FloatArray {
        return makeArray(x, y, z, v0, v1, v2, v3)
    }

    fun rightVertices(x: Int, y: Int, z: Int): FloatArray {
        return makeArray(x, y, z, v1, v5, v6, v2)
    }

    fun backVertices(x: Int, y: Int, z: Int): FloatArray {
        return makeArray(x, y, z, v5, v4, v7, v6)
    }

    fun leftVertices(x: Int, y: Int, z: Int): FloatArray {
        return makeArray(x, y, z, v4, v0, v3, v7)
    }

    fun upVertices(x: Int, y: Int, z: Int): FloatArray {
        return makeArray(x, y, z, v3, v2, v6, v7)
    }

    fun downVertices(x: Int, y: Int, z: Int): FloatArray {
        return makeArray(x, y, z, v4, v5, v1, v0)
    }
}