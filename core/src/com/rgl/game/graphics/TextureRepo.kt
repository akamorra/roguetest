package com.rgl.game.graphics

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion


object TextureRepo {
    private const val TEXTURESIZE = 128
    private val atlas_texture = Texture("atlas-world.png")
    private val gui_texture = Texture("gui.png")
    private val items_texture = Texture("items.png")
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

    private val items_region: ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(items_texture, 0, 0, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE, 0, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, 0, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, 0, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, 0, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, 0, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, 0, TEXTURESIZE*8, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, 0, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*8, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, 0, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE*9, 0, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*9, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*9, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*9, TEXTURESIZE*3, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, 0, TEXTURESIZE*9, TEXTURESIZE, TEXTURESIZE),


        )
    )

    fun getGui(name: String): TextureRegion =
        when (name) {
            GuiName.INSPECT._name -> gui_region[0]
            else -> gui_region[0]
        }

    fun dispose() {
        atlas_texture.dispose()
        gui_texture.dispose()
        items_texture.dispose()
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
                getItemTexture(id)
            }
        }

    fun getItemTexture(id: Byte): TextureRegion =
        when (id) {
            ItemsSprites.SWORD1.id -> items_region[1]
            ItemsSprites.SWORD2.id -> items_region[2]
            ItemsSprites.SWORD3.id -> items_region[3]
            ItemsSprites.SWORD_UNKNOWN.id -> items_region[0]
            ItemsSprites.AXE1.id -> items_region[5]
            ItemsSprites.AXE2.id -> items_region[6]
            ItemsSprites.AXE3.id -> items_region[7]
            ItemsSprites.AXE_UNKNOWN.id -> items_region[4]
            ItemsSprites.MACE1.id -> items_region[9]
            ItemsSprites.MACE2.id -> items_region[10]
            ItemsSprites.MACE3.id -> items_region[11]
            ItemsSprites.MACE_UNKNOWN.id -> items_region[8]
            ItemsSprites.CHESTPLATE1.id -> items_region[13]
            ItemsSprites.CHESTPLATE2.id -> items_region[14]
            ItemsSprites.CHESTPLATE3.id -> items_region[15]
            ItemsSprites.ARMOR_UNKNOWN.id -> items_region[12]
            ItemsSprites.BOOTS1.id -> items_region[17]
            ItemsSprites.BOOTS2.id -> items_region[18]
            ItemsSprites.BOOTS3.id -> items_region[19]
            ItemsSprites.BOOTS_UNKNOWN.id -> items_region[16]
            ItemsSprites.POT1.id -> items_region[20]
            ItemsSprites.POT2.id -> items_region[21]
            ItemsSprites.POT3.id -> items_region[22]
            ItemsSprites.POT4.id -> items_region[23]
            ItemsSprites.SCROLL.id -> items_region[24]
            else -> atlasRegion[8]
        }
}