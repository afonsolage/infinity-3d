package com.lagecompany.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.lagecompany.infinity.InfinityGame

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 1024
    config.height = 768
    LwjglApplication(InfinityGame(), config)
}