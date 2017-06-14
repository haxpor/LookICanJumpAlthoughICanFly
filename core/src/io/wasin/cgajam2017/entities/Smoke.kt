package io.wasin.cgajam2017.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pool

/**
 * Created by haxpor on 6/15/17.
 */
class Smoke(texture: Texture): Sprite(texture, 32, 32), Pool.Poolable {

    private var startedAnimation: Boolean = false
    private var animation: Animation<TextureRegion>
    private var animationTimer: Float = 0.0f
    private var fadeoutTimeout: Float = 1.0f
    var callbackFadedOut: ((Smoke) -> Unit)? = null

    var markedToBeRemoved: Boolean = false
        private set

    init {
        val tmpFrames = TextureRegion.split(texture, 32, 32)

        val smokeFrames = Array<TextureRegion>()
        for (col in 0..5) {
            smokeFrames.add(tmpFrames[0][col])
        }

        animation = Animation<TextureRegion>(1/12f, smokeFrames)
    }

    fun update(dt: Float) {
        if (startedAnimation && !markedToBeRemoved) {
            animationTimer += dt

            val currentFrameRegion = animation.getKeyFrame(animationTimer)
            setRegion(currentFrameRegion)

            val currentFrameIndex = animation.getKeyFrameIndex(animationTimer)
            if (currentFrameIndex == animation.keyFrames.size-1) {
                fadeoutTimeout -= dt

                if (fadeoutTimeout < 0f) {
                    setAlpha(0f)
                    callbackFadedOut?.invoke(this)
                }
                else {
                    setAlpha(fadeoutTimeout)
                }
            }
        }
    }

    fun spawnAt(x: Float, y: Float, scale: Float) {
        this.x = x
        this.y = y
        setScale(scale)
        startedAnimation = true
    }

    override fun reset() {
        setPosition(0f, 0f)
        startedAnimation = false
        setAlpha(1.0f)
        markedToBeRemoved = false
        fadeoutTimeout = 1.0f
        animationTimer = 0.0f
        setScale(1.0f)
    }

    fun markToRemove() {
        markedToBeRemoved = true
    }
}