package com.lagecompany.infinity.world

import com.badlogic.gdx.utils.Disposable

class Chunk(val index: Int) : Disposable {
    val types = ChunkBuffer()

    var x: Int = 0
    var y: Int = 0
    var z: Int = 0

    val isEmpty get() = types.isEmpty

    override fun dispose() {
        types.free()
    }
}