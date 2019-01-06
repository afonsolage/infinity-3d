package com.lagecompany.infinity.debug

import com.lagecompany.infinity.world.World
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val world = World()
    println("-------NON-SEQUENCE-----------")
    simpleMeasureTest {
        runBlocking {
            world.generateAllChunks()
        }
        world.clearAllChunks()
    }
}