package com.lagecompany.infinity

import com.lagecompany.infinity.stage.StageComponent
import com.lagecompany.infinity.world.World

class WorldRenderer : StageComponent {

    val world = World()

    override fun initialize() {
        world.generateAllChunks()
    }


    override fun tick(delta: Float) {
    }

    override fun render() {

    }

    override fun dispose() {
        world.dispose()
    }

}