package com.lagecompany.infinity.world

import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.math.Vector3I
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class World : Disposable {
    private val chunks = Array(SIZE) { Chunk(it) }

    companion object {
        const val WIDTH = 5
        const val HEIGHT = 5
        const val DEPTH = 5

        const val SIZE = WIDTH * HEIGHT * DEPTH
        const val X_SIZE = WIDTH
        const val Y_SIZE = HEIGHT
        const val Z_SIZE = DEPTH

        const val Z_UNIT = 1
        const val Y_UNIT = Z_SIZE * Z_UNIT
        const val X_UNIT = Y_SIZE * Y_UNIT

        fun toIndex(vec: Vector3I): Int {
            return toIndex(vec.x, vec.y, vec.z)
        }

        fun toIndex(x: Int, y: Int, z: Int): Int {
            return x * X_UNIT + y * Y_UNIT + z * Z_UNIT
        }

        fun fromIndex(index: Int): Vector3I {
            return Vector3I(index / X_UNIT, (index % X_UNIT) / Y_UNIT, index % Y_UNIT)
        }
    }

    suspend fun generateAllChunks() = coroutineScope {
        chunks.forEachIndexed { idx, chunk ->
            launch(Dispatchers.Default) {
                generateChunk(idx, chunk)
            }
        }
    }

    suspend fun generateAllChunksSequence() = coroutineScope {
        chunks.forEachIndexed { idx, chunk ->
            launch(Dispatchers.Default) {
                generateChunkSequence(idx, chunk)
            }
        }
    }

    fun clearAllChunks() {
        chunks.forEach(Chunk::dispose)
    }

    private fun generateChunk(index: Int, chunk: Chunk) {
        setChunkPosition(index, chunk)

        chunk.types.alloc()


        val generator = NoiseGenerator.default
        generator.generate(chunk)

        for (i in 0 until Chunk.SIZE * Chunk.SIZE) {
            val height = generator[i]

            if (height >= Chunk.SIZE) continue

            val (x, z) = NoiseGenerator.fromIndex(i)
            for (y in 0..height.toInt()) {
                chunk.types[x, y, z].set(VoxelType.Grass).save()
            }
        }
    }


    private suspend fun generateChunkSequence(index: Int, chunk: Chunk) {
        setChunkPosition(index, chunk)

        chunk.types.alloc()
        val generator = NoiseGenerator.default.generateSequence(chunk)

        var i = 0
        for (height in generator) {
            if (height < Chunk.SIZE) {
                val (x, z) = NoiseGenerator.fromIndex(i++)
                for (y in 0..height.toInt()) {
                    chunk.types[x, y, z].set(VoxelType.Grass).save()
                }
            }
        }
    }

    private fun setChunkPosition(index: Int, chunk: Chunk) {

        val vec = fromIndex(index)
        chunk.x = vec.x * Chunk.SIZE
        chunk.y = vec.y * Chunk.SIZE
        chunk.z = vec.z * Chunk.SIZE
    }

    override fun dispose() {
        chunks.forEach { it.dispose() }
    }

    operator fun get(index: Int): Chunk {
        assert(index in 0 until World.SIZE)
        return chunks[index]
    }

    operator fun get(x: Int, y: Int, z: Int): Chunk {
        assert(x in 0 until World.X_SIZE)
        assert(y in 0 until World.Y_SIZE)
        assert(z in 0 until World.Z_SIZE)
        return chunks[toIndex(x, y, z)]
    }
}