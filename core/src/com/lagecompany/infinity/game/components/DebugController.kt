package com.lagecompany.infinity.game.components

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.lagecompany.infinity.game.Debug
import com.lagecompany.infinity.stage.StageObject

private const val TOGGLE_WIREFRAME = Input.Keys.TAB
private const val TOGGLE_CHUNK_BOUNDS = Input.Keys.NUMPAD_1
private const val TOGGLE_BACKFACE_CULLING = Input.Keys.NUMPAD_2
private const val TOGGLE_CLEAR_COLOR_RED = Input.Keys.NUMPAD_3

class DebugController : StageObject, InputAdapter() {

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
                TOGGLE_BACKFACE_CULLING -> Debug.disableBackfaceCulling = Debug.disableBackfaceCulling.not()
                TOGGLE_CLEAR_COLOR_RED -> Debug.clearColorRed = Debug.clearColorRed.not()
            }
        }
        return false
    }
}