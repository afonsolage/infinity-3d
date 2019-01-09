package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.world.Chunk
import com.lagecompany.infinity.world.VoxelType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VoxTypeRefTest {

    private val buffer = VoxelTypeBuffer()

    @BeforeEach
    fun setUp() {
        buffer.alloc()
    }

    @AfterEach
    fun tearDown() {
        buffer.free()
    }

    @Test
    fun save() {
        val ref = buffer[0]

        val old = ref.get()
        val new = ref.set(VoxelType.DIRT).save().get()

        Assertions.assertNotEquals(old, new)
    }

    @Test
    fun refresh() {
        val ref = buffer[0]

        var old = ref.get()
        var new = ref.set(VoxelType.DIRT).refresh().get()

        Assertions.assertEquals(old, new)
    }

    @Test
    fun clear() {
        val ref = buffer[0]

        var old = ref.set(VoxelType.ROCK).get()
        var new = ref.clear().get()

        Assertions.assertNotEquals(old, new)
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

        buffer[0, 0, 10].set(VoxelType.GRASS).save()
        Assertions.assertEquals(buffer[10].get(), VoxelType.GRASS)

        buffer[0, 0, Chunk.SIZE - 1].set(VoxelType.ROCK).save()
        Assertions.assertEquals(buffer[Chunk.SIZE - 1].get(), VoxelType.ROCK)

        buffer[0, 1, 0].set(VoxelType.DIRT).save()
        Assertions.assertEquals(buffer[Chunk.SIZE].get(), VoxelType.DIRT)

        buffer[1, 0, 0].set(VoxelType.GRASS).save()
        Assertions.assertEquals(buffer[Chunk.SIZE * Chunk.SIZE].get(), VoxelType.GRASS)
    }

    @Test
    fun set() {
        val ref = buffer[0]

        var old = ref.get()
        Assertions.assertNotEquals(old, ref.set(VoxelType.GRASS).get())
    }
}