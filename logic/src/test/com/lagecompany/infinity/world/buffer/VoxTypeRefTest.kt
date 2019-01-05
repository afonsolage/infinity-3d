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
        val new = ref.set(VoxelType.Dirt).save().get()

        Assertions.assertNotEquals(old, new)
    }

    @Test
    fun refresh() {
        val ref = buffer[0]

        var old = ref.get()
        var new = ref.set(VoxelType.Dirt).refresh().get()

        Assertions.assertEquals(old, new)
    }

    @Test
    fun clear() {
        val ref = buffer[0]

        var old = ref.set(VoxelType.Rock).get()
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

        buffer[0, 0, 10].set(VoxelType.Grass).save()
        Assertions.assertEquals(buffer[10].get(), VoxelType.Grass)

        buffer[0, 0, Chunk.SIZE - 1].set(VoxelType.Rock).save()
        Assertions.assertEquals(buffer[Chunk.SIZE - 1].get(), VoxelType.Rock)

        buffer[0, 1, 0].set(VoxelType.Dirt).save()
        Assertions.assertEquals(buffer[Chunk.SIZE].get(), VoxelType.Dirt)

        buffer[1, 0, 0].set(VoxelType.Grass).save()
        Assertions.assertEquals(buffer[Chunk.SIZE * Chunk.SIZE].get(), VoxelType.Grass)
    }

    @Test
    fun set() {
        val ref = buffer[0]

        var old = ref.get()
        Assertions.assertNotEquals(old, ref.set(VoxelType.Grass).get())
    }
}