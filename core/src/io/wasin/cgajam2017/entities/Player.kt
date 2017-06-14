package io.wasin.cgajam2017.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import io.wasin.cgajam2017.Game

/**
 * Created by haxpor on 6/14/17.
 */
class Player {

    lateinit private var textureRegion: TextureRegion

    init {
        val tex = Game.res.getTexture("jet")!!
    }

    fun update(dt: Float) {

    }

    fun render(sb: SpriteBatch) {

    }
}