package io.wasin.cgajam2017.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Pool
import io.wasin.cgajam2017.Game

/**
 * Created by haxpor on 6/15/17.
 */
class SmokePool(initialCapacity: Int) : Pool<Smoke>(initialCapacity) {

    private var texture: Texture = Game.res.getTexture("smoke")!!

    override fun newObject(): Smoke {
        return Smoke(texture)
    }
}