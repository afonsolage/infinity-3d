package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class VoxelSideBuffer : VoxelByteBuffer() {
    operator fun get(index: Int): VoxSideRef {
        return VoxSideRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxSideRef {
        return VoxSideRef(this, toIndex(x, y, z))
    }
}

class VoxSideRef(buffer: VoxelByteBuffer, index: Int) : VoxByteRef<VoxSideRef>(buffer, index) {
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

    val isVisible get() = value > 0
}

class WeakVoxSideRef(buffer: VoxelByteBuffer?, index: Int) : WeakVoxByteRef<VoxSideRef>(buffer, index) {
    constructor(ref: VoxSideRef?) : this(ref?.buffer, ref?.index ?: 0)

    override fun get(): VoxSideRef? {
        val buffer = buffer.get()
        if (buffer == null)
            return null
        return VoxSideRef(buffer, index)
    }
}