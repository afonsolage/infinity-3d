package com.lagecompany.infinity


object App {
    private const val DEBUG = true

    fun isDebug(lambda: () -> Unit) {
        if (DEBUG) {
            lambda()
        }
    }
}

