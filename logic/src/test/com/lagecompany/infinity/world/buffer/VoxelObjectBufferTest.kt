package com.lagecompany.infinity.world.buffer

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxelObjectBufferTest {

    private val buffer = VoxelNeighborSidesBuffer()

    @BeforeEach
    fun setUp() {
        buffer.alloc()
    }

    @AfterEach
    fun tearDown() {
        buffer.free()
    }

    @Test
    fun isEmpty() {
        Assertions.assertFalse(buffer.isEmpty)
        buffer.free()
        Assertions.assertTrue(buffer.isEmpty)
    }

    @Test
    fun alloc() {
        buffer.free()
        Assertions.assertThrows(AssertionError::class.java) { buffer.getValue(0) }
        buffer.alloc()
        Assertions.assertDoesNotThrow { buffer.getValue(0) }
    }

    @Test
    fun free() {
        Assertions.assertDoesNotThrow { buffer.getValue(0) }
        buffer.free()
        Assertions.assertThrows(AssertionError::class.java) { buffer.getValue(0)}
    }
}