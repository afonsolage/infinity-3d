package com.lagecompany.infinity

object Debug {
    private const val DEBUG = true

    var chunkBounds = false
    var wireframe = false

    fun ifEnabled(lambda: () -> Unit) {
        if (DEBUG) {
            lambda()
        }
    }
}

