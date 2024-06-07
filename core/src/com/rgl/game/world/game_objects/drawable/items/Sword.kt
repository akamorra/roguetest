package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Weapon
import kotlin.random.Random

class Sword: Weapon(){

    init {
        var rand = Random.nextInt(0, 4)
        when (rand) {
            1 -> textureID = ItemsSprites.SWORD1.id
            2 -> textureID = ItemsSprites.SWORD2.id
            3 -> textureID = ItemsSprites.SWORD3.id
            else -> textureID = ItemsSprites.SWORD1.id
        }
    }

    override fun toString(): String {

        return "ItemType: Sword "+super.toString()
    }

    override fun generateRequirements(playerLevel: Int) {
        //
    }
}