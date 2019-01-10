package com.lagecompany.infinity.game.renderer

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.lagecompany.infinity.game.Debug
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageManager
import com.lagecompany.infinity.stage.StageObject
import com.lagecompany.infinity.world.Chunk

private const val DEFAULT_PRIMITIVE = GL20.GL_TRIANGLES
private const val WIREFRAME_PRIMITIVE = GL20.GL_LINES

class ChunkRenderer(private val chunk: Chunk) : StageObject() {

    private var mesh = ChunkMeshBuilder.emptyMesh

    private var primitiveType = DEFAULT_PRIMITIVE
    private val shapeRenderer = ShapeRenderer()

    fun setup() {
        mesh = ChunkMeshBuilder.generate(chunk)
    }

    override fun tick(delta: Float) {
        Debug.ifEnabled {
            primitiveType = if (Debug.wireframe) WIREFRAME_PRIMITIVE else DEFAULT_PRIMITIVE
        }
    }

    override fun render() {
        mesh.render(shader, primitiveType)
    }

    override fun renderDebug() {
        if (!Debug.chunkBounds)
            return

        if (currentStage<GameStage>().camera.position.dst(chunk.x.toFloat(), chunk.y.toFloat(), chunk.z.toFloat()) > 100)
            return

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
}