package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side

data class NeighborhoodType(var front: WeakVoxTypeRef = WeakVoxTypeRef(null, 0),
                            var right: WeakVoxTypeRef = WeakVoxTypeRef(null, 0),
                            var back: WeakVoxTypeRef = WeakVoxTypeRef(null, 0),
                            var left: WeakVoxTypeRef = WeakVoxTypeRef(null, 0),
                            var up: WeakVoxTypeRef = WeakVoxTypeRef(null, 0),
                            var down: WeakVoxTypeRef = WeakVoxTypeRef(null, 0))

class VoxNeighborTypeRef(buffer: VoxelObjectBuffer<NeighborhoodType>, index: Int)
    : VoxObjectRef<VoxNeighborTypeRef, NeighborhoodType>(buffer, index) {
    operator fun get(side: Side): WeakVoxTypeRef {
        return when (side) {
            Side.FRONT -> value.front
            Side.RIGHT -> value.right
            Side.BACK -> value.back
            Side.LEFT -> value.left
            Side.UP -> value.up
            Side.DOWN -> value.down
        }
    }

    operator fun set(side: Side, neighbor: VoxTypeRef?): VoxNeighborTypeRef {
        when (side) {
            Side.FRONT -> value.front = WeakVoxTypeRef(neighbor)
            Side.RIGHT -> value.right = WeakVoxTypeRef(neighbor)
            Side.BACK -> value.back = WeakVoxTypeRef(neighbor)
            Side.LEFT -> value.left = WeakVoxTypeRef(neighbor)
            Side.UP -> value.up = WeakVoxTypeRef(neighbor)
            Side.DOWN -> value.down = WeakVoxTypeRef(neighbor)
        }

        return this
    }
}

class VoxelNeighborTypeBuffer : VoxelObjectBuffer<NeighborhoodType>() {
    override fun instanciateObject(): NeighborhoodType {
        return NeighborhoodType()
    }

    operator fun get(index: Int): VoxNeighborTypeRef {
        return VoxNeighborTypeRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxNeighborTypeRef {
        return VoxNeighborTypeRef(this, toIndex(x, y, z))
    }
}