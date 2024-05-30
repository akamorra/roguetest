package com.rgl.game.graphics

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

object TextureRepo {
    private const val TEXTURESIZE = 128
    private val atlas_texture = Texture("atlas-world.png")
    private val gui_texture = Texture("gui.png")
    private val atlasRegion: ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(atlas_texture, 0, 0, TEXTURESIZE, TEXTURESIZE * 2), //floor-1
            TextureRegion(atlas_texture, TEXTURESIZE, 0, TEXTURESIZE, TEXTURESIZE * 2), //floor-2
            TextureRegion(
                atlas_texture,
                TEXTURESIZE * 2,
                0,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //floor-3
            TextureRegion(atlas_texture, 0, 0, TEXTURESIZE, TEXTURESIZE * 2),
            TextureRegion(atlas_texture, 0, TEXTURESIZE * 2, TEXTURESIZE, TEXTURESIZE * 2), //wall-1
            TextureRegion(
                atlas_texture,
                TEXTURESIZE,
                TEXTURESIZE * 2,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //wall-transparent
            TextureRegion(
                atlas_texture,
                TEXTURESIZE * 2,
                TEXTURESIZE * 2,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //wall-transparent-mirror
            TextureRegion(
                atlas_texture,
                TEXTURESIZE * 3,
                TEXTURESIZE * 2,
                TEXTURESIZE,
                TEXTURESIZE * 2
            ), //wall transparent full
            TextureRegion(atlas_texture, 1, 1, 1, 1)//empty
        )
    )
    private val gui_region: ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(gui_texture, 32, 544, 320, 240),
        )
    )

    fun getGuiTexture(name: String): TextureRegion =
        when (name) {
            GuiName.INSPECT._name -> gui_region[0]
            else -> gui_region[0]
        }

    fun dispose() {
        atlas_texture.dispose()
        gui_texture.dispose()
    }

    fun getTexture(id: Byte): TextureRegion =
        when (id) {
            Textures.STONE1.id -> atlasRegion[0]
            Textures.STONE2.id -> atlasRegion[1]
            Textures.STONE3.id -> atlasRegion[2]
            Textures.DOOR.id -> atlasRegion[0]
            Textures.STONEWALL.id -> atlasRegion[4]
            Textures.STONEWALL_TRANSPARENT_W.id -> atlasRegion[5]
            Textures.STONEWALL_TRANSPARENT_E.id -> atlasRegion[6]
            Textures.STONEWALL_TRANSPARENT.id -> atlasRegion[7]
            Textures.EMPTY_TEXTURE.id -> atlasRegion[8]
            else -> {
                atlasRegion[8]
            }
        }

}