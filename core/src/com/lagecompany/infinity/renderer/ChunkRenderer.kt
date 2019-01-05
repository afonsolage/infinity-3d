package com.lagecompany.infinity.renderer

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.world.Chunk
import com.lagecompany.infinity.world.ChunkBuffer

const val MAX_VERTICES = 8 * ChunkBuffer.BUFFER_SIZE // 3 float per 3 vertices per 8 vertices per chunk
const val MAX_INDICES = 3 * 2 * 6 * ChunkBuffer.BUFFER_SIZE // 3 indices per triangle, 2 triangle per side, 6 sides

class ChunkRenderer(private val chunk: Chunk) : Disposable {

    private val mesh = Mesh(true, MAX_VERTICES, MAX_INDICES,
            VertexAttribute(VertexAttributes.Usage.Position, 3, "a_Position"))

    fun setup() {
        val vertices = FloatArray(MAX_VERTICES * 3)
        val indices = ShortArray(MAX_INDICES)

        var i = 0
        var k = 0
        var l: Short = 0

        for (x in 0 until ChunkBuffer.SIZE) {
            for (y in 0 until ChunkBuffer.SIZE) {
                for (z in 0 until ChunkBuffer.SIZE) {
                    val cube = Cube((x + chunk.x).toFloat(), (y + chunk.y).toFloat(), (z + chunk.z).toFloat())
                    cube.v0.append(vertices, i)
                    i += 3
                    cube.v1.append(vertices, i)
                    i += 3
                    cube.v2.append(vertices, i)
                    i += 3
                    cube.v3.append(vertices, i)
                    i += 3
                    cube.v4.append(vertices, i)
                    i += 3
                    cube.v5.append(vertices, i)
                    i += 3
                    cube.v6.append(vertices, i)
                    i += 3
                    cube.v7.append(vertices, i)
                    i += 3

                    indices[k++] = l
                    indices[k++] = (l + 1).toShort()
                    indices[k++] = (l + 2).toShort()
                    indices[k++] = (l + 2).toShort()
                    indices[k++] = (l + 3).toShort()
                    indices[k++] = l

                    indices[k++] = (l + 1).toShort()
                    indices[k++] = (l + 5).toShort()
                    indices[k++] = (l + 6).toShort()
                    indices[k++] = (l + 6).toShort()
                    indices[k++] = (l + 2).toShort()
                    indices[k++] = (l + 1).toShort()

                    indices[k++] = (l + 5).toShort()
                    indices[k++] = (l + 4).toShort()
                    indices[k++] = (l + 7).toShort()
                    indices[k++] = (l + 7).toShort()
                    indices[k++] = (l + 6).toShort()
                    indices[k++] = (l + 5).toShort()

                    indices[k++] = (l + 4).toShort()
                    indices[k++] = l
                    indices[k++] = (l + 3).toShort()
                    indices[k++] = (l + 3).toShort()
                    indices[k++] = (l + 7).toShort()
                    indices[k++] = l

                    indices[k++] = (l + 3).toShort()
                    indices[k++] = (l + 2).toShort()
                    indices[k++] = (l + 6).toShort()
                    indices[k++] = (l + 6).toShort()
                    indices[k++] = (l + 7).toShort()
                    indices[k++] = (l + 3).toShort()

                    indices[k++] = (l + 4).toShort()
                    indices[k++] = (l + 5).toShort()
                    indices[k++] = (l + 1).toShort()
                    indices[k++] = (l + 1).toShort()
                    indices[k++] = l
                    indices[k++] = (l + 4).toShort()

                    l = (l + 8).toShort()
                }
            }
        }
        mesh.setVertices(vertices)
        mesh.setIndices(indices)
    }

    fun render(shader: ShaderProgram) {
        mesh.render(shader, GL20.GL_TRIANGLES)
    }

    override fun dispose() {

    }

}