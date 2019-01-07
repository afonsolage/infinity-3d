package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import com.lagecompany.infinity.world.Chunk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxNeighborSidesRefTest {

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
    fun get() {
        val buf = VoxelSideBuffer()
        buf.alloc()

        val ref = buf[0, 0, 0]
        ref.set(Side.DOWN, true).save()

        val ref2 = buf[0, 0, 1]
        ref2.set(Side.UP, false).save()

        val ref3 = buf[0, 0, 2]
        ref3.set(Side.LEFT, true).save()

        val ref4 = buf[0, 0, 3]
        ref4.set(Side.BACK, false).save()

        val ref5 = buf[0, 0, 4]
        ref5.set(Side.RIGHT, true).save()

        val ref6 = buf[0, 0, 5]
        ref6.set(Side.FRONT, false).save()

        Assertions.assertThrows(AssertionError::class.java) { buffer[-1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.BUFFER_SIZE] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[-1, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.SIZE, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, -1, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, Chunk.SIZE, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, -1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, Chunk.SIZE] }

        buffer[0, 0, 10].set(Side.LEFT, ref).save()
        Assertions.assertEquals(true, buffer[0, 0, 10][Side.LEFT].get()?.get(Side.DOWN))

        buffer[0, 0, Chunk.SIZE - 1].set(Side.LEFT, null).save()
        Assertions.assertEquals(null, buffer[Chunk.SIZE - 1][Side.LEFT].get()?.get(Side.LEFT))

        buffer[0, 1, 0]
                .set(Side.FRONT, ref)
                .set(Side.RIGHT, ref2)
                .set(Side.BACK, ref3)
                .set(Side.LEFT, ref4)
                .set(Side.UP, ref5)
                .set(Side.DOWN, ref6)
                .save()

        Assertions.assertEquals(true, buffer[Chunk.SIZE][Side.FRONT].get()?.get(Side.DOWN))
        Assertions.assertEquals(false, buffer[Chunk.SIZE][Side.RIGHT].get()?.get(Side.UP))
        Assertions.assertEquals(true, buffer[Chunk.SIZE][Side.BACK].get()?.get(Side.LEFT))
        Assertions.assertEquals(false, buffer[Chunk.SIZE][Side.LEFT].get()?.get(Side.BACK))
        Assertions.assertEquals(true, buffer[Chunk.SIZE][Side.UP].get()?.get(Side.RIGHT))
        Assertions.assertEquals(false, buffer[Chunk.SIZE][Side.DOWN].get()?.get(Side.FRONT))
    }
}