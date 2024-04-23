package com.rgl.game.graphics

import com.badlogic.gdx.graphics.Texture

object TextureRepo {
    private val texture:ArrayList<Texture> = ArrayList(listOf(
        Texture("test-tile.png"),
        Texture("test-tile1.png"),
        Texture("test-tile2.png")
    ))
    fun dispose() {
        for(it in texture) it.dispose()
    }

    fun getTexture(id: Byte): Texture =
        when (id) {
            Textures.STONE.id -> texture[0]
            Textures.GRASS.id -> texture[1]
            Textures.STONEWALL.id->texture[2]
            else -> {
                texture[0]
            }
        }

}