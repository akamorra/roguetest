package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Armor
import kotlin.random.Random

class Chestplate : Armor() {

    init {
        var rand = Random.nextInt(0, 4)
        when (rand) {
            1 -> textureID = ItemsSprites.CHESTPLATE1.id
            2 -> textureID = ItemsSprites.CHESTPLATE2.id
            3 -> textureID = ItemsSprites.CHESTPLATE3.id
            else -> textureID = ItemsSprites.CHESTPLATE1.id
        }
    }

    override fun toString(): String {

        return "ItemType: Chestplate "+super.toString()
    }

    override fun generateRequirements(playerLevel: Int) {
        //
    }

}