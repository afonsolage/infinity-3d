package com.lagecompany.infinity.world

import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.math.Vector3I

class World : Disposable {
    private val chunks = Array(SIZE) { Chunk() }
    var x: Int = 0
    var y: Int = 0


    companion object {
        const val WIDTH = 2
        const val HEIGHT = 2
        const val DEPTH = 2

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

    fun generateAllChunks() {
        chunks.forEachIndexed(this::generateChunk)
    }

    private fun generateChunk(index: Int, chunk: Chunk) {
        setChunkPosition(index, chunk)

        chunk.types.alloc()

        val generator = NoiseGenerator.default
        generator.generate(chunk)

        for (x in 0 until ChunkBuffer.SIZE) {
            for (z in 0 until ChunkBuffer.SIZE) {
                val height = generator.get(x, z).toInt()

                if (height >= ChunkBuffer.SIZE) continue

                for (y in 0..height) {
                    chunk.types[x, y, z].set(VoxelType.Grass).save()
                }
            }
        }
    }

    private fun setChunkPosition(index: Int, chunk: Chunk) {
        val vec = fromIndex(index)
        chunk.x = vec.x * ChunkBuffer.SIZE
        chunk.y = vec.y * ChunkBuffer.SIZE
        chunk.z = vec.z * ChunkBuffer.SIZE
    }

    override fun dispose() {
        chunks.forEach { it.dispose() }
    }
}