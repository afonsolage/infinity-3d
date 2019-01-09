package com.lagecompany.infinity.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector3
import com.lagecompany.infinity.App
import com.lagecompany.infinity.components.FlyCameraController
import com.lagecompany.infinity.components.Gizmos
import com.lagecompany.infinity.renderer.WorldRenderer

private const val LOG_TAG = "GameStage"

class GameStage : Stage() {

    val camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    val shader = ShaderProgram(Gdx.files.internal("shaders/lightingVertex.glsl"), Gdx.files.internal("shaders/lightingFragment.glsl"))
    private val cameraController = FlyCameraController(camera)

    private val sunDir = Vector3(.25f, 1f, .45f)

    override fun initialize() {
        super.initialize()

        Gdx.app.log(LOG_TAG, "Initializing")

        camera.near = 0.01f
        camera.far = 1500f
        camera.up.set(Vector3.Y)
        camera.position.set(-20f, 10f, -20f)
        camera.update()

        cameraController.setVelocity(15f)
        addInputProcessor(cameraController)

        add(WorldRenderer())

        App.isDebug {
            add(Gizmos())
        }
    }

    override fun dispose() {
        Gdx.app.log(LOG_TAG, "Disposed")
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glEnable(GL20.GL_CULL_FACE)
        Gdx.gl.glCullFace(GL20.GL_BACK)

        cameraController.update()
        shader.begin()
        shader.setUniformMatrix("viewProjMatrix", camera.combined)
        shader.setUniform4fv("sunDir", floatArrayOf(sunDir.x, sunDir.y, sunDir.z, 0.0f), 0, 4)

        //Render components
        super.render()

        shader.end()
    }
}