package com.lagecompany.infinity.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3


class FlyCameraController(private val camera: Camera) : InputAdapter() {
    private val strafeLeft = Input.Keys.A
    private val strafeRight = Input.Keys.D
    private val forward = Input.Keys.W
    private val backward = Input.Keys.S
    private val up = Input.Keys.SPACE
    private val down = Input.Keys.CONTROL_LEFT
    private val run = Input.Keys.SHIFT_LEFT

    private val listenKeys = intArrayOf(forward, backward, strafeLeft, strafeRight, up, down)

    private var velocity = 5f
    private var degreesPerPixel = 0.5f

    private val tmp = Vector3()
    private val tmp2 = Vector3()
    private val tmp3 = Vector3()

    private var moveForward = false
    private var moveBackward = false
    private var moveLeft = false
    private var moveRight = false
    private var moveUp = false
    private var moveDown = false
    private var shouldRun = false

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            forward -> moveForward = true
            backward -> moveBackward = true
            strafeRight -> moveRight = true
            strafeLeft -> moveLeft = true
            up -> moveUp = true
            down -> moveDown = true
            run -> shouldRun = true
        }
        return listenKeys.contains(keycode)
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            forward -> moveForward = false
            backward -> moveBackward = false
            strafeRight -> moveRight = false
            strafeLeft -> moveLeft = false
            up -> moveUp = false
            down -> moveDown = false
            run -> shouldRun = false
        }
        return listenKeys.contains(keycode)
    }

    fun setVelocity(velocity: Float) {
        this.velocity = velocity
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        Gdx.input.isCursorCatched = true
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        Gdx.input.isCursorCatched = false
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val deltaX = -Gdx.input.deltaX * degreesPerPixel
        val deltaY = -Gdx.input.deltaY * degreesPerPixel
        camera.direction.rotate(camera.up, deltaX)

        val oldPitchAxis = tmp.set(camera.direction).crs(camera.up).nor()
        val newDirection = tmp2.set(camera.direction).rotate(tmp, deltaY)
        val newPitchAxis = tmp3.set(tmp2).crs(camera.up)

        if (!newPitchAxis.hasOppositeDirection(oldPitchAxis))
            camera.direction.set(newDirection)

        return true
    }

    @JvmOverloads
    fun update(deltaTime: Float = Gdx.graphics.deltaTime) {
        val speed = velocity * (if (shouldRun) 2 else 1) * deltaTime

        if (moveForward) {
            tmp.set(camera.direction).nor().scl(speed)
            camera.position.add(tmp)
        }
        if (moveBackward) {
            tmp.set(camera.direction).nor().scl(-speed)
            camera.position.add(tmp)
        }
        if (moveRight) {
            tmp.set(camera.direction).crs(camera.up).nor().scl(speed)
            camera.position.add(tmp)
        }
        if (moveLeft) {
            tmp.set(camera.direction).crs(camera.up).nor().scl(-speed)
            camera.position.add(tmp)
        }
        if (moveUp) {
            tmp.set(camera.up).nor().scl(speed)
            camera.position.add(tmp)
        }
        if (moveDown) {
            tmp.set(camera.up).nor().scl(-speed)
            camera.position.add(tmp)
        }
        camera.update(true)
    }
}
