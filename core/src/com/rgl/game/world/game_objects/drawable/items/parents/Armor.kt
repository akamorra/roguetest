package com.rgl.game.world.game_objects.drawable.items.parents


abstract class Armor : Item(),Requirements{
    private val isRevealed: Boolean = false

    init {
        itemType = ItemType.ARMOR

    }
}