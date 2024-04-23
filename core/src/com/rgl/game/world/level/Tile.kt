package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.graphics.Textures
import com.rgl.game.world.game_objects.Drawable

class Tile(override var pos: Vector2, override var textureID: Byte) : TileInterface, Drawable {
    companion object {
        const val TILESIZE: Float = 256.0f
    }

    override fun render(batch: Batch, x: Float, y: Float) {
        when (textureID) {
            Textures.GRASS.id, Textures.STONEWALL.id, Textures.STONE.id -> batch.draw(
                TextureRepo.getTexture(
                    textureID
                ), x, y, TILESIZE, TILESIZE
            )
        }

    }

}