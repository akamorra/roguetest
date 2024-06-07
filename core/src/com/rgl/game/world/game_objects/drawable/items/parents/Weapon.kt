package com.rgl.game.world.game_objects.drawable.items.parents

abstract class Weapon : Item(), Requirements {
    private val isRevealed:Boolean=false
    init{
        itemType= ItemType.WEAPON

    }
}