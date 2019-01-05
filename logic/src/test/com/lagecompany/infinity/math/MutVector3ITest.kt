package com.lagecompany.infinity.math

import com.badlogic.gdx.math.MathUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MutVector3ITest {

    fun rnd(): MutVector3I {
        return MutVector3I(MathUtils.random(-100, 100), MathUtils.random(-100, 100), MathUtils.random(-100, 100))
    }

    @Test
    fun set() {
        val v = MutVector3I()
        v.set(1, 2, 3)
        Assertions.assertEquals(v.x, 1)
        Assertions.assertEquals(v.y, 2)
        Assertions.assertEquals(v.z, 3)
    }

    @Test
    fun set1() {
        val v = MutVector3I()
        v.set(Vector3I(1, 2, 3))
        Assertions.assertEquals(v.x, 1)
        Assertions.assertEquals(v.y, 2)
        Assertions.assertEquals(v.z, 3)
    }

    @Test
    fun mul() {
        val v = MutVector3I()

        v.set(1, 1, 1)
        v.mul(Vector3I.Zero)
        Assertions.assertTrue(v.isZero)

        v.set(3, 2, 1)
        v.mul(Vector3I(1, 1, 1))
        Assertions.assertTrue(v.x == 3 && v.y == 2 && v.z == 1)

        v.set(3, 2, 1)
        v.mul(Vector3I.X.mutable().inv())
        Assertions.assertTrue(v.x == -3 && v.y == 0 && v.z == 0)

        v.set(3, 2, 1)
        v.mul(Vector3I.One.mutable().inv())
        Assertions.assertTrue(v.x == -3 && v.y == -2 && v.z == -1)
    }

    @Test
    fun add() {
        val v = MutVector3I()

        v.setZero()
        v.add(0, 0, 0)
        Assertions.assertTrue(v.isZero)

        v.add(0, 6, 0)
        Assertions.assertTrue(v.x == 0 && v.y == 6 && v.z == 0)

        v.add(1, 0, 0)
        Assertions.assertTrue(v.x == 1 && v.y == 6 && v.z == 0)

        v.add(1, 4, -1)
        Assertions.assertTrue(v.x == 2 && v.y == 10 && v.z == -1)
    }

    @Test
    fun add1() {
        val v = MutVector3I()

        v.setZero()
        v.add(Vector3I(0, 0, 0))
        Assertions.assertTrue(v.isZero)

        v.add(Vector3I(0, 6, 0))
        Assertions.assertTrue(v.x == 0 && v.y == 6 && v.z == 0)

        v.add(Vector3I(1, 0, 0))
        Assertions.assertTrue(v.x == 1 && v.y == 6 && v.z == 0)

        v.add(Vector3I(1, 4, -1))
        Assertions.assertTrue(v.x == 2 && v.y == 10 && v.z == -1)
    }

    @Test
    fun setToRandomDirection() {
        var equalsCount = 0
        var last = MutVector3I(-101, -101, -101)
        repeat(10) {
            val v = rnd().setToRandomDirection()

            Assertions.assertFalse(v.isZero) {"$v is zero (${v.isZero})"}

            if (last == v)
                equalsCount++
            last = v
        }

        Assertions.assertTrue(equalsCount < 5)
    }

    @Test
    fun clamp() {
        repeat(40) {
            val v = rnd()
            v.clamp(10, 20)
            Assertions.assertTrue(v.x in 10..20 && v.y in 10..20 && v.z in 10..20)
        }
    }

    @Test
    fun sub() {
        val v = MutVector3I()

        v.setZero()
        v.sub(0, 0, 0)
        Assertions.assertTrue(v.isZero)

        v.sub(0, 6, 0)
        Assertions.assertTrue(v.x == 0 && v.y == -6 && v.z == 0)

        v.sub(1, 0, 0)
        Assertions.assertTrue(v.x == -1 && v.y == -6 && v.z == 0)

        v.sub(1, 4, -1)
        Assertions.assertTrue(v.x == -2 && v.y == -10 && v.z == 1)
    }

    @Test
    fun sub1() {
        val v = MutVector3I()

        v.setZero()
        v.sub(Vector3I(0, 0, 0))
        Assertions.assertTrue(v.isZero)

        v.sub(Vector3I(0, 6, 0))
        Assertions.assertTrue(v.x == 0 && v.y == -6 && v.z == 0)

        v.sub(Vector3I(1, 0, 0))
        Assertions.assertTrue(v.x == -1 && v.y == -6 && v.z == 0)

        v.sub(Vector3I(1, 4, -1))
        Assertions.assertTrue(v.x == -2 && v.y == -10 && v.z == 1)
    }

    @Test
    fun nor() {
        repeat(10) {
            val rnd = rnd().nor()
            Assertions.assertTrue(rnd.isUnit || rnd.isZero) {"$rnd isn't normalized"}
        }
    }

    @Test
    fun inv() {
        repeat(10) {
            val rnd = rnd()
            Assertions.assertTrue(rnd.add(rnd.cpy().inv()).isZero)
        }
    }

    @Test
    fun setZero() {
        repeat(10) {
            Assertions.assertTrue(rnd().setZero().isZero)
        }
    }


    @Test
    fun setLength2() {
        repeat(10) {
            val v = rnd()
            val r = MathUtils.random(100)
            Assertions.assertEquals(v.setLength2(r), v.toVector3().setLength(r.toFloat()).toVector3I())
        }
    }

    @Test
    fun scl() {
        Assertions.assertEquals(MutVector3I(0, 1, 0), MutVector3I(0, 1, 0).scl(1))
        Assertions.assertEquals(MutVector3I(22, 22, 22), MutVector3I(1, 1, 1).scl(22))
        Assertions.assertEquals(MutVector3I(-10, -10, -10), MutVector3I(2, 2, 2).scl(-5))
        Assertions.assertEquals(Vector3I.Zero, MutVector3I(2, 2, 2).scl(0))
    }

    @Test
    fun scl1() {
        Assertions.assertEquals(MutVector3I(22, 22, 22), MutVector3I(44, 44, 44).scl(0.5f))
        Assertions.assertEquals(MutVector3I(1, 1, 1), MutVector3I(100, 100, 100).scl(0.01f))
        Assertions.assertEquals(MutVector3I(4, 4, 4), MutVector3I(3, 3, 3).scl(1.5f))
        Assertions.assertEquals(Vector3I.Zero, MutVector3I(3, 3, 3).scl(0f))
    }


}