package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.world.Chunk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxelByteBufferTest {

    private val buffer = VoxelByteBuffer()

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
        for (x in 0 until Chunk.SIZE) {
            for (y in 0 until Chunk.SIZE) {
                for (z in 0 until Chunk.SIZE) {
                    Assertions.assertEquals(i++, VoxelByteBuffer.toIndex(x, y, z))
                }
            }
        }

        Assertions.assertEquals(1, VoxelByteBuffer.toIndex(0, 0, 1))
        Assertions.assertEquals(0, VoxelByteBuffer.toIndex(0, 0, 0))
        Assertions.assertEquals(16, VoxelByteBuffer.toIndex(0, 1, 0))
        Assertions.assertEquals(256, VoxelByteBuffer.toIndex(1, 0, 0))
        Assertions.assertEquals(257, VoxelByteBuffer.toIndex(1, 0, 1))
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