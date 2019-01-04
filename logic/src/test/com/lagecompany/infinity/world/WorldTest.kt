package com.lagecompany.infinity.world

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
    fun generateChunk() {
        val world = World()
        world.generateAllChunks()
    }
}