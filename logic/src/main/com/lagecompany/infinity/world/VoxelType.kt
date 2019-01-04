package com.lagecompany.infinity.world

enum class VoxelType {
    None,

    Grass,
    Dirt,
    Rock

    ;

    companion object {
        private val types = VoxelType.values()

        fun get(code: Int): VoxelType {
            assert(code in 0 until types.size) { "Failed to getValue VoxelType" }
            return types[code]
        }
    }
}