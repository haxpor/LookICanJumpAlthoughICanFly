package io.wasin.cgajam2017.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import io.wasin.cgajam2017.Game

/**
 * Pallete type.
 */
enum class PalleteType {
    CGA_0,
    CGA_1
}

/**
 * Created by haxpor on 6/13/17.
 */
class Tile(x: Float, y: Float, width: Float, height: Float, palleteIndex: Int, palleteType: PalleteType): Box(x, y, 0f, 0f) {
    private val textureRegion: TextureRegion
    private var timer: Float = 0f
    private var maxTime: Float = 0.5f
    private var totalWidth: Float = width - 8
    private var totalHeight: Float = height - 8

    var selected: Boolean = false

    init {
        val tex = Game.res.getTexture("cga-palletes")!!

        // get the pallete to color this tile
        val regionY = if (palleteType == PalleteType.CGA_0) 0 else 1
        textureRegion = TextureRegion(tex, palleteIndex, regionY, 1, 1)
    }

    fun update(dt: Float) {
        if (timer < maxTime) {
            timer += dt
            width = (timer / maxTime) * totalWidth
            height = (timer / maxTime) * totalHeight

            if (width > totalWidth) {
                width = totalWidth
            }
            if (width < 0) {
                width = 0f
            }
            if (height > totalHeight) {
                height = totalHeight
            }
            if (height < 0) {
                height = 0f
            }
        }
    }

    fun render(sb: SpriteBatch) {
        sb.draw(textureRegion, x, y, width, height)
    }

    fun setTimer(timer: Float) {
        this.timer = timer
    }
}