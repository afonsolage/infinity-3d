package com.lagecompany.infinity.world

enum class VoxelType {
    NONE,

    GRASS,
    DIRT,
    ROCK

    ;

    companion object {
        val types = VoxelType.values()
        val metaTypes = arrayOf(NONE)

        fun get(code: Int): VoxelType {
            assert(code in 0 until types.size) { "Failed to getValue VoxelType" }
            return types[code]
        }

        fun isVisible(type: VoxelType?): Boolean {
            return type != null && type != NONE
        }
    }
}