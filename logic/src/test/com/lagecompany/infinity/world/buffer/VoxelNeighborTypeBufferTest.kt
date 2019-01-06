package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import com.lagecompany.infinity.world.Chunk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxelNeighborTypeBufferTest {

    private val buffer = VoxelNeighborTypeBuffer()

    @BeforeEach
    fun setUp() {
        buffer.alloc()
    }

    @AfterEach
    fun tearDown() {
        buffer.free()
    }

    @Test
    fun get() {
        val byteBuffer = VoxelByteBuffer()
        byteBuffer.alloc()
        val ref = VoxTypeRef(byteBuffer, 15)

        Assertions.assertDoesNotThrow { buffer[0] }
        buffer[1][Side.UP] = ref
        Assertions.assertEquals(ref, buffer[1][Side.UP].get())

        buffer[1][Side.RIGHT] = null
        Assertions.assertEquals(null, buffer[1][Side.RIGHT].get())
    }

    @Test
    fun get1() {
        Assertions.assertThrows(AssertionError::class.java) { buffer[-1, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, -1, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, -1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.SIZE, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, Chunk.SIZE, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, Chunk.SIZE] }

        Assertions.assertDoesNotThrow { buffer[0, 1, 0] }
    }
}