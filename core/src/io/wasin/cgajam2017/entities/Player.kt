package io.wasin.cgajam2017.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils

/**
 * Created by haxpor on 6/14/17.
 */
class Player(textureRegion: TextureRegion): Sprite(textureRegion) {

    var onGround: Boolean = true

    var forwardVelocity: Float = 0f
        private set
    var jumpVelocity: Float = 0f
        private set

    var endScaleFromJump: Float = 1.0f

    object Spec {
        const val JUMP_SHAKE_INTERVAL: Float = 0.2f
        const val GRAVITY: Float = 3.5f
        const val SPEED_INCREASE_RATE: Float = 3.0f
        const val SPEED_UPPER_LIMIT: Float = 400.0f
    }

    private var jump_shakeTimer: Float = 0f

    init {

    }

    fun update(dt: Float) {
        if (!onGround) {
            jumpVelocity += Spec.GRAVITY * dt

            // scale to make illusion of jumping
            val newScale = Interpolation.bounceIn.apply(Math.abs(jumpVelocity / Spec.GRAVITY) / Math.abs(endScaleFromJump)) * endScaleFromJump
            setScale(newScale)

            jump_shakeTimer += dt
            if (jump_shakeTimer > Spec.JUMP_SHAKE_INTERVAL) {
                rotation = MathUtils.random(-5f, 5f)
                jump_shakeTimer -= Spec.JUMP_SHAKE_INTERVAL
            }
        }

        // reset onGround flag if touch the ground
        if (!onGround && scaleX - 1.0f <= 0.001f) {
            onGround = true
            jump_shakeTimer = 0.0f
            setScale(1.0f)
            rotation = 0f
            Gdx.app.log("Player", "Player back to touch the ground")
        }
    }

    fun speedUp() {
        forwardVelocity += Spec.SPEED_INCREASE_RATE
        Gdx.app.log("Player", "Increased speed to $forwardVelocity")

        if (forwardVelocity > Spec.SPEED_UPPER_LIMIT) {
            forwardVelocity = Spec.SPEED_UPPER_LIMIT
        }
    }

    fun speedDown() {
        forwardVelocity -= Spec.SPEED_INCREASE_RATE
        if (forwardVelocity < 0f) {
            forwardVelocity = 0.0f
        }
    }

    fun strafeLeft() {

    }

    fun jump() {
        if (onGround) {
            jump_shakeTimer = 0.0f
            onGround = false
            jumpVelocity = -6.0f

            // calculate the reference end of jump speed
            endScaleFromJump = Math.abs(jumpVelocity / Spec.GRAVITY)
        }
    }
}