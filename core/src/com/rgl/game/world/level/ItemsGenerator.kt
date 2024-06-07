package com.rgl.game.world.level

import com.rgl.game.world.game_objects.drawable.items.Axe
import com.rgl.game.world.game_objects.drawable.items.Boots
import com.rgl.game.world.game_objects.drawable.items.Chestplate
import com.rgl.game.world.game_objects.drawable.items.Mace
import com.rgl.game.world.game_objects.drawable.items.Pot
import com.rgl.game.world.game_objects.drawable.items.Scroll
import com.rgl.game.world.game_objects.drawable.items.Sword
import kotlin.random.Random

class ItemsGenerator {


    fun generate(
        equipmentAmount: Int,
        consumablesAmount: Int,
        objectsManager: ObjectsManager,
        graph: Graph
    ) {
        var setofspawned: MutableSet<Tile.Index> = mutableSetOf()
        var amountWeapons = Random.nextInt(0, equipmentAmount + 1)
        var amountArmor = equipmentAmount - amountWeapons
        var amountPots = Random.nextInt(0, consumablesAmount + 1)
        var amountScrolls = consumablesAmount - amountPots
        var row = 0
        var col = 0
        var rand = 0
        for (i in 1..amountWeapons) {
            rand = Random.nextInt(1, 4)
            when (rand) {
                1 -> objectsManager.addItem(Sword())
                2 -> objectsManager.addItem(Mace())
                3 -> objectsManager.addItem(Axe())
                else -> objectsManager.addItem(Scroll())
            }
        }
        for (i in 1..amountArmor) {
            rand = Random.nextInt(1, 3)
            when (rand) {
                1 -> objectsManager.addItem(Chestplate())
                2 -> objectsManager.addItem(Boots())
                else -> objectsManager.addItem(Sword())
            }
        }
        for (i in 1..amountPots) {
            rand = Random.nextInt(1, 4)
            objectsManager.addItem(Pot())
        }
        for (i in 1..amountScrolls) {
            objectsManager.addItem(Scroll())
        }
        objectsManager.getList().forEach {
            rand = Random.nextInt(0, graph.get().count())
            do {
                row = Random.nextInt(1, graph.get(rand.toString())!!.room.getSrc().size - 2)
                col = Random.nextInt(1, graph.get(rand.toString())!!.room.getSrc()[0].size - 2)
            } while (setofspawned.contains(graph.get(rand.toString())!!.room.getSrc()[row][col].index))
            it.value.spawn(graph.get(rand.toString())!!.room.getSrc()[row][col])
            setofspawned.add(graph.get(rand.toString())!!.room.getSrc()[row][col].index)
        }
    }
}