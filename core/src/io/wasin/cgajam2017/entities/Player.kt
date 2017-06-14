package io.wasin.cgajam2017.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import io.wasin.cgajam2017.Game

/**
 * Created by haxpor on 6/14/17.
 */
class Player(textureRegion: TextureRegion): Sprite(textureRegion) {

    var onGround: Boolean = true

    var forwardVelocity: Float = 0f
        private set
    var jumpVelocity: Float = 0f
        private set

    object Spec {
        const val JUMP_SHAKE_INTERVAL: Float = 0.1f
        const val GRAVITY: Float = 3f
    }

    private var jump_shakeTimer: Float = 0f

    init {

    }

    fun update(dt: Float) {
        x += forwardVelocity * dt
        y += jumpVelocity * dt

        if (!onGround) {
            jumpVelocity += Spec.GRAVITY * dt

            // scale to make illusion of jumping
            setScale(Math.abs(jumpVelocity / Spec.GRAVITY))

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
        forwardVelocity += 0.1f
    }

    fun speedDown() {
        forwardVelocity -= 0.1f
    }

    fun jump() {
        if (onGround) {
            jump_shakeTimer = 0.0f
            onGround = false
            jumpVelocity = -6.0f
        }
    }
}