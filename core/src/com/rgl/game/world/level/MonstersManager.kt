package com.rgl.game.world.level

import com.rgl.game.world.game_objects.drawable.monsters.Monster


class MonstersManager {
    private var listOfItems = mutableMapOf<String, Monster>()

    fun getItem(key: String): Monster? {
        return listOfItems.get(key)
    }

    fun addItem(item: Monster) {
        listOfItems.put(item.key!!, item)
    }

    fun destroyItem(item: Monster) {
        listOfItems.remove(item.key, item)
    }

    fun getList(): MutableMap<String, Monster> {
        return listOfItems
    }

}