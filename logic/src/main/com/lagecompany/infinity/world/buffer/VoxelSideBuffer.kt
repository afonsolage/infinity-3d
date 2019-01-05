package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class VoxelSideBuffer : VoxelBuffer() {
    operator fun get(index: Int): VoxSideRef {
        return VoxSideRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxSideRef {
        return VoxSideRef(this, toIndex(x, y, z))
    }
}

class VoxSideRef(buffer: VoxelBuffer, index: Int) : VoxRef<VoxSideRef>(buffer, index) {
    operator fun get(side: Side): Boolean {
        val mask = (1 shl side.ordinal).toByte()
        return value and mask == mask
    }

    operator fun set(side: Side, newValue: Boolean): VoxSideRef {
        val mask = (1 shl side.ordinal).toByte()
        //Clear previous value
        value = value and mask.inv()

        if (newValue)
            value = value or mask

        return this
    }
}