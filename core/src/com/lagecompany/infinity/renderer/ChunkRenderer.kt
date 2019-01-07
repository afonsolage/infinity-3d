package com.lagecompany.infinity.renderer

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.world.Chunk

const val MAX_VERTICES = 8 * Chunk.BUFFER_SIZE // 3 float per 3 vertices per 8 vertices per chunk
const val MAX_INDICES = 3 * 2 * 6 * Chunk.BUFFER_SIZE // 3 indices per triangle, 2 triangle per side, 6 sides

class ChunkRenderer(private val chunk: Chunk) : Disposable {

    private var mesh = ChunkMeshBuilder.emptyMesh

    fun setup() {
        mesh = ChunkMeshBuilder.generate(chunk)
    }

    fun render(shader: ShaderProgram) {
        mesh.render(shader, GL20.GL_TRIANGLES)
    }

    override fun dispose() {
        mesh.dispose()
    }
}