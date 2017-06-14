package io.wasin.cgajam2017.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import io.wasin.cgajam2017.Game
import io.wasin.cgajam2017.handlers.GameStateManager

/**
 * Created by haxpor on 6/13/17.
 */
class Play(gsm: GameStateManager): GameState(gsm) {

    private var tileMap: TiledMap
    private var tmr: OrthogonalTiledMapRenderer
    private var tileSize: Float

    private var playerCam: OrthographicCamera

    init {

        // create tilemap and get tilesize from map
        // TODO: change this to load general level case ...
        tileMap = TmxMapLoader().load("maps/level1.tmx")
        tileSize = tileMap.properties.get("tilewidth", Float::class.java)
        tmr = OrthogonalTiledMapRenderer(tileMap)

        playerCam = OrthographicCamera()
        playerCam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT)
        playerCam.translate(tileSize/2, 0f)
        playerCam.update()
    }

    override fun handleInput() {

    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render() {

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        sb.projectionMatrix = cam.combined
        sb.begin()

        // draw tilemap
        tmr.setView(playerCam)
        tmr.render()

        sb.end()
    }

    override fun dispose() {

    }

    override fun resize_user(width: Int, height: Int) {

    }

    private fun createBackendTiles() {
        val layer = tileMap.layers.get("floor") as TiledMapTileLayer

        for (row in 0..layer.width-1) {
            for (col in 0..layer.height-1) {

            }
        }
    }
}