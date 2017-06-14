package io.wasin.cgajam2017.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

/**
 * Created by haxpor on 5/17/17.
 */
class Content {

    private var textures: HashMap<String, Texture> = HashMap()
    private var textureAtlases: HashMap<String, TextureAtlas> = HashMap()
    private var sounds: HashMap<String, Sound> = HashMap()
    private var musics: HashMap<String, Music> = HashMap()

    // ** Textures **
    fun loadTexture(path: String, key: String) {
        val tex = Texture(Gdx.files.internal(path))
        textures.put(key, tex)
    }

    fun getTexture(key: String) : Texture? {
        return textures[key]
    }

    fun removeTexture(key: String) {
        val tex = textures.get(key)
        if (tex != null) {
            textures.remove(key)
            tex.dispose()
        }
    }

    // ** TextureAtlases ** //
    fun loadAtlas(path: String, key: String) {
        textureAtlases.put(key, TextureAtlas(Gdx.files.internal(path)))
    }

    fun getAtlas(key: String): TextureAtlas? {
        return textureAtlases[key]
    }

    // ** Sounds ** //
    fun loadSound(path: String, key: String) {
        val s = Gdx.audio.newSound(Gdx.files.internal(path))
        sounds.put(key, s)
    }

    fun getSound(key: String): Sound? {
        return sounds[key]
    }

    fun removeSound(key: String) {
        val s = sounds.get(key)
        if (s != null) {
            sounds.remove(key)
            s.dispose()
        }
    }

    // ** Musics ** //
    fun loadMusic(path: String, key: String) {
        val m = Gdx.audio.newMusic(Gdx.files.internal(path))
        musics.put(key, m)
    }

    fun getMusic(key: String): Music? {
        return musics[key]
    }

    fun removeMusic(key: String) {
        val m = musics.get(key)
        if (m != null) {
            musics.remove(key)
            m.dispose()
        }
    }
}