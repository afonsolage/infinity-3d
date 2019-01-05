package com.lagecompany.infinity.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.InfinityGame
import kotlin.reflect.KClass

interface StageComponent : Disposable {
    fun initialize()
    fun tick(delta: Float)
    fun render()
}

abstract class Stage : Disposable {
    private val renderables = mutableListOf<StageComponent>()

    val app get() = Gdx.app as InfinityGame

    open fun initialize() {}

    /**
     * Dispose it self and all it's children. If this method is overridden, it must call super.dispose() in
     * order to dispose any children or dispose all children manually
     */
    override fun dispose() {
        renderables.forEach { it.dispose() }
    }

    /**
     * Updates it's internal state and all it's children. If this method is overridden, it must call super.tick(Float) in
     * order to update any children or update all children manually
     */
    fun tick(delta: Float) {
        renderables.forEach { it.tick(delta) }
    }

    /**
     * Renders it self and all it's children. If this method is overridden, it must call super.render() in order to render
     * any children or render all children manually
     */
    fun render() {
        renderables.forEach { it.render() }
    }

    fun add(component: StageComponent) {
        renderables.add(component)
        component.initialize()
    }

    fun remove(component: StageComponent) {
        renderables.remove(component)
        component.dispose()
    }
}

object StageManager : Disposable {

    private val emptyStage = object : Stage() {}

    var currentStage: Stage = emptyStage
        private set

    fun <T : Stage> changeStage(stage: KClass<T>) {
        currentStage.dispose()
        currentStage = stage.constructors.find { it.parameters.isEmpty() }?.call() ?: throw RuntimeException("${stage.qualifiedName} must have a empty constructor")
        currentStage.initialize()
    }

    override fun dispose() {
        currentStage.dispose()
    }

    fun tick() {
        currentStage.tick(Gdx.graphics.deltaTime)
    }

    fun render() {
        currentStage.render()
    }
}