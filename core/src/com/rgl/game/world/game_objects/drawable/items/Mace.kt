package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Weapon
import kotlin.random.Random

class Mace :Weapon(){

    init {
        var rand = Random.nextInt(0, 4)
        when (rand) {
            1 -> textureID = ItemsSprites.MACE1.id
            2 -> textureID = ItemsSprites.MACE2.id
            3 -> textureID = ItemsSprites.MACE3.id
            else -> textureID = ItemsSprites.MACE1.id
        }
    }

    override fun toString(): String {

        return "ItemType: Mace "+super.toString()
    }

    override fun generateRequirements(playerLevel: Int) {
        //
    }
}