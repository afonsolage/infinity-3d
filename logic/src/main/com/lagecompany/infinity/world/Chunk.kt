package com.lagecompany.infinity.world

import com.badlogic.gdx.utils.Disposable

class Chunk : Disposable {
    val types = ChunkBuffer()

    var x: Int = 0
    var y: Int = 0

    override fun dispose() {
        types.free()
    }
}