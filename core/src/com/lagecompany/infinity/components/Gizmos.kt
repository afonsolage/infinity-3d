package com.lagecompany.infinity.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageComponent

class Gizmos : StageComponent {

    private val shapeRenderer = ShapeRenderer()

    var position = Vector3.Zero

    override fun tick(delta: Float) {
        position = currentStage<GameStage>().camera.position ?: Vector3.Zero
    }

    override fun render() {
        val cam = currentStage<GameStage>().camera
        shapeRenderer.projectionMatrix = cam.combined

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        shapeRenderer.color = Color.RED
        shapeRenderer.line(Vector3.Zero, Vector3(100f, 0f, 0f))
        shapeRenderer.color = Color.GREEN
        shapeRenderer.line(Vector3.Zero, Vector3(0f, 100f, 0f))
        shapeRenderer.color = Color.BLUE
        shapeRenderer.line(Vector3.Zero, Vector3(0f, 0f, 100f))

        shapeRenderer.end()
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }

}