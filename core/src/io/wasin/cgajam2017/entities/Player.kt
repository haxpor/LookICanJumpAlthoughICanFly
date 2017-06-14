package io.wasin.cgajam2017.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import io.wasin.cgajam2017.Game

/**
 * Created by haxpor on 6/14/17.
 */
class Player(textureRegion: TextureRegion): Sprite(textureRegion) {

    var onGround: Boolean = false

    init {

    }
}