package com.lagecompany.infinity.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.Disposable
import com.lagecompany.infinity.InfinityGame
import com.lagecompany.infinity.game.Debug
import kotlin.reflect.KClass

/**
 * A simple object that has the ability cyclic life-time: initialize, update it's internal state, render and dispose when done.
 */
interface StageObject : Disposable {
    fun <T> currentStage(): T {
        return StageManager.currentStage as T ?: throw ClassCastException()
    }

    fun currentStage(): Stage {
        return StageManager.currentStage
    }

    /**
     * Initialize it self.
     */
    fun initialize() {}

    /**
     * Dispose it self.
     */
    override fun dispose() {}

    /**
     * Updates it's internal state.
     */
    fun tick(delta: Float) {}

    /**
     * Renders it self.
     */
    fun render() {}

    /**
     * Renders debug it self. This method will be called only
     * if the flag Debug.DEBUG is set to true
     */
    fun renderDebug() {}
}

/**
 * A node that has the ability cyclic life-time: initialize, update it's internal state, render and dispose when done. It also
 * has children that do the same.
 */
interface StageNode : StageObject {
    val children: MutableCollection<StageObject>

    /**
     * Initialize it self and all it's children. If this method is overridden, it must call super.initialize() in
     * order to initialize any children or initialize all children manually
     */
    override fun initialize() {
        children.forEach { it.initialize() }
    }

    /**
     * Dispose it self and all it's children. If this method is overridden, it must call super.dispose() in
     * order to dispose any children or dispose all children manually
     */
    override fun dispose() {
        children.forEach { it.dispose() }
    }

    /**
     * Updates it's internal state and all it's children. If this method is overridden, it must call super.tick(Float) in
     * order to update any children or update all children manually
     */
    override fun tick(delta: Float) {
        children.forEach { it.tick(delta) }
    }

    /**
     * Renders it self and all it's children. If this method is overridden, it must call super.render() in order to render
     * any children or render all children manually
     */
    override fun render() {
        children.forEach { it.render() }
    }

    /**
     * Renders debug it self and all it's children. If this method is overridden, it must call super.renderDebug()
     * in order to render debug any children or render debug all children manually. This method will be called only
     * if the flag Debug.DEBUG is set to true
     */
    override fun renderDebug() {
        Debug.ifEnabled {
            children.forEach { it.renderDebug() }
        }
    }

    fun add(node: StageObject) {
        assert(node != this) { "Can't add your self" }
        children.add(node)
        node.initialize()
    }

    fun remove(node: StageObject) {
        children.remove(node)
        node.dispose()
    }
}

abstract class Stage : StageNode {
    private val _children = mutableListOf<StageObject>()
    override val children: MutableList<StageObject>
        get() = _children

    private val inputMultiplexer = InputMultiplexer()

    abstract fun getCamera(): Camera

    val app get() = Gdx.app as InfinityGame

    override fun initialize() {
        Gdx.input.inputProcessor = inputMultiplexer
        super.initialize()
    }

    fun addInputProcessor(inputProcessor: InputProcessor) {
        inputMultiplexer.addProcessor(inputProcessor)
    }

    fun removeInputProcessor(inputProcessor: InputProcessor) {
        inputMultiplexer.removeProcessor(inputProcessor)
    }

    open fun resizeViewport(width: Int, height: Int) {}
}

object StageManager : Disposable {
    private val emptyStage = object : Stage() {
        override fun getCamera(): Camera {
            throw UnsupportedOperationException()
        }
    }

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