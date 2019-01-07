package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import com.lagecompany.infinity.world.Chunk
import com.lagecompany.infinity.world.VoxelType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxSideRefTest {

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
        Assertions.assertThrows(AssertionError::class.java) { buffer[-1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.BUFFER_SIZE] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[-1, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.SIZE, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, -1, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, Chunk.SIZE, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, -1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, Chunk.SIZE] }

        buffer[0, 0, 10].set(Side.LEFT, true).save()
        Assertions.assertEquals(buffer[10][Side.LEFT], true)

        buffer[0, 0, Chunk.SIZE - 1].set(Side.BACK, false).save()
        Assertions.assertEquals(buffer[Chunk.SIZE - 1][Side.BACK], false)

        buffer[0, 1, 0].set(Side.DOWN, true).save()
        Assertions.assertEquals(buffer[Chunk.SIZE][Side.DOWN], true)

        buffer[0, 1, 0]
                .set(Side.FRONT, true)
                .set(Side.RIGHT, false)
                .set(Side.BACK, true)
                .set(Side.LEFT, false)
                .set(Side.UP, true)
                .set(Side.DOWN, false)
                .save()

        Assertions.assertEquals(buffer[Chunk.SIZE][Side.FRONT], true)
        Assertions.assertEquals(buffer[Chunk.SIZE][Side.RIGHT], false)
        Assertions.assertEquals(buffer[Chunk.SIZE][Side.BACK], true)
        Assertions.assertEquals(buffer[Chunk.SIZE][Side.LEFT], false)
        Assertions.assertEquals(buffer[Chunk.SIZE][Side.UP], true)
        Assertions.assertEquals(buffer[Chunk.SIZE][Side.DOWN], false)

        buffer[1, 0, 0].set(Side.FRONT, true).save()
        Assertions.assertEquals(buffer[Chunk.SIZE * Chunk.SIZE][Side.FRONT], true)
    }
}