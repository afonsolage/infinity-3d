package com.lagecompany.infinity.renderer

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.App
import com.lagecompany.infinity.stage.StageManager
import com.lagecompany.infinity.world.Chunk

const val MAX_VERTICES = 8 * Chunk.BUFFER_SIZE // 3 float per 3 vertices per 8 vertices per chunk
const val MAX_INDICES = 3 * 2 * 6 * Chunk.BUFFER_SIZE // 3 indices per triangle, 2 triangle per side, 6 sides

class ChunkRenderer(private val chunk: Chunk) : Disposable, InputAdapter() {

    private var mesh = ChunkMeshBuilder.emptyMesh

    private var primitiveType = GL20.GL_TRIANGLES

    fun setup() {
        mesh = ChunkMeshBuilder.generate(chunk)

        App.isDebug {
            StageManager.currentStage.addInputProcessor(this)
        }
    }

    fun render(shader: ShaderProgram) {
        mesh.render(shader, primitiveType)
    }

    override fun dispose() {
        mesh.dispose()
    }

    override fun keyUp(keycode: Int): Boolean {
        App.isDebug {
            when (keycode) {
                Input.Keys.TAB -> primitiveType = if (primitiveType == GL20.GL_TRIANGLES) GL20.GL_LINE_STRIP else GL20.GL_TRIANGLES
            }
        }
        return false
    }
}