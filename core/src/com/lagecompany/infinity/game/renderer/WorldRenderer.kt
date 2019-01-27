package com.lagecompany.infinity.game.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.lagecompany.infinity.ShaderLoader
import com.lagecompany.infinity.game.BlocksTextureLoader
import com.lagecompany.infinity.game.Debug
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageNode
import com.lagecompany.infinity.world.World

class WorldRenderer : StageNode() {
    private val world = World()
    private lateinit var atlas: Texture

    override fun initialize() {
        atlas = BlocksTextureLoader.loadBlocksTexture()
        shader = ShaderLoader.lightingShader

        world.generateAllChunks()

        for (i in 0 until World.SIZE) {
            val chunk = world[i]

            if (chunk.isEmpty)
                continue

            val renderer = ChunkRenderer(chunk)
            renderer.setup()
            add(renderer)
        }
    }

    override fun dispose() {
        super.dispose()

        world.dispose()
        atlas.dispose()
    }

    override fun render() {
        val gameStage = currentStage<GameStage>()

        val sunDir = gameStage.sunDir
        assert(sunDir.isUnit) { "sun direction must be normalized (sunDir.isUnit = ${sunDir.isUnit})" }

        shader.begin()
        shader.setUniformMatrix("uViewProjectionMatrix", gameStage.camera.combined)
        shader.setUniform4fv("uSunDir", floatArrayOf(sunDir.x, sunDir.y, sunDir.z, 0.0f), 0, 4)
        shader.setUniformf("uTileSize", BlocksTextureLoader.tileSize)

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
        atlas.bind()
        shader.setUniformi("uTexAtlas", 0)

        //Render children
        super.render()

        Debug.ifEnabled {
            super.renderDebug()
        }

        shader.end()
    }
}