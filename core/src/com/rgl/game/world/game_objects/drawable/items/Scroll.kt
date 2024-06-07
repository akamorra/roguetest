package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Item
import com.rgl.game.world.game_objects.drawable.items.parents.ItemType
import kotlin.random.Random

class Scroll : Item() {

    init{
        itemType= ItemType.SCROLL
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