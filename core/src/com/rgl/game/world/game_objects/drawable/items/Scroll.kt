package com.rgl.game.world.game_objects.drawable.items

import com.badlogic.gdx.graphics.g2d.Batch
import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.drawable.items.parents.Item
import com.rgl.game.world.game_objects.drawable.items.parents.ItemType
import com.rgl.game.world.game_objects.drawable.player.Player
import kotlin.random.Random

class Scroll : Item() {
    override fun renderInv(batch: Batch, x: Float, y: Float, player: Player) {
        batch.draw(
            TextureRepo.getItemTexture(textureID),renderPos.x,renderPos.y,
            MapCFG.ITEM_INVENTORY_WIDTH, MapCFG.ITEM_INVENTORY_HEIGHT
        )
    }
    init{
        itemType= ItemType.SCROLL
        tintTextureID= Textures.EMPTY_TEXTURE.id
        var rand = Random.nextInt(0, 2)
        when (rand) {
            1 -> textureID = ItemsSprites.SCROLL.id
            else -> textureID = ItemsSprites.SCROLL.id
        }
    }

    override fun toString(): String {

        return "ItemType: Scroll "+super.toString()
    }


}