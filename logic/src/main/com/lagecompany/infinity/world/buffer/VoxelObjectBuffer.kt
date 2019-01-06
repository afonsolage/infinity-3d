package com.lagecompany.infinity.world.buffer


import com.lagecompany.infinity.world.Chunk
import java.lang.ref.WeakReference

abstract class WeakVoxObjectRef<T, O : Any>(buffer: VoxelObjectBuffer<O>?, protected val index: Int) {
    protected val buffer = WeakReference(buffer)

    abstract fun get(): T?
}

abstract class VoxObjectRef<T, O : Any>(protected val buffer: VoxelObjectBuffer<O>, protected val index: Int) {
    protected var value: O = buffer.getValue(index)

    fun save(): T {
        buffer.setValue(index, value)
        return this as T
    }

    fun refresh(): T {
        value = buffer.getValue(index)
        return this as T
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is VoxObjectRef<*, *>) return false

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

abstract class VoxelObjectBuffer<T : Any> {
    companion object {
        val emptyBuffer = Array<Any>(0) {}

        fun toIndex(x: Int, y: Int, z: Int): Int {
            assert(x in 0 until Chunk.SIZE) { "Failed to setValue: Invalid X coordinates" }
            assert(y in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Y coordinates" }
            assert(z in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Z coordinates" }

            return (x shl Chunk.SIZE_SHIFT_X) + (y shl Chunk.SIZE_SHIFT_Y) + z
        }
    }

    private var buffer = emptyBuffer

    val isEmpty get () = buffer.isEmpty()

    protected abstract fun instanciateObject(): T

    fun alloc() {
        assert(buffer === emptyBuffer)
        buffer = Array(Chunk.BUFFER_SIZE) {}
    }

    fun free() {
        buffer = emptyBuffer
    }

    @Suppress("UNCHECKED_CAST")
    internal fun getValue(index: Int): T {
        assert(index in 0 until Chunk.BUFFER_SIZE) { "Failed to getValue: Invalid index" }
        assert(buffer.isNotEmpty()) { "Failed to getValue:  Buffer isn't allocated" }

        val v = buffer[index]

        return if (v is Unit) {
            buffer[index] = instanciateObject()
            buffer[index] as T
        } else {
            v as T
        }
    }

    internal fun getValue(x: Int, y: Int, z: Int): T {
        assert(x in 0 until Chunk.SIZE) { "Failed to getValue: Invalid X coordinates" }
        assert(y in 0 until Chunk.SIZE) { "Failed to getValue: Invalid Y coordinates" }
        assert(z in 0 until Chunk.SIZE) { "Failed to getValue: Invalid Z coordinates" }

        return getValue(toIndex(x, y, z))
    }

    internal fun setValue(index: Int, value: T) {
        assert(index in 0 until Chunk.BUFFER_SIZE) { "Failed to setValue: Invalid index" }
        assert(buffer.isNotEmpty()) { "Failed to setValue:  Buffer isn't allocated" }

        buffer[index] = value
    }

    internal fun setValue(x: Int, y: Int, z: Int, value: T) {
        assert(x in 0 until Chunk.SIZE) { "Failed to setValue: Invalid X coordinates" }
        assert(y in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Y coordinates" }
        assert(z in 0 until Chunk.SIZE) { "Failed to setValue: Invalid Z coordinates" }

        setValue(toIndex(x, y, z), value)
    }
}