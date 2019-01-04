package com.lagecompany.infinity.world

import com.lagecompany.infinity.math.SimplexNoise

class Layer(val freq: Float, val weight: Float, val elevations: Array<FloatArray> = Array(ChunkBuffer.SIZE) { FloatArray(ChunkBuffer.SIZE) })

class NoiseGenerator(vararg noiseLayers: Layer) {
    private val layers = arrayOf(*noiseLayers)

    companion object {
        val default = NoiseGenerator(Layer(0.003f, 1.0f), Layer(0.01f, 0.2f), Layer(0.03f, 0.1f))
    }

    fun generate(chunk: Chunk) {
        layers.forEach {
            SimplexNoise.generateSimplexNoise(chunk.x, chunk.y, ChunkBuffer.SIZE, ChunkBuffer.SIZE, it.freq, it.elevations)
        }
    }

    operator fun get(x: Int, y: Int): Float {
        return layers.sumByDouble { (it.elevations[x][y] * it.weight).toDouble() }.toFloat()
    }
}