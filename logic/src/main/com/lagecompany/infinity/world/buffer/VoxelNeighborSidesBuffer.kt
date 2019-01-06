package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.utils.Side

data class NeighborhoodSides(var front: WeakVoxSideRef,
                             var right: WeakVoxSideRef,
                             var back: WeakVoxSideRef,
                             var left: WeakVoxSideRef,
                             var up: WeakVoxSideRef,
                             var down: WeakVoxSideRef)

class VoxNeighborSidesRef(buffer: VoxelObjectBuffer<NeighborhoodSides>, index: Int)
: VoxObjectRef<VoxNeighborSidesRef, NeighborhoodSides>(buffer, index) {
    operator fun get(side: Side): WeakVoxSideRef {
        return when (side) {
            Side.FRONT -> value.front
            Side.RIGHT -> value.right
            Side.BACK -> value.back
            Side.LEFT -> value.left
            Side.UP -> value.up
            Side.DOWN -> value.down
        }
    }

    operator fun set(side: Side, neighbor: VoxSideRef?) {
        when (side) {
            Side.FRONT -> value.front = WeakVoxSideRef(neighbor)
            Side.RIGHT -> value.right = WeakVoxSideRef(neighbor)
            Side.BACK -> value.back = WeakVoxSideRef(neighbor)
            Side.LEFT -> value.left = WeakVoxSideRef(neighbor)
            Side.UP -> value.up = WeakVoxSideRef(neighbor)
            Side.DOWN -> value.down = WeakVoxSideRef(neighbor)
        }
    }
}

class VoxelNeighborSidesBuffer : VoxelObjectBuffer<NeighborhoodSides>() {
    operator fun get(index: Int): VoxNeighborSidesRef {
        return VoxNeighborSidesRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxNeighborSidesRef {
        return VoxNeighborSidesRef(this, toIndex(x, y, z))
    }
}