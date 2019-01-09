package com.lagecompany.infinity

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3
import ktx.math.minus

fun Camera.changeDirection(target: Vector3) {
    direction.set((target - this.position).nor())
}
