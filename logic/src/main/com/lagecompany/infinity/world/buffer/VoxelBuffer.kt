package com.lagecompany.infinity.world.buffer

import com.lagecompany.infinity.world.Chunk

open class VoxRef<T>(protected val buffer: VoxelBuffer, protected val index: Int) {
    protected var value: Byte = buffer.getValue(index)

    fun save(): T {
        buffer.setValue(index, value)
        return this as T
    }

    fun refresh(): T {
        value = buffer.getValue(index)
        return this as T
    }

    fun clear(): T {
        value = 0
        return this as T
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is VoxRef<*>) return false

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

open class VoxelBuffer {
    companion object {
        val emptyBuffer = byteArrayOf()

        fun toIndex(x: Int, y: Int, z: Int): Int {
            assert(x in 0 until Chunk.SIZE) { "Failed to setValue: Invalid X coordinates" }
            assert(y in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Y coordinates" }
            assert(z in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Z coordinates" }

            return (x shl Chunk.SIZE_SHIFT_X) + (y shl Chunk.SIZE_SHIFT_Y) + z
        }
    }

    private var buffer = emptyBuffer

    val isEmpty get () = buffer.isEmpty()

    fun alloc() {
        assert(buffer === emptyBuffer)
        buffer = ByteArray(Chunk.BUFFER_SIZE)
    }

    fun free() {
        buffer = emptyBuffer
    }

    internal fun getValue(index: Int): Byte {
        assert(index in 0 until Chunk.BUFFER_SIZE) { "Failed to getValue: Invalid index" }
        assert(buffer.isNotEmpty()) { "Failed to getValue:  Buffer isn't allocated" }

        return buffer[index]
    }

    internal fun getValue(x: Int, y: Int, z: Int): Byte {
        assert(x in 0 until Chunk.SIZE) { "Failed to getValue: Invalid X coordinates" }
        assert(y in 0 until Chunk.SIZE) { "Failed to getValue: Invalid Y coordinates" }
        assert(z in 0 until Chunk.SIZE) { "Failed to getValue: Invalid Z coordinates" }

        return getValue(toIndex(x, y, z))
    }

    internal fun setValue(index: Int, value: Byte) {
        assert(index in 0 until Chunk.BUFFER_SIZE) { "Failed to setValue: Invalid index" }
        assert(buffer.isNotEmpty()) { "Failed to setValue:  Buffer isn't allocated" }

        buffer[index] = value
    }

    internal fun setValue(x: Int, y: Int, z: Int, value: Byte) {
        assert(x in 0 until Chunk.SIZE) { "Failed to setValue: Invalid X coordinates" }
        assert(y in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Y coordinates" }
        assert(z in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Z coordinates" }

        setValue(toIndex(x, y, z), value)
    }
}