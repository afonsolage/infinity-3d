package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.world.VoxelType

class VoxelTypeBuffer: VoxelBuffer() {
    operator fun get(index: Int): VoxTypeRef {
        return VoxTypeRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxTypeRef {
        return VoxTypeRef(this, toIndex(x, y, z))
    }
}

class VoxTypeRef(buffer: VoxelBuffer, index: Int) : VoxRef<VoxTypeRef>(buffer, index) {
    fun get(): VoxelType {
        return VoxelType.get(value.toInt())
    }

    fun set(type: VoxelType): VoxTypeRef {
        value = type.ordinal.toByte()
        return this
    }
}