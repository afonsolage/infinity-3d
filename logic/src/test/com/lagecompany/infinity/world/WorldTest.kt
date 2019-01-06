package com.lagecompany.infinity.world

import com.lagecompany.infinity.math.Vector3I
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class WorldTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun toIndex() {
        var i = 0
        for (x in 0 until World.X_SIZE) {
            for (y in 0 until World.Y_SIZE) {
                for (z in 0 until World.Z_SIZE) {
                    Assertions.assertEquals(i++, World.toIndex(x, y, z))
                }
            }
        }

        Assertions.assertEquals(World.Z_UNIT, World.toIndex(0, 0, 1))
        Assertions.assertEquals(0, World.toIndex(0, 0, 0))
        Assertions.assertEquals(World.Y_UNIT, World.toIndex(0, 1, 0))
        Assertions.assertEquals(World.X_UNIT, World.toIndex(1, 0, 0))
        Assertions.assertEquals(World.X_UNIT + World.Z_UNIT, World.toIndex(1, 0, 1))
    }

    @Test
    fun fromIndex() {
        var i = 0
        for (x in 0 until World.X_SIZE) {
            for (y in 0 until World.Y_SIZE) {
                for (z in 0 until World.Z_SIZE) {
                    Assertions.assertEquals(Vector3I(x, y, z), World.fromIndex(i++))
                }
            }
        }

        if (World.WIDTH > 1 && World.HEIGHT > 1 && World.DEPTH > 1) {
            Assertions.assertEquals(Vector3I(0, 0, 1), World.fromIndex(World.Z_UNIT))
            Assertions.assertEquals(Vector3I.Zero, World.fromIndex(0))
            Assertions.assertEquals(Vector3I(0, 1, 0), World.fromIndex(World.Y_UNIT))
            Assertions.assertEquals(Vector3I(1, 0, 0), World.fromIndex(World.X_UNIT))
            Assertions.assertEquals(Vector3I(1, 0, 1), World.fromIndex(World.X_UNIT + World.Z_UNIT))
        }
    }

    @Test
    fun generateChunk() {
        val world = World()
        runBlocking {
            world.generateAllChunks()
        }
    }
}