package com.lagecompany.infinity.world

import com.lagecompany.infinity.math.SimplexNoise

class Layer(val freq: Float, val weight: Float, val elevations: FloatArray = FloatArray(Chunk.SIZE * Chunk.SIZE))

class NoiseGenerator(vararg noiseLayers: Layer) {
    private val layers = arrayOf(*noiseLayers)

    companion object {
        val default = NoiseGenerator(Layer(0.015f, .9f))

        fun fromIndex(index: Int): Pair<Int, Int> {
            return Pair(index / Chunk.SIZE, index % Chunk.SIZE)
        }
    }

    fun generate(chunk: Chunk) {
        layers.forEach {
            SimplexNoise.generateSimplexNoise(chunk.x, chunk.z, Chunk.SIZE, Chunk.SIZE, it.freq, it.elevations)
        }
    }

    operator fun get(index: Int): Float {
        return layers.sumByDouble { (it.elevations[index] * it.weight).toDouble() }.toFloat()
    }
}