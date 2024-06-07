package com.rgl.game.world.level

import com.rgl.game.world.game_objects.drawable.monsters.Golem
import com.rgl.game.world.game_objects.drawable.monsters.Ork
import com.rgl.game.world.game_objects.drawable.monsters.Skeleton
import com.rgl.game.world.game_objects.drawable.player.Player
import kotlin.random.Random

class MonstersSpawner {


    fun generate(
        amount: Int,
        monstersManager: MonstersManager,
        graph: Graph,
        player: Player,
        lvl: Level
    ) {
        var setofspawned: MutableSet<Tile.Index> = mutableSetOf()
        var row = 0
        var col = 0
        var rand = 0
        for (i in 1..amount) {
            rand=Random.nextInt(0,4)
            when (rand){
                1->monstersManager.addItem(Ork(player, lvl))
                    2->monstersManager.addItem(Skeleton(player, lvl))
                        3->monstersManager.addItem(Golem(player, lvl))
            }

        }

        monstersManager.getList().forEach {
            rand = Random.nextInt(0, graph.get().count())
            do {
                row = Random.nextInt(1, graph.get(rand.toString())!!.room.getSrc().size - 2)
                col = Random.nextInt(1, graph.get(rand.toString())!!.room.getSrc()[0].size - 2)
            } while (setofspawned.contains(graph.get(rand.toString())!!.room.getSrc()[row][col].index)||graph.get(rand.toString())!!.room.getSrc()[row][col].containsInstance)
            it.value.spawn(graph.get(rand.toString())!!.room.getSrc()[row][col])
            setofspawned.add(graph.get(rand.toString())!!.room.getSrc()[row][col].index)
        }
    }
}