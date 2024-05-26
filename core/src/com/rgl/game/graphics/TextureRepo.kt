package com.rgl.game.graphics

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

object TextureRepo {
    const val TEXTURESIZE = 128
    private val texture = Texture("atlas-world.png")
    private val textureRegion: ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(texture, 0, 0, TEXTURESIZE, TEXTURESIZE * 2), //floor-1
            TextureRegion(texture, TEXTURESIZE, 0, TEXTURESIZE, TEXTURESIZE * 2), //floor-2
            TextureRegion(texture, TEXTURESIZE * 2, 0, TEXTURESIZE, TEXTURESIZE * 2), //floor-3
            TextureRegion(texture, 0, 0, TEXTURESIZE, TEXTURESIZE * 2),
            TextureRegion(texture, 0, TEXTURESIZE * 2, TEXTURESIZE, TEXTURESIZE * 2), //wall-1
            TextureRegion(
                texture,
                TEXTURESIZE,
                TEXTURESIZE * 2,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //wall-transparent
            TextureRegion(
                texture,
                TEXTURESIZE * 2,
                TEXTURESIZE * 2,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //wall-transparent-mirror
            TextureRegion(
                texture,
                TEXTURESIZE * 3,
                TEXTURESIZE * 2,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //wall transparent full
        )
    )

    fun dispose() {
        texture.dispose()
    }

    fun getTexture(id: Byte): TextureRegion =
        when (id) {
            Textures.STONE1.id -> textureRegion[0]
            Textures.STONE2.id -> textureRegion[1]
            Textures.STONE3.id -> textureRegion[2]
            Textures.GRASS.id -> textureRegion[0]
            Textures.STONEWALL.id -> textureRegion[4]
            Textures.STONEWALL_TRANSPARENT_W.id -> textureRegion[5]
            Textures.STONEWALL_TRANSPARENT_E.id -> textureRegion[6]
            Textures.STONEWALL_TRANSPARENT.id -> textureRegion[7]
            else -> {
                textureRegion[0]
            }
        }

}