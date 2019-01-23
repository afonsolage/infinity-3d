package com.lagecompany.infinity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import ktx.log.error

object ShaderLoader {
    val blankShader = ShaderProgram(Gdx.files.internal("shaders/blankVertex.glsl"), Gdx.files.internal("shaders/blankFragment.glsl"))
    val lightingShader = ShaderProgram(Gdx.files.internal("shaders/lightingVertex.glsl"), Gdx.files.internal("shaders/lightingFragment.glsl"))

    init {
        if (!blankShader.isCompiled) {
            error("[FATAL]") { "Failed to compile blank shader:\n ${blankShader.log ?: "Unknown"}. Src:\n ${blankShader.vertexShaderSource}" }
            Gdx.app.exit()
        }
        if (!lightingShader.isCompiled) {
            error { "Failed to compile lighting shader:\n ${lightingShader.log ?: "Unknown"}. Src:\\n ${blankShader.vertexShaderSource}" }
            Gdx.app.exit()
        }
    }
}