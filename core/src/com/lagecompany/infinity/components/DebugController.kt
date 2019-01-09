package com.lagecompany.infinity.components

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.lagecompany.infinity.Debug
import com.lagecompany.infinity.stage.StageComponent

private const val TOGGLE_WIREFRAME = Input.Keys.TAB
private const val TOGGLE_CHUNK_BOUNDS = Input.Keys.F5

class DebugController : StageComponent, InputAdapter() {

    override fun initialize() {
        Debug.ifEnabled {
            currentStage().addInputProcessor(this)
        }
    }

    override fun dispose() {
        Debug.ifEnabled {
            currentStage().removeInputProcessor(this)
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        Debug.ifEnabled {
            when (keycode) {
                TOGGLE_WIREFRAME -> Debug.wireframe = Debug.wireframe.not()
                TOGGLE_CHUNK_BOUNDS -> Debug.chunkBounds = Debug.chunkBounds.not()
            }
        }
        return false
    }
}