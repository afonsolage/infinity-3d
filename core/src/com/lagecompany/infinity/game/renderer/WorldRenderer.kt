package com.lagecompany.infinity.game.renderer

import com.badlogic.gdx.graphics.Texture
import com.lagecompany.infinity.game.BlocksTextureLoader
import com.lagecompany.infinity.stage.GameStage
import com.lagecompany.infinity.stage.StageNode
import com.lagecompany.infinity.stage.StageObject
import com.lagecompany.infinity.world.World

class WorldRenderer : StageNode {
    override val children: MutableCollection<StageObject>
        get() = chunkMap.values

    private val chunkMap = mutableMapOf<Int, StageObject>()

    private val world = World()
    private lateinit var atlas: Texture

    override fun initialize() {
        atlas = BlocksTextureLoader.loadBlocksTexture()

        world.generateAllChunks()

        for (i in 0 until World.SIZE) {
            val chunk = world[i]

            if (chunk.isEmpty)
                continue

            val renderer = ChunkRenderer(chunk)
            renderer.setup()
            chunkMap[i] = renderer
            currentStage<GameStage>().add(renderer)
        }

        super.initialize()
    }

    override fun dispose() {
        super.dispose()

        world.dispose()
        atlas.dispose()
    }

    override fun render() {
        //TODO: Shader Programa

        super.render()
    }
}