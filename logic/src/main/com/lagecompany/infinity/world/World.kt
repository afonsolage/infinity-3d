package com.lagecompany.infinity.world

class World {
    private val chunks = Array(SIZE) { Chunk() }
    var x: Int = 0
    var y: Int = 0


    companion object {
        const val WIDTH = 4
        const val HEIGHT = 1
        const val DEPTH = 4

        const val SIZE = WIDTH * HEIGHT * DEPTH
        const val X_SIZE = WIDTH
        const val Y_SIZE = HEIGHT
        const val Z_SIZE = DEPTH

        const val Z_UNIT = 1
        const val Y_UNIT = Z_SIZE * Z_UNIT
        const val X_UNIT = Y_SIZE * Y_UNIT

        fun toIndex(x: Int, y: Int, z: Int): Int {
            return x * X_UNIT + y * Y_UNIT + z * Z_UNIT
        }
    }

    fun generateAllChunks() {
        chunks.forEach(this::generateChunk)
    }

    private fun generateChunk(chunk: Chunk) {
        chunk.types.alloc()

        val generator = NoiseGenerator.default
        generator.generate(chunk)

        for(x in 0 until ChunkBuffer.SIZE) {
            for(z in 0 until ChunkBuffer.SIZE) {
                val height = generator.get(x, z).toInt()

                if (height >= ChunkBuffer.SIZE) continue

                for(y in 0..height) {
                    chunk.types[x, y, z].set(VoxelType.Grass).save()
                }
            }
        }
    }
}