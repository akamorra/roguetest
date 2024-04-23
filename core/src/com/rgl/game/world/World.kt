package com.rgl.game.world

import com.rgl.game.world.level.Level
import com.rgl.game.world.level.Tile

//World - Singleton класс для хранения данных текущего игрового мира
object World {
    private val data = mutableListOf<Array<Array<Tile>>>()

    fun getWorld(): MutableList<Array<Array<Tile>>> {
        return data
    }

    fun getLevel(): Array<Array<Tile>> {
        Level.data = data[data.lastIndex]
        return data[data.lastIndex]
    }

    fun addLevel(level: Level) {
        data.add(Level.get(30+data.lastIndex+5))
    }

    fun loadSaved(seed: String) { //Загрузка сохраненного мира и

    }
    fun clear(){
        data.clear()
    }
}