package com.rgl.game.world.level

import com.rgl.game.world.game_objects.drawable.items.parents.Item

class ObjectsManager {
    //Array

    //update
    //fun render;
    //dispose
    private var listOfItems = mutableMapOf<String, Item>()

    fun getItem(key:String):Item?{
        return listOfItems.get(key)
    }

    fun addItem(item:Item){
        listOfItems.put(item.key!!,item)
    }

    fun destroyItem(item: Item){
        listOfItems.remove(item.key,item)
    }

    fun getList(): MutableMap<String, Item>{
        return listOfItems
    }
}