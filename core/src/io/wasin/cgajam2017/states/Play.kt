package io.wasin.cgajam2017.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import io.wasin.cgajam2017.Game
import io.wasin.cgajam2017.entities.Player
import io.wasin.cgajam2017.entities.Smoke
import io.wasin.cgajam2017.entities.SmokePool
import io.wasin.cgajam2017.handlers.BBInput
import io.wasin.cgajam2017.handlers.GameStateManager

/**
 * Created by haxpor on 6/13/17.
 */
class Play(gsm: GameStateManager): GameState(gsm) {

    private var tileMap: TiledMap
    private var tmr: OrthogonalTiledMapRenderer
    private var tileSize: Float

    private var playerCam: OrthographicCamera
    private var playerCamTargetPosition: Vector3 = Vector3.Zero
    lateinit private var player: Player

    private var isReachedFarRight: Boolean = false
    private var isReachedFarLeft: Boolean = false
    private var playerCamTranslatedXOffset: Float = 0f
    private var smokePool: SmokePool
    private var activeSmoke: ArrayList<Smoke>

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

        // save the target player cam's position
        playerCamTargetPosition = Vector3(playerCam.position)

        smokePool = SmokePool(3)
        activeSmoke = ArrayList<Smoke>()

        createPlayer()
    }

    override fun handleInput() {
        if (BBInput.isDown(BBInput.BUTTON_UP)) {
            Gdx.app.log("Play", "Speed up")
            player.speedUp()
        }
        if (BBInput.isDown(BBInput.BUTTON_DOWN)) {
            Gdx.app.log("Play", "Speed down")
            player.speedDown()
        }
        if (BBInput.isPressed(BBInput.BUTTON_LEFT)) {

            if (!isReachedFarLeft) {
                Gdx.app.log("Play", "Strafe left")
                playerCamTranslatedXOffset -= 40f
                //playerCam.translate(-40f, 0f)
                playerCamTargetPosition.x += -40f

                if (playerCamTranslatedXOffset == -40f) {
                    isReachedFarLeft = true
                }
                isReachedFarRight = false

                Gdx.app.log("Play", "xoffset $playerCamTranslatedXOffset")
            }
        }
        if (BBInput.isPressed(BBInput.BUTTON_RIGHT)) {
            if (!isReachedFarRight) {
                Gdx.app.log("Play", "Strafe right")
                playerCamTranslatedXOffset += 40f
                //playerCam.translate(40f, 0f)
                playerCamTargetPosition.x += 40.0f

                if (playerCamTranslatedXOffset == 40.0f) {
                    isReachedFarRight = true
                }
                isReachedFarLeft = false

                Gdx.app.log("Play", "xoffset $playerCamTranslatedXOffset")
            }
        }
        if (BBInput.isPressed(BBInput.BUTTON1)) {
            if (player.onGround) {
                Gdx.app.log("Play", "Jump button is pressed")
                player.jump()

                // spawn a new smoke
                val smoke = smokePool.obtain()
                smoke.spawnAt(player.x, player.y, MathUtils.random(2.2f, 3.2f))
                smoke.callbackFadedOut = { it.markToRemove() }
                // add to active smoke
                activeSmoke.add(smoke)
            }
        }
    }

    override fun update(dt: Float) {
        handleInput()

        // update playerCam according to player's forward velocity
        // make the delay camera
        playerCamTargetPosition.y += player.forwardVelocity * dt
        playerCam.position.lerp(playerCamTargetPosition, 0.1f)
        playerCam.update()

        player.update(dt)
        activeSmoke.filter { !it.markedToBeRemoved }.map { it.update(dt) }

        if (activeSmoke.count() > 0) {
            for (i in activeSmoke.count() - 1 downTo 0) {
                val item = activeSmoke.get(i)
                if (item.markedToBeRemoved) {
                    activeSmoke.removeAt(i)
                    smokePool.free(item)
                }
            }
        }
    }

    override fun render() {

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        sb.projectionMatrix = playerCam.combined
        sb.begin()

        // draw tilemap
        tmr.setView(playerCam)
        tmr.render()
        sb.end()

        sb.begin()

        // draw smoke
        for (smoke in activeSmoke) {
            smoke.draw(sb)
        }

        // draw player
        player.setPosition(playerCam.viewportWidth/2f + player.width/2f + 8f, playerCamTargetPosition.y - playerCam.viewportHeight/2 + player.height/2f)
        player.draw(sb)

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

    private fun createPlayer() {
        val tex = Game.res.getTexture("jet")!!
        val texRegion = TextureRegion(tex, 0, 0, 32, 32)
        player = Player(texRegion)
    }
}