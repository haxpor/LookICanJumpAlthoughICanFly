package io.wasin.cgajam2017.handlers

import io.wasin.cgajam2017.Game
import io.wasin.cgajam2017.states.*

import java.util.Stack

/**
 * Created by haxpor on 5/14/17.
 */

class GameStateManager(game: Game){
    var game: Game
        private set
    private var gameStates: Stack<GameState>

    private var isCurrentStateClear: Boolean = false
    private var currentStateCystalsAmount: Int = 0
    private var currentMaxCrystalAmount: Int = 0

    init {
        this.game = game
        this.gameStates = Stack<GameState>()
    }

    companion object {
        const val MAINMENU = 5001
        const val PLAY = 5002
    }

    fun update(dt: Float) {
        this.gameStates.peek().update(dt)
    }

    fun resize(width: Int, height: Int) {
        this.gameStates.peek().resize(width, height)
    }

    fun render() {
        this.gameStates.peek().render()
    }

    private fun getState(state: Int): GameState? {
        if (state == MAINMENU) return Mainmenu(this)
        else if (state == PLAY) return Play(this)
        return null
    }

    fun setState(state: Int) {
        this.gameStates.clear()
        this.pushState(state)
    }

    fun pushState(state: Int) {
        this.gameStates.push(this.getState(state))
    }

    fun popState() {
        val g = this.gameStates.pop()
        g.dispose()
    }

    fun setCurrentActiveLevelAsGameOver() {
        isCurrentStateClear = false
        currentStateCystalsAmount = 0
        currentMaxCrystalAmount = 0
    }

    fun setCurrentActiveLevelAsClear(crystals: Int, maxCrystals: Int) {
        isCurrentStateClear = true
        currentStateCystalsAmount = crystals
        currentMaxCrystalAmount = maxCrystals
    }

    fun resetPreviousActiveLevelState() {
        setCurrentActiveLevelAsGameOver()
    }
}