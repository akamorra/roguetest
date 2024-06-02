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
        var edgesList: MutableSet<edge> = mutableSetOf()
        override fun toString(): String {
            return "S=" + s + " "
        }

        override fun equals(other: Any?): Boolean {
            return hashCode() == other.hashCode()
        }

        override fun hashCode(): Int {
            return s.hashCode()
        }

        class edge(var start: Vertex, var end: Vertex) {
            override fun equals(other: Any?): Boolean {

                return other.hashCode() == this.hashCode()
            }

            override fun hashCode(): Int {
                return this.toString().toInt()
            }

            override fun toString(): String {
                return start.s + end.s
            }
        }
    }

    private val returnPaired: pairedBooleanMutableSet = pairedBooleanMutableSet(
        true,
        mutableSetOf()
    )

    class pairedBooleanMutableSet(var bool: Boolean, var set: MutableSet<Vertex>)

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

            v1.edgesList.add(Vertex.edge(v1, v2))
            v2.edgesList.add(Vertex.edge(v1, v2))
        
    }

    @Suppress("SuspiciousIndentation")
    fun connect(s1: String, s2: String) {
        if (s1 != s2) connect(data[s1]!!, data[s2]!!)
    }

    fun neighbors(s: String) = data[s]?.neighbors?.map { it } ?: emptySet<Vertex>()

    fun deepFirstSearch(s1: String, visited: MutableSet<Vertex>): pairedBooleanMutableSet {
        if (data.contains(s1) && !visited.contains(data[s1])) {
            visited.add(data[s1]!!)
            data[s1]!!.neighbors.forEach {
                connect(it,data[s1]!!)
                deepFirstSearch(it.s, visited)
            }
        }
        returnPaired.bool = visited.count() == data.count()
        returnPaired.set = visited
        return returnPaired
    }

    fun check() {
        var flag = true
        while (flag) {
                flag = false
                data.forEach {
                        if (it.value.neighbors.count() <= 3) {
                            if (!deepFirstSearch(it.key, mutableSetOf()).bool) {
                                connectToClosest(returnPaired.set.last())
                                flag = true
                                if (flag) return@forEach
                            }
                        }
                }
        }

    }

    private fun connectToClosest(v: Vertex): Boolean {
        if (!data.isNullOrEmpty()) {
            var min = 999999.0f
            var minKey = v.s
            var flag = true
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
            if (minKey != v.s && !v.neighbors.contains(data[minKey]) && data[minKey]!!.neighbors.count() <= 4 && v.neighbors.count() <= 2) {
                connect(v.s, minKey)
                return true
            } else {
                if (v.s == minKey || v.neighbors.count() >= 2) {
                    return false
                }
            }

        }

        return false
    }
}
