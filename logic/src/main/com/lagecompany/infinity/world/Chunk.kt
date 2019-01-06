package com.lagecompany.infinity.world

import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.world.buffer.VoxelSideBuffer
import com.lagecompany.infinity.world.buffer.VoxelTypeBuffer

class Chunk(val index: Int) : Disposable {

    companion object {
        const val SIZE = 16
        const val BUFFER_SIZE = SIZE * SIZE * SIZE
        const val SIZE_SHIFT_Y = 4
        const val SIZE_SHIFT_X = 8
    }

    val types = VoxelTypeBuffer()
    val visible = VoxelSideBuffer()

    var x: Int = 0
    var y: Int = 0
    var z: Int = 0

    val isEmpty get() = types.isEmpty

    override fun dispose() {
        types.free()
        visible.free()
    }
}