package com.lagecompany.infinity.renderer

import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageComponent
import com.lagecompany.infinity.world.World

class WorldRenderer : StageComponent {

    val chunkRenderers = mutableMapOf<Int, ChunkRenderer>()

    val world = World()

    override fun initialize() {
        world.generateAllChunks()

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