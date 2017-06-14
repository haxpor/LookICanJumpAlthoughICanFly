package io.wasin.cgajam2017.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector3
import io.wasin.cgajam2017.Game
import io.wasin.cgajam2017.handlers.GameStateManager
import io.wasin.cgajam2017.ui.Tile

/**
 * Created by haxpor on 6/13/17.
 */
class Play(gsm: GameStateManager): GameState(gsm) {

    init {
    }

    override fun handleInput() {

    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render() {

        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        sb.projectionMatrix = cam.combined
        sb.begin()

        sb.end()
    }

    override fun dispose() {

    }

    override fun resize_user(width: Int, height: Int) {

    }
}