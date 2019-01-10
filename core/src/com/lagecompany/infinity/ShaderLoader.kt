package com.lagecompany.infinity

import com.badlogic.gdx.graphics.glutils.ShaderProgram

object ShaderLoader {
    val blankShader = ShaderProgram("shaders/blankVertex.glsl", "shaders/blankFragment.glsl")
    val lightingShader = ShaderProgram("shaders/lightingVertex.glsl", "shaders/lightingFragment.glsl")
}