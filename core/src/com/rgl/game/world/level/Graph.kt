package com.rgl.game.world.level

import com.badlogic.gdx.graphics.Color
import com.rgl.game.graphics.Colors
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Graph {
    data class Vertex(val s: String, val room: Room) {
        var neighbors = mutableSetOf<Vertex>()
        var clr= Random.nextInt(7)-1
        var color:Color=Color()
        var listOfClosests = mutableSetOf<Vertex>()
    }

    private val data = mutableMapOf<String, Vertex>()

    operator fun get(s: String) = data[s]
    fun get() = data
    fun add(s: String, room: Room) {
        data[s] = Vertex(s, room)
        data[s]!!.clr=Random.nextInt(7)-1
        data[s]!!.color=
            when (data[s]!!.clr){
                Colors.RED.i -> Color.RED
                Colors.BLUE.i -> Color.BLUE
                Colors.CYAN.i -> Color.CYAN
                Colors.FOREST.i -> Color.FOREST
                Colors.GOLD.i -> Color.GOLD
                Colors.MAGENTA.i -> Color.MAGENTA
                else -> Color.WHITE
            }
    }

    private fun connect(v1: Vertex, v2: Vertex) {
        v1.neighbors.add(v2)
        v2.neighbors.add(v1)
    }

    @Suppress("SuspiciousIndentation")
    fun connect(s1: String, s2: String) {
        if (s1 != s2)
            connect(data[s1]!!, data[s2]!!)
    }

    fun neighbors(s: String) = data[s]?.neighbors?.map { it } ?: emptySet<Vertex>()
    fun getNeighbors(s: String): Boolean {
        return !data[s]?.neighbors.isNullOrEmpty()
    }

    fun isConnected(s1: String, s2: String) = neighbors(s1).contains(data[s2])
    fun deepFirstSearch(s1: String, visited: Set<Vertex>): Boolean {
        if (!getNeighbors(s1) && data.contains(s1)) {
            visited.plusElement(data[s1])
            data[s1]!!.neighbors.forEach {
                deepFirstSearch(it.s, visited)
            }
        }
        return visited.count() == data.count()
    }

    fun setClosests(v: Vertex, a: Int) {
        var minKey = "0"
        var min = 99999999.0f
        if (!data.isNullOrEmpty()) {
            while (v.listOfClosests.count() <a) {
                System.out.println("v.neighbors:" + v.neighbors.count())
                System.out.println("v.closests:" + v.listOfClosests.count())
                data.forEach { entry ->
                    var min2 = sqrt(
                        (entry.value.room.getCenter().x - v.room.getCenter().x).pow(2) + (entry.value.room.getCenter().y - v.room.getCenter().y).pow(
                            2
                        )
                    )
                    if (!v.listOfClosests.contains(entry.value) && v != entry.value) {
                        if (min2 < min)
                            if (!v.listOfClosests.contains(data[entry.key])) {
                                min = min2
                                minKey = entry.key
                            }
                    }

                }
                if (!v.listOfClosests.contains(data[minKey]) && v != data[minKey] && v.s != minKey
                ) {
                    v.listOfClosests.add(data[minKey]!!)
                    connect(v, data[minKey]!!)
                }
                min = 9999999.0f
            }


        }
        System.out.println("List:" + v.listOfClosests.toString())
        System.out.println("ListN:" + v.neighbors.toString())
    }
    fun check(){
        data.forEach {
            if (!deepFirstSearch(it.key, setOf())) {
                connectToClosest(it.value)
            }
        }
    }

    private fun connectToClosest(v: Vertex): Boolean {
        if (!data.isNullOrEmpty()) {
            var min = 999999.0f
            var minKey = v.s
            while (true) {
                data.forEach { entry ->

                    if (!v.neighbors.contains(entry.value) && v != entry.value) {
                        var min2 = sqrt(
                            (entry.value.room.getCenter().x - v.room.getCenter().x).pow(2) + (entry.value.room.getCenter().y - v.room.getCenter().y).pow(
                                2
                            )
                        )
                        if (min2 < min)
                            if (!v.neighbors.contains(data[entry.key])) {
                                min = min2
                                minKey = entry.key
                            }
                    }
                }
                if (minKey != v.s && !v.neighbors.contains(data[minKey])) {
                    connect(v.s, minKey)
                    return true
                } else {
                    if (v.s == minKey) {
                        return false
                    }
                }

            }
        }
        return false
    }
}
