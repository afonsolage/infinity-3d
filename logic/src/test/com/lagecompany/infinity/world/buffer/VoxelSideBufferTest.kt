package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import com.lagecompany.infinity.world.Chunk
import com.lagecompany.infinity.world.VoxelType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.print.attribute.standard.Sides

internal class VoxelSideBufferTest {

    private val buffer = VoxelSideBuffer()

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
        Assertions.assertDoesNotThrow { buffer[0] }
        buffer[1].set(Side.UP , true).save()
        Assertions.assertEquals(true, buffer[1][Side.UP])
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
        buffer[0, 1, 0].set(Side.RIGHT, false).save()
        Assertions.assertEquals(false, buffer[0, 1, 0][Side.RIGHT])
    }
}