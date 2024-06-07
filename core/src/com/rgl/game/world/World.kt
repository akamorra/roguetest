package com.rgl.game.world

import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.level.Level

//World - Singleton класс для хранения данных текущего игрового мира
object World {
    private val data = mutableListOf<Level>()
    var CURRENTLVL:Int =0
    fun getWorld(): MutableList<Level> {
        return data
    }

    fun getLevel(): Level {

        return data.last()
    }

    fun addLevel(player: Player) {
        var lvl = Level(player)
        data.add(lvl.getNew(MapCFG.ROOMCOUNT))
    }

    fun loadSaved(UUID: String) { //Загрузка сохраненного мира и

    }

    fun clear() {
        data.clear()
        CURRENTLVL=0
    }
}