package com.rgl.game.world.level

import com.badlogic.gdx.graphics.Color
import com.rgl.game.graphics.Colors
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Graph {
    data class Vertex(val s: String, val room: Room) {
        var neighbors = mutableSetOf<Vertex>()
        var clr = Random.nextInt(7) - 1
        var color: Color = Color()
    }

    private val data = mutableMapOf<String, Vertex>()

    operator fun get(s: String) = data[s]
    fun get() = data
    fun add(s: String, room: Room) {
        data[s] = Vertex(s, room)
        data[s]!!.clr = Random.nextInt(7) - 1
        data[s]!!.color = when (data[s]!!.clr) {
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
        if (s1 != s2) connect(data[s1]!!, data[s2]!!)
    }

    fun neighbors(s: String) = data[s]?.neighbors?.map { it } ?: emptySet<Vertex>()

    fun deepFirstSearch(s1: String, visited: MutableSet<Vertex>): Boolean {
        if (data.contains(s1) && !visited.contains(data[s1])) {
            visited.add(data[s1]!!)
            data[s1]!!.neighbors.forEach {
                deepFirstSearch(it.s, visited)
            }
        }
        return visited.size == data.count()
    }

    fun check() {
        var flag = true
        data.forEach {
            if (!deepFirstSearch(it.key, mutableSetOf())) {
                connectToClosest(it.value)
            }
        }
        data.forEach {
            while (!deepFirstSearch(it.key, mutableSetOf())) {
                if (it.value.neighbors.count() > 2) {
                    break
                } else {
                    connectToClosest(it.value) && !deepFirstSearch(it.key, mutableSetOf())
                }
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
                        if (min2 < min) if (!v.neighbors.contains(data[entry.key])) {
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
