package com.lagecompany.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.lagecompany.InfinityGame

fun main (args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(InfinityGame(), config)
}