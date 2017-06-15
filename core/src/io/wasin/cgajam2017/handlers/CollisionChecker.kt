package io.wasin.cgajam2017.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import io.wasin.cgajam2017.entities.Player

interface CollisionListener {
    /**
     * Player is current on non-existing tile at col and row.
     */
    fun playerOnFall(player: Player, tileMap: TiledMap, col: Int, row: Int)

}

/**
 * Created by haxpor on 6/16/17.
 */
class CollisionChecker(player: Player, tilemap: TiledMap, cam: OrthographicCamera) {
    private val player: Player = player
    private val tilemap: TiledMap = tilemap
    private val cam: OrthographicCamera = cam

    var listener: CollisionListener? = null

    private val tileSize: Float
    private val layer: TiledMapTileLayer
    private var isPlayerFell: Boolean = false

    init {
        // get reference to layer
        layer = tilemap.layers.get("floor") as TiledMapTileLayer

        // regard width and height to be the same size
        tileSize = tilemap.properties.get("tilewidth", Float::class.java)
    }

    fun update(dt: Float) {
        if (!isPlayerFell) {
            val (col, row) = convertPlayerPositionToTilePosition()

            var cell = layer.getCell(col, row)
            Gdx.app.log("CollisionChecker", "at $col,$row")
            if (cell == null || (cell != null && cell.tile == null)) {
                if (player.onGround) {
                    Gdx.app.log("CollisionChecker", "${player.scaleX}")
                    listener?.playerOnFall(player, tilemap, col, row)
                    isPlayerFell = true
                }
            }
        }
    }

    private fun convertPlayerPositionToTilePosition(): Pair<Int, Int> {
        val col = Math.floor( (player.x).toDouble() / tileSize).toInt()
        val row = Math.floor(player.y.toDouble() / tileSize).toInt()

        // TODO: I know, this line smells quite a lot, should be better way to avoid creating a new object every return...
        return Pair(col, row)
    }
}