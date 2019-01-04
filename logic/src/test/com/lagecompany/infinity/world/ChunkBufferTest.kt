package com.lagecompany.infinity.world

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ChunkBufferTest {

    private val buffer = ChunkBuffer()

    @BeforeEach
    fun setUp() {
        buffer.alloc()
    }

    @AfterEach
    fun tearDown() {
        buffer.free()
    }

    @Test
    fun toIndex() {
        var i = 0
        for (x in 0 until ChunkBuffer.SIZE) {
            for (y in 0 until ChunkBuffer.SIZE) {
                for (z in 0 until ChunkBuffer.SIZE) {
                    Assertions.assertEquals(i++, ChunkBuffer.toIndex(x, y, z))
                }
            }
        }

        Assertions.assertEquals(1, ChunkBuffer.toIndex(0, 0, 1))
        Assertions.assertEquals(0, ChunkBuffer.toIndex(0, 0, 0))
        Assertions.assertEquals(16, ChunkBuffer.toIndex(0, 1, 0))
        Assertions.assertEquals(256, ChunkBuffer.toIndex(1, 0, 0))
        Assertions.assertEquals(257, ChunkBuffer.toIndex(1, 0, 1))
    }


    @Test
    fun alloc() {
        buffer.free()
        Assertions.assertThrows(AssertionError::class.java) { buffer[0] }
        buffer.alloc()
        Assertions.assertDoesNotThrow { buffer[0] }
    }

    @Test
    fun free() {
        Assertions.assertDoesNotThrow { buffer[0] }
        buffer.free()
        Assertions.assertThrows(AssertionError::class.java) { buffer[0] }
    }

    @Test
    fun get() {
        Assertions.assertDoesNotThrow { buffer[0] }
        buffer[1].set(VoxelType.Dirt).save()
        Assertions.assertEquals(VoxelType.Dirt, buffer[1].get())
    }

    @Test
    fun get1() {
        Assertions.assertThrows(AssertionError::class.java) { buffer[-1, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, -1, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, -1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[ChunkBuffer.SIZE, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, ChunkBuffer.SIZE, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, ChunkBuffer.SIZE] }

        Assertions.assertDoesNotThrow { buffer[0, 1, 0] }
        buffer[0, 1, 0].set(VoxelType.Dirt).save()
        Assertions.assertEquals(VoxelType.Dirt, buffer[0, 1, 0].get())
    }
}