package com.lagecompany.infinity.game.renderer

import com.badlogic.gdx.graphics.Texture
import com.lagecompany.infinity.game.BlocksTextureLoader
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageComponent
import com.lagecompany.infinity.world.World
import kotlinx.coroutines.runBlocking

class WorldRenderer : StageComponent {

    private val chunkRenderers = mutableMapOf<Int, ChunkRenderer>()

    private val world = World()
    private lateinit var atlas: Texture

    override fun initialize() {
        atlas = BlocksTextureLoader.loadBlocksTexture()

        runBlocking {
            //TODO: Find better way to do it
            world.allocAllChunks()
            world.generateAllChunks()
        }

        for (i in 0 until World.SIZE) {
            val chunk = world[i]

            if (chunk.isEmpty)
                continue

            val renderer = ChunkRenderer(chunk)
            renderer.setup()
            chunkRenderers[i] = renderer
            currentStage<GameStage>().add(renderer)
        }
    }

    override fun tick(delta: Float) {
    }

    override fun dispose() {
        world.dispose()
        atlas.dispose()
    }

}