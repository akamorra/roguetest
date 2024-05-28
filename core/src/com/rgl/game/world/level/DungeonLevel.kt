package com.rgl.game.world.level

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.rgl.game.world.MapCFG
import kotlin.random.Random

object DungeonLevel {
    private var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    private var pointer: Int = 0
    private var pointerX: Int = 0
    private var pointerY: Int = 0
    private var data: Array<Array<Tile>> =
        Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 0, Tile.Index(0, 0), false) } }
    private var amountOfRooms: Int = 0
    private var amountOfConnections: Int = 0
    private var chance: Int = 1
    private var listOfRooms: ArrayList<Room> = ArrayList()
    private var graph: Graph = Graph()
    private var spawned: Boolean = false
    private var breakFlag: Boolean = false

    fun getDungeonLevel(rooms: Int): Array<Array<Tile>> {
        clearData()
        addRooms(rooms)
        setIndexes()
        setRenderPoses()
        setCenters()
        addToGraph()
        makeConnections()
        prepare()
        return data
    }

    private fun addToGraph() {
        for (i in 0..listOfRooms.size - 1) graph.add(i.toString(), listOfRooms[i])
        graph.get().forEach { entry -> System.out.println(entry.toString() + "\n") }
    }

    fun prepare() {
        graph.get().forEach { entry ->
            entry.value.color = Color(Color.RED)
        }
    }

    fun render(batch: ShapeRenderer) {
        graph.get().forEach { entry ->
            for (it in graph.neighbors(entry.key)) {
                batch.color = entry.value.color
                batch.line(entry.value.room.getCenter(), it.room.getCenter())
            }
        }
    }

    private fun clearData() {
        data = Array(MapCFG.MAPSIZE) {
            Array(MapCFG.MAPSIZE) {
                Tile(
                    Vector2(1.0f, 1.0f),
                    0,
                    Tile.Index(0, 0),
                    false
                )
            }
        }
        for (it in data) {
            for (it1 in it) {
                it1.isObstacle = true
            }
        }
    }

    private fun print() {
        for (it in listOfRooms) System.out.println(it.toString())
    }

    private fun addRooms(rooms: Int) {
        amountOfRooms = Random.nextInt(rooms) + rooms
        for (it in 0..rooms) {
            listOfRooms.add(Room(6, 6))
        }
        for (it in listOfRooms) {
            spawned = false
            while (!spawned) {
                breakFlag = false
                pointerX = Random.nextInt(
                    MapCFG.MAPSIZE / 2 - amountOfRooms * 2,
                    MapCFG.MAPSIZE / 2 + amountOfRooms * 2
                )
                pointerY = Random.nextInt(
                    MapCFG.MAPSIZE / 2 - amountOfRooms * 2,
                    MapCFG.MAPSIZE / 2 + amountOfRooms * 2
                )
                if (pointerX < 0 || pointerX > MapCFG.MAPSIZE) pointerX =
                    Random.nextInt(MapCFG.MAPSIZE)
                if (pointerY < 0 || pointerY > MapCFG.MAPSIZE) pointerY =
                    Random.nextInt(MapCFG.MAPSIZE)
                if ((pointerX + it.getSrc().size) > data.size) pointerX -= (pointerX + it.getSrc().size) - data.size
                if ((pointerY + it.getSrc()[0].size) > data[0].size) pointerY -= (pointerY + it.getSrc()[0].size) - data[0].size
                for (i in 0..<it.getSrc().size + 2) {
                    if (!breakFlag) for (j in 0..<it.getSrc()[0].size + 2) {
                        if (data[pointerX + i - 1][pointerY + j - 1].textureID.toInt() != 0) {
                            breakFlag = true
                            break
                        }
                    }
                }
                if (!breakFlag) spawned = true
            }

            for (i in 0..<it.getSrc().size) {
                for (j in 0..<it.getSrc()[0].size) {
                    it.getSrc()[i][j].index.x = pointerX + i
                    it.getSrc()[i][j].index.y = pointerY + j
                    data[pointerX + i][pointerY + j] = it.getSrc()[i][j]
                }
            }
        }
    }

    private fun setIndexes() {
        for (it in data) {
            for (it1 in it) {
                it1.index = Tile.Index(
                    data.indexOf(
                        it
                    ), it.indexOf(it1)
                )
            }
        }
    }

    private fun setRenderPoses() {
        renderPos.x = 0.0f
        renderPos.y = 0.0f
        pointer = 0
        for (it in data) {
            for (it1 in it) {
                renderPos.x -= MapCFG.TILESIZE / 2
                renderPos.y -= (MapCFG.TILESIZE / 2 - MapCFG.TILESIZE / 6) + 5
                it1.renderPos.x = renderPos.x
                it1.renderPos.y = renderPos.y
            }
            pointer++
            renderPos.y = 0 - pointer * (MapCFG.TILESIZE / 2 - MapCFG.TILESIZE / 6 + 5)
            renderPos.x = 0 + pointer * MapCFG.TILESIZE / 2
        }

        for (it in listOfRooms) {
            for (it1 in it.getSrc()) {
                for (it2 in it1) {
                    it2.renderPos.x = data[it2.index.x][it2.index.y].renderPos.x
                    it2.renderPos.y = data[it2.index.x][it2.index.y].renderPos.y
                    it2.isDrawable = true
                }
            }
        }
    }

    private fun setCenters() { //определение центров комнат
        for (it in listOfRooms) {
            it.setCenter()
        }
    }

    private fun makeConnections() {
        graph.check()
    }
}
