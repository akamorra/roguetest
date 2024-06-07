package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Item
import com.rgl.game.world.game_objects.drawable.items.parents.ItemType
import kotlin.random.Random

class Pot : Item() {
    init{
        itemType= ItemType.POT
        var rand = Random.nextInt(0, 5)
        when (rand) {
            1 -> textureID = ItemsSprites.POT1.id
            2 -> textureID = ItemsSprites.POT2.id
            3 -> textureID = ItemsSprites.POT3.id
            4 -> textureID = ItemsSprites.POT4.id
            else -> textureID = ItemsSprites.POT1.id
        }
    }

    override fun toString(): String {

        return "ItemType: Pot "+super.toString()
    }


}