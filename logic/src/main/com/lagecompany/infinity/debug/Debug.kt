package com.lagecompany.infinity.debug

import com.lagecompany.infinity.world.World
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val world = World()
//    world.allocAllChunks()
//    runBlocking {
//        world.generateAllChunks()
//    }
    simpleMeasureTest(dispose = world::dispose) {
        runBlocking {
            world.generateAllChunks()
        }
    }

//    simpleMeasureTest {
//        runBlocking {
//            world.allocAllChunks()
//            world.generateAllChunks()
//            world.clearAllChunks()
//        }
//    }
}