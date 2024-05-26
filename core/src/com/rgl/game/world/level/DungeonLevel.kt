package com.rgl.game.world.level

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
    private var listOfRooms: ArrayList<Room> = ArrayList()
    private var spawned: Boolean = false
    private var breakFlag: Boolean = false

    fun getDungeonLevel(rooms: Int): Array<Array<Tile>> {
        clearData()
        addRooms(rooms)
        setIndexes()
        setRenderPoses()
        return data
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

    private fun addRooms(rooms: Int) {
        amountOfRooms = Random.nextInt(rooms) + rooms
        for (it in 0..<rooms) {
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
            it.print()
        }
    }

}