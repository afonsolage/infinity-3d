package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.world.VoxelType

class VoxelTypeBuffer : VoxelByteBuffer() {
    operator fun get(index: Int): VoxTypeRef {
        return VoxTypeRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxTypeRef {
        return VoxTypeRef(this, toIndex(x, y, z))
    }
}

class VoxTypeRef(buffer: VoxelByteBuffer, index: Int) : VoxByteRef<VoxTypeRef>(buffer, index) {
    fun get(): VoxelType {
        return VoxelType.get(value.toInt())
    }

    fun set(type: VoxelType): VoxTypeRef {
        value = type.ordinal.toByte()
        return this
    }
}

class WeakVoxTypeRef(buffer: VoxelByteBuffer?, index: Int) : WeakVoxByteRef<VoxTypeRef>(buffer, index) {
    constructor(ref: VoxTypeRef?) : this(ref?.buffer, ref?.index ?: 0)

    override fun get(): VoxTypeRef? {
        return VoxTypeRef(buffer.get() ?: return null, index)
    }
}