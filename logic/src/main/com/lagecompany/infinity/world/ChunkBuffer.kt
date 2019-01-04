package com.lagecompany.infinity.world

open class VoxTypeRef(private val buffer: ChunkBuffer, private val index: Int) {

    private var value: Byte = buffer.getValue(index)

    fun save(): VoxTypeRef {
        buffer.setValue(index, value)
        return this
    }

    fun refresh(): VoxTypeRef {
        value = buffer.getValue(index)
        return this
    }

    fun clear(): VoxTypeRef {
        value = 0
        return this
    }

    fun get(): VoxelType {
        return VoxelType.get(value.toInt())
    }

    fun set(type: VoxelType): VoxTypeRef {
        value = type.ordinal.toByte()
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VoxTypeRef

        if (buffer != other.buffer) return false
        if (index != other.index) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buffer.hashCode()
        result = 31 * result + index
        return result
    }
}

class ChunkBuffer {
    companion object {

        const val SIZE = 16
        const val BUFFER_SIZE = SIZE * SIZE * SIZE
        private const val SIZE_SHIFT_Y = 4
        private const val SIZE_SHIFT_X = 8

        val emptyBuffer = byteArrayOf()

        fun toIndex(x: Int, y: Int, z: Int): Int {
            assert(x in 0 until SIZE) { "Failed to setValue: Invalid X coordinates" }
            assert(y in 0 until SIZE) { "Failed to setValue: Invalid Y coordinates" }
            assert(z in 0 until SIZE) { "Failed to setValue: Invalid Z coordinates" }

            return (x shl SIZE_SHIFT_X) + (y shl SIZE_SHIFT_Y) + z
        }

    }

    private var buffer = emptyBuffer

    fun alloc() {
        assert(buffer === emptyBuffer)
        buffer = ByteArray(BUFFER_SIZE)
    }

    fun free() {
        buffer = emptyBuffer
    }

    internal fun getValue(index: Int): Byte {
        assert(index in 0 until BUFFER_SIZE) { "Failed to getValue: Invalid index" }
        assert(buffer.isNotEmpty()) { "Failed to getValue:  Buffer isn't allocated" }

        return buffer[index]
    }

    internal fun getValue(x: Int, y: Int, z: Int): Byte {
        assert(x in 0 until SIZE) { "Failed to getValue: Invalid X coordinates" }
        assert(y in 0 until SIZE) { "Failed to getValue: Invalid Y coordinates" }
        assert(z in 0 until SIZE) { "Failed to getValue: Invalid Z coordinates" }

        return getValue(toIndex(x, y, z))
    }

    internal fun setValue(index: Int, value: Byte) {
        assert(index in 0 until BUFFER_SIZE) { "Failed to setValue: Invalid index" }
        assert(buffer.isNotEmpty()) { "Failed to setValue:  Buffer isn't allocated" }

        buffer[index] = value
    }

    internal fun setValue(x: Int, y: Int, z: Int, value: Byte) {
        assert(x in 0 until SIZE) { "Failed to setValue: Invalid X coordinates" }
        assert(y in 0 until SIZE) { "Failed to setValue: Invalid Y coordinates" }
        assert(z in 0 until SIZE) { "Failed to setValue: Invalid Z coordinates" }

        setValue(toIndex(x, y, z), value)
    }

    operator fun get(index: Int): VoxTypeRef {
        return VoxTypeRef(this, index)
    }

    operator fun get(x: Int, y: Int, z: Int): VoxTypeRef {
        return VoxTypeRef(this, toIndex(x, y, z))
    }
}