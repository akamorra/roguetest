package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Armor
import kotlin.random.Random

class Boots : Armor() {

    init {
        var rand = Random.nextInt(0, 4)
        when (rand) {
            1 -> textureID = ItemsSprites.BOOTS1.id
            2 -> textureID = ItemsSprites.BOOTS2.id
            3 -> textureID = ItemsSprites.BOOTS3.id
            else -> textureID = ItemsSprites.BOOTS1.id
        }
    }

    override fun toString(): String {

        return "ItemType: Boots "+super.toString()
    }

    override fun generateRequirements(playerLevel: Int) {
        //
    }
}