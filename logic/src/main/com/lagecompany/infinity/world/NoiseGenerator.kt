package com.lagecompany.infinity.world

import com.lagecompany.infinity.math.SimplexNoise

class Layer(val freq: Float, val weight: Float, val elevations: FloatArray = FloatArray(Chunk.SIZE * Chunk.SIZE))

class NoiseGenerator(vararg noiseLayers: Layer) {
    private val layers = arrayOf(*noiseLayers)

    companion object {
        val default = NoiseGenerator(Layer(0.003f, 1.0f), Layer(0.01f, 0.2f), Layer(0.03f, 0.1f))

        fun fromIndex(index: Int): Pair<Int, Int> {
            return Pair(index / Chunk.SIZE, index % Chunk.SIZE)
        }
    }

    fun generate(chunk: Chunk) {
        layers.forEach {
            SimplexNoise.generateSimplexNoise(chunk.x, chunk.z, Chunk.SIZE, Chunk.SIZE, it.freq, it.elevations)
        }
    }

    suspend fun generateSequence(chunk: Chunk) = sequence {
        for (x in chunk.x until chunk.x + Chunk.SIZE) {
            for (z in chunk.z until chunk.z + Chunk.SIZE) {
                yield(layers.sumByDouble { SimplexNoise.generateSimplexNoise(x, z, it.freq).toDouble() }.toFloat())
            }
        }
    }

    operator fun get(index: Int): Float {
        return layers.sumByDouble { (it.elevations[index] * it.weight).toDouble() }.toFloat()
    }
}