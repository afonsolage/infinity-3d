package com.lagecompany.infinity

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageManager

class InfinityGame : ApplicationAdapter() {
    override fun create() {
        StageManager.changeStage(GameStage::class)
    }

    override fun render() {
        StageManager.tick()
        StageManager.render()
    }

    override fun dispose() {
        StageManager.dispose()
    }
}