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
            TextureRegion(atlas_texture, 1, 1, 1, 1),//empty
            TextureRegion(atlas_texture, TEXTURESIZE*3, 0, TEXTURESIZE, TEXTURESIZE*2),
        )
    )


    private val gui_region: ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(gui_texture, 32, 544, 320, 240),
            TextureRegion(gui_texture,28,0,904,512),
            TextureRegion(gui_texture,32,832,128,32),
            TextureRegion(gui_texture,32,896,360,128),
            TextureRegion(gui_texture,192,800,64,64),
            TextureRegion(gui_texture,256,800,64,64),
            TextureRegion(gui_texture,362,540,140,148)
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
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*4, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*5, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE*9, 0, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*9, TEXTURESIZE, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*9, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*9, TEXTURESIZE*3, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, 0, TEXTURESIZE*9, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*3, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE*3, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*3, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*4, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE*4, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*4, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*5, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE*5, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*5, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, 0, TEXTURESIZE*7, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE, TEXTURESIZE*7, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*2, TEXTURESIZE*7, TEXTURESIZE, TEXTURESIZE),

            TextureRegion(items_texture, TEXTURESIZE*3, TEXTURESIZE*7, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*4, TEXTURESIZE*7, TEXTURESIZE, TEXTURESIZE),
            TextureRegion(items_texture, TEXTURESIZE*5, TEXTURESIZE*7, TEXTURESIZE, TEXTURESIZE),
        )
    )

    private val player_region:ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(atlas_texture, TEXTURESIZE*7, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*8, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*9, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE*2),

            TextureRegion(atlas_texture, TEXTURESIZE*7, 0, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*8, 0, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*9, 0, TEXTURESIZE, TEXTURESIZE*2)

        )
    )
    private val monsters_region:ArrayList<TextureRegion> = ArrayList(
        listOf(
            TextureRegion(atlas_texture, TEXTURESIZE*7, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*8, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*9, TEXTURESIZE*6, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*7, TEXTURESIZE*4, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*8, TEXTURESIZE*4, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*9, TEXTURESIZE*4, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*4, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*5, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*6, TEXTURESIZE*2, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*4, 0, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*5, 0, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*6, 0, TEXTURESIZE, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*1, TEXTURESIZE*8, TEXTURESIZE*2, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*3, TEXTURESIZE*8, TEXTURESIZE*2, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*5, TEXTURESIZE*8, TEXTURESIZE*2, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*1, TEXTURESIZE*6, TEXTURESIZE*2, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*3, TEXTURESIZE*6, TEXTURESIZE*2, TEXTURESIZE*2),
            TextureRegion(atlas_texture, TEXTURESIZE*5, TEXTURESIZE*6, TEXTURESIZE*2, TEXTURESIZE*2)

        )
    )
    fun getMonsterTexture(id:Byte):TextureRegion=
        when(id){
            MonsterSprites.SKELETON_IDLE1.id-> monsters_region[0]
            MonsterSprites.SKELETON_IDLE2.id-> monsters_region[1]
            MonsterSprites.SKELETON_IDLE3.id->monsters_region[2]
            MonsterSprites.SKELETON_WALK1.id->monsters_region[3]
            MonsterSprites.SKELETON_WALK2.id->monsters_region[4]
            MonsterSprites.SKELETON_WALK3.id->monsters_region[5]
            MonsterSprites.GOBLIN_IDLE1.id->monsters_region[6]
            MonsterSprites.GOBLIN_IDLE2.id->monsters_region[7]
            MonsterSprites.GOBLIN_IDLE3.id->monsters_region[8]
            MonsterSprites.GOBLIN_WALK1.id->monsters_region[9]
            MonsterSprites.GOBLIN_WALK2.id->monsters_region[10]
            MonsterSprites.GOBLIN_WALK3.id->monsters_region[11]
            MonsterSprites.GOLEM_IDLE1.id->monsters_region[12]
            MonsterSprites.GOLEM_IDLE2.id->monsters_region[13]
            MonsterSprites.GOLEM_IDLE3.id->monsters_region[14]
            MonsterSprites.GOLEM_WALK1.id->monsters_region[15]
            MonsterSprites.GOLEM_WALK2.id->monsters_region[16]
            MonsterSprites.GOLEM_WALK3.id->monsters_region[17]
            else -> items_region[0]
        }

    fun getGui(name: String): TextureRegion =
        when (name) {
            GuiName.INSPECT._name -> gui_region[0]
            GuiName.INVENTORY._name -> gui_region[1]
            GuiName.HEALTH_BAR._name -> gui_region[2]
            GuiName.START_BUTTON._name -> gui_region[3]
            GuiName.SKIP_BUTTON._name -> gui_region[4]
            GuiName.INVENTORY_BUTTON._name-> gui_region[5]
            "selected" -> gui_region[6]
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
            Textures.END_GATE.id -> atlasRegion[9]
            else -> {
                getItemTexture(id)
            }
        }

    fun getPlayerTexture(id:Byte):TextureRegion=
        when (id) {
            PlayerSprites.IDLE1.id -> player_region[0]
            PlayerSprites.IDLE2.id -> player_region[1]
            PlayerSprites.IDLE3.id -> player_region[2]
            PlayerSprites.WALK1.id -> player_region[3]
            PlayerSprites.WALK2.id -> player_region[4]
            PlayerSprites.WALK3.id -> player_region[5]
            else -> items_region[0]
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
            ItemsSprites.SWORD_TINT1.id -> items_region[25]
            ItemsSprites.SWORD_TINT2.id -> items_region[26]
            ItemsSprites.SWORD_TINT3.id -> items_region[27]
            ItemsSprites.MACE_TINT1.id -> items_region[28]
            ItemsSprites.MACE_TINT2.id -> items_region[29]
            ItemsSprites.MACE_TINT3.id -> items_region[30]
            ItemsSprites.AXE_TINT1.id -> items_region[31]
            ItemsSprites.AXE_TINT2.id -> items_region[32]
            ItemsSprites.AXE_TINT3.id -> items_region[33]
            ItemsSprites.ARMOR_TINT1.id -> items_region[34]
            ItemsSprites.ARMOR_TINT2.id -> items_region[35]
            ItemsSprites.ARMOR_TINT3.id -> items_region[36]
            ItemsSprites.BOOTS_TINT1.id -> items_region[37]
            ItemsSprites.BOOTS_TINT2.id -> items_region[38]
            ItemsSprites.BOOTS_TINT3.id -> items_region[39]
            else -> getMonsterTexture(id)
        }
}