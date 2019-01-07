package com.lagecompany.infinity.renderer

import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageComponent
import com.lagecompany.infinity.world.World
import kotlinx.coroutines.runBlocking

class WorldRenderer : StageComponent {

    val chunkRenderers = mutableMapOf<Int, ChunkRenderer>()

    val world = World()

    override fun initialize() {
        runBlocking {
            //TODO: Find better way to do it
            world.allocAllChunks()
            world.generateAllChunks()
        }

        for(i in 0 until World.SIZE) {
            val chunk = world[i]

            if (chunk.isEmpty)
                continue

            val renderer = ChunkRenderer(chunk)
            renderer.setup()
            chunkRenderers[i] = renderer
        }
    }

    override fun tick(delta: Float) {
    }

    override fun render() {
        val stage = currentStage<GameStage>()

        chunkRenderers.values.forEach {
            it.render(stage.shader)
        }
    }

    override fun dispose() {
        world.dispose()
    }

}