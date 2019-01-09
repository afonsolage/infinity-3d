package com.lagecompany.infinity.renderer

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.lagecompany.infinity.App
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageComponent
import com.lagecompany.infinity.stage.StageManager
import com.lagecompany.infinity.world.Chunk

class ChunkRenderer(private val chunk: Chunk) : StageComponent, InputAdapter() {

    private var mesh = ChunkMeshBuilder.emptyMesh

    private var primitiveType = GL20.GL_TRIANGLES
    private val shapeRenderer = ShapeRenderer()

    fun setup() {
        mesh = ChunkMeshBuilder.generate(chunk)
        App.isDebug {
            StageManager.currentStage.addInputProcessor(this)
        }
    }

    override fun render() {
        mesh.render(currentStage<GameStage>().shader, primitiveType)
    }

    override fun renderDebug() {
        val currentStage = StageManager.currentStage
        if (currentStage is GameStage) {
            val cam = currentStage.camera

            val origin = Vector3(chunk.x.toFloat(), chunk.y.toFloat(), chunk.z.toFloat())
            val destiny = Vector3((chunk.x + Chunk.SIZE).toFloat(), (chunk.y + Chunk.SIZE).toFloat(), (chunk.z + Chunk.SIZE).toFloat())

            shapeRenderer.projectionMatrix = cam.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.setColor(1f, 0f, 0f, 0.5f)

            shapeRenderer.line(origin, Vector3(origin).mulAdd(Vector3.X, Chunk.SIZE.toFloat()))
            shapeRenderer.line(origin, Vector3(origin).mulAdd(Vector3.Y, Chunk.SIZE.toFloat()))
            shapeRenderer.line(origin, Vector3(origin).mulAdd(Vector3.Z, Chunk.SIZE.toFloat()))

            shapeRenderer.line(destiny, Vector3(destiny).mulAdd(Vector3.X, -Chunk.SIZE.toFloat()))
            shapeRenderer.line(destiny, Vector3(destiny).mulAdd(Vector3.Y, -Chunk.SIZE.toFloat()))
            shapeRenderer.line(destiny, Vector3(destiny).mulAdd(Vector3.Z, -Chunk.SIZE.toFloat()))

            val tmp1 = Vector3(origin).mulAdd(Vector3.X, Chunk.SIZE.toFloat())
            shapeRenderer.line(tmp1, Vector3(tmp1).mulAdd(Vector3.Y, Chunk.SIZE.toFloat()))
            tmp1.set(origin).mulAdd(Vector3.Z, Chunk.SIZE.toFloat())
            shapeRenderer.line(tmp1, Vector3(tmp1).mulAdd(Vector3.Y, Chunk.SIZE.toFloat()))
            tmp1.set(origin).mulAdd(Vector3.Y, Chunk.SIZE.toFloat())
            shapeRenderer.line(tmp1, Vector3(tmp1).mulAdd(Vector3.X, Chunk.SIZE.toFloat()))
            tmp1.set(origin).mulAdd(Vector3.Y, Chunk.SIZE.toFloat())
            shapeRenderer.line(tmp1, Vector3(tmp1).mulAdd(Vector3.Z, Chunk.SIZE.toFloat()))
            tmp1.set(destiny).mulAdd(Vector3.Y, -Chunk.SIZE.toFloat())
            shapeRenderer.line(tmp1, Vector3(tmp1).mulAdd(Vector3.X, -Chunk.SIZE.toFloat()))
            tmp1.set(destiny).mulAdd(Vector3.Y, -Chunk.SIZE.toFloat())
            shapeRenderer.line(tmp1, Vector3(tmp1).mulAdd(Vector3.Z, -Chunk.SIZE.toFloat()))

            shapeRenderer.end()
        }
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