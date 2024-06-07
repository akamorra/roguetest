package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.world.game_objects.drawable.items.parents.Weapon
import kotlin.random.Random

class Axe:Weapon(){

    init {
        var rand = Random.nextInt(0, 4)
        when (rand) {
            1 -> textureID = ItemsSprites.AXE1.id
            2 -> textureID = ItemsSprites.AXE2.id
            3 -> textureID = ItemsSprites.AXE3.id
            else -> textureID = ItemsSprites.AXE1.id
        }
    }

    override fun toString(): String {

        return "ItemType: Axe "+super.toString()
    }

    override fun generateRequirements(playerLevel: Int) {
        //
    }
}