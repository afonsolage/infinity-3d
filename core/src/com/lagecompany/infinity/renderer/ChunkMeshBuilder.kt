package com.lagecompany.infinity.renderer

import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.lagecompany.infinity.utils.Side
import com.lagecompany.infinity.utils.addAndReturn
import com.lagecompany.infinity.world.Chunk
import com.lagecompany.infinity.world.VoxelType

data class SideData(val side: Side, val type: VoxelType, val floatBuffer: MutableList<Float> = mutableListOf()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SideData

        if (side != other.side) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = side.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

object ChunkMeshBuilder {
    private val attributes = arrayOf(
            VertexAttribute(VertexAttributes.Usage.Position, 3, "aPosition"),
            VertexAttribute(VertexAttributes.Usage.Normal, 3, "aNormal")
    )
    val emptyMesh = Mesh(true, 0, 0, *attributes)

    private val data = mutableListOf<SideData>()

    private val emptyIndexList = shortArrayOf()
    private val indexAdd = shortArrayOf(0, 1, 2, 2, 3, 0)

    private fun get(side: Side, type: VoxelType): SideData {
        return data.find { it.side == side && it.type == type } ?: data.addAndReturn(SideData(side, type))
    }

    private fun add(side: Side, type: VoxelType, vararg vertices: Float) {
        get(side, type).floatBuffer.addAll(vertices.asIterable())
    }

    private fun clear() {
        data.clear()
    }

    private val isEmpty get() = data.size == 0

    private fun vertexCount(): Int {
        return data.sumBy { it.floatBuffer.size } / 3
    }

    fun generate(chunk: Chunk): Mesh {
        clear()

        //TODO: Create faces merge method
        addAll(chunk)

        return buildMesh(chunk)
    }

    private fun buildMesh(chunk: Chunk): Mesh {
        val indices = getIndicesList()
        val vertices = getVertices()

        assert(vertices.size == vertexCount() * attributesSize()) {
            "${vertices.size} != ${vertexCount()} * ${attributesSize()} (${vertexCount() * attributesSize()})"
        }

        assert(indices.size == (vertexCount() * 1.5f).toInt()) {
            "${indices.size} != ${vertexCount()} * 1.5f} (${(vertexCount() * 1.5f).toInt()})"
        }

        val mesh = Mesh(true, vertices.size, indices.size, *attributes)

        mesh.setIndices(indices)
        mesh.setVertices(vertices)

        return mesh
    }

    private fun getVertices(): FloatArray {
        val result = FloatArray(vertexCount() * attributesSize())

        //add interleaved normal on vertices data.

        var i = 0
        data.forEach {
            //Copy vertices data
            val floats = it.floatBuffer.toFloatArray()

            //Count how many vertices we have
            val vertexCount = floats.size / 3
            val normalArr = Cube.normalArrays[it.side.ordinal]

            var src = 0
            repeat(vertexCount) {
                floats.copyInto(result, i, src, src + 3) //Copy vertex data
                i += 3
                src += 3
                normalArr.copyInto(result, i) //Copy normal data
                i += normalArr.size
            }
        }
        return result
    }

    private fun attributesSize(): Int {
        return attributes.sumBy { it.numComponents }
    }

    private fun getIndicesList(): ShortArray {
        if (isEmpty)
            return emptyIndexList

        //Each cube as 4 vertex and 6 index so this is a 1.5 ratio between vertex and index.
        val size = (vertexCount() * 1.5f).toInt()

        /*  Vertexes are built using the counter-clockwise, we just need to follow this index pattern:
         *		     3		2   2
         *		     +--------+    +
         *		     |       /   / |
         *		     |     /   /   |
         *		     |   /   /     |
         *		     | /   /	   |
         *		     +   +---------+
         *		    0    0	    1
         *
         * Each 6 increment on it, the index value should increment by 4, so this way we can ge the following pattern:
         * 0, 1, 2, 2, 3, 0,
         * 4, 5, 6, 6, 7, 4,
         * 8, 9, 10, 10, 11, 8,
         * 12, 13, 14, 14, 15, 12,
         * and so on...
         */
        return ShortArray(size) { (it / 6 * 4 + indexAdd[it % 6]).toShort() }
    }

    private fun addAll(chunk: Chunk) {
        for (x in 0 until Chunk.SIZE) {
            for (y in 0 until Chunk.SIZE) {
                for (z in 0 until Chunk.SIZE) {
                    val typeRef = chunk.types[x, y, z]

                    if (!VoxelType.isVisible(typeRef.get()))
                        continue

                    val sideRef = chunk.visibleSides[x, y, z]

                    if (sideRef.isNotVisible)
                        continue

                    for (side in Side.allSides) {
                        if (sideRef[side]) {
                            addVoxel(side, typeRef.get(), x, y, z)
                        }
                    }
                }
            }
        }
    }

    private fun addVoxel(side: Side, type: VoxelType, x: Int, y: Int, z: Int) {
        when (side) {
            Side.FRONT -> add(side, type, *Cube.frontVertices(x, y, z))
            Side.RIGHT -> add(side, type, *Cube.rightVertices(x, y, z))
            Side.BACK -> add(side, type, *Cube.backVertices(x, y, z))
            Side.LEFT -> add(side, type, *Cube.leftVertices(x, y, z))
            Side.UP -> add(side, type, *Cube.upVertices(x, y, z))
            Side.DOWN -> add(side, type, *Cube.downVertices(x, y, z))
        }
    }

}