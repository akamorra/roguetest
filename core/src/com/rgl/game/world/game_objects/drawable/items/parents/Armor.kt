package com.rgl.game.world.game_objects.drawable.items.parents


abstract class Armor : Item(),Requirements{
    private val isRevealed: Boolean = false
    open var stats:MutableSet<Pair<ListOfStats,Int>> = mutableSetOf()
    init {
        itemType = ItemType.ARMOR

    }
    override fun getstatistics():MutableSet<Pair<ListOfStats,Int>> {

        return stats
    }
}