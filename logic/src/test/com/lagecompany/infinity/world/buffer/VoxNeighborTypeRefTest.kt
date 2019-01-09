package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import com.lagecompany.infinity.world.Chunk
import com.lagecompany.infinity.world.VoxelType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxNeighborTypeRefTest {

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
        val buf = VoxelTypeBuffer()
        buf.alloc()

        val ref = buf[0, 0, 0]
        ref.set(VoxelType.GRASS).save()

        val ref2 = buf[0, 0, 1]
        ref2.set(VoxelType.DIRT).save()

        val ref3 = buf[0, 0, 2]
        ref3.set(VoxelType.ROCK).save()

        val ref4 = buf[0, 0, 3]
        ref4.set(VoxelType.NONE).save()

        val ref5 = buf[0, 0, 4]
        ref5.set(VoxelType.GRASS).save()

        val ref6 = buf[0, 0, 5]
        ref6.set(VoxelType.DIRT).save()

        Assertions.assertThrows(AssertionError::class.java) { buffer[-1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.BUFFER_SIZE] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[-1, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[Chunk.SIZE, 0, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, -1, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, Chunk.SIZE, 0] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, -1] }
        Assertions.assertThrows(AssertionError::class.java) { buffer[0, 0, Chunk.SIZE] }

        buffer[0, 0, 10].set(Side.LEFT, ref).save()
        Assertions.assertEquals(VoxelType.GRASS, buffer[0, 0, 10][Side.LEFT].get()?.get())

        buffer[0, 0, Chunk.SIZE - 1].set(Side.LEFT, null).save()
        Assertions.assertEquals(null, buffer[Chunk.SIZE - 1][Side.LEFT].get()?.get())

        buffer[0, 1, 0]
                .set(Side.FRONT, ref)
                .set(Side.RIGHT, ref2)
                .set(Side.BACK, ref3)
                .set(Side.LEFT, ref4)
                .set(Side.UP, ref5)
                .set(Side.DOWN, ref6)
                .save()

        Assertions.assertEquals(VoxelType.GRASS, buffer[Chunk.SIZE][Side.FRONT].get()?.get())
        Assertions.assertEquals(VoxelType.DIRT, buffer[Chunk.SIZE][Side.RIGHT].get()?.get())
        Assertions.assertEquals(VoxelType.ROCK, buffer[Chunk.SIZE][Side.BACK].get()?.get())
        Assertions.assertEquals(VoxelType.NONE, buffer[Chunk.SIZE][Side.LEFT].get()?.get())
        Assertions.assertEquals(VoxelType.GRASS, buffer[Chunk.SIZE][Side.UP].get()?.get())
        Assertions.assertEquals(VoxelType.DIRT, buffer[Chunk.SIZE][Side.DOWN].get()?.get())
    }
}