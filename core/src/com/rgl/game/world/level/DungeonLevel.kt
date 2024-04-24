package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2
import com.rgl.game.world.MapCFG
import kotlin.random.Random

object DungeonLevel {
    private var renderPos:Vector2 = Vector2(0.0f,0.0f)
    private var pointer:Int=0
    private var pointerX: Int = 0
    private var pointerY: Int = 0
    private var data: Array<Array<Tile>> =
        Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 0, Tile.Index(0,0)) } }
    private var amountOfRooms: Int = 0
    private var listOfRooms: ArrayList<Array<Array<Tile>>> = ArrayList()
    private var spawned: Boolean = false
    private var breakFlag: Boolean = false
    private var attemptsToSpawn:Int=0

    fun getDungeonLevel(rooms: Int): Array<Array<Tile>> {
        clearData()
        addRooms(rooms)
        setIndexes()
        setRenderPoses()
        return data
    }

    private fun clearData() {
        data = Array(MapCFG.MAPSIZE) { Array(MapCFG.MAPSIZE) { Tile(Vector2(1.0f, 1.0f), 0,Tile.Index(0,0)) } }
    }

    private fun addRooms(rooms:Int){
        amountOfRooms = Random.nextInt(rooms) + rooms
        for (it in 0..<rooms) {
            listOfRooms.add(Room.getNext())
        }
        for (it in listOfRooms) {
            spawned = false
            attemptsToSpawn=0
            while (!spawned && attemptsToSpawn!=5) {
                attemptsToSpawn++
                breakFlag = false
                pointerX = Random.nextInt(MapCFG.MAPSIZE/2- amountOfRooms*2,MapCFG.MAPSIZE/2+amountOfRooms*2)
                pointerY = Random.nextInt(MapCFG.MAPSIZE/2- amountOfRooms*2,MapCFG.MAPSIZE/2+amountOfRooms*2)
                if(pointerX<0||pointerX>MapCFG.MAPSIZE) pointerX=Random.nextInt(MapCFG.MAPSIZE)
                if(pointerY<0||pointerY>MapCFG.MAPSIZE) pointerY=Random.nextInt(MapCFG.MAPSIZE)
                if ((pointerX + it.size) > data.size) pointerX -= (pointerX + it.size) - data.size
                if ((pointerY + it[0].size) > data[0].size) pointerY -= (pointerY + it[0].size) - data[0].size
                for (i in 0..<it.size) {
                    if (!breakFlag) for (j in 0..<it[0].size) {
                        if (data[pointerX + i][pointerY + j].textureID.toInt() != 0) {
                            breakFlag = true
                            break
                        }
                    }
                }
                if (!breakFlag) spawned = true
            }
            if(spawned) {
                for (i in 0..<it.size) {
                    for (j in 0..<it[0].size) {
                        data[pointerX + i][pointerY + j] = it[i][j]
                    }
                }
            }

        }
    }

    private fun setIndexes(){
        for (it in data){
            for (it1 in it) {
                it1.index = Tile.Index(
                    data.indexOf(
                    it),it.indexOf(it1))
            }
        }
    }

    private fun setRenderPoses(){
        renderPos.x = 0.0f
        renderPos.y = 0.0f
        pointer = 0
        for (it in data) {
            for (it1 in it) {
                renderPos.x -= MapCFG.TILESIZE / 2
                renderPos.y -= (MapCFG.TILESIZE / 2 - MapCFG.TILESIZE / 8)
                it1.renderPos.x= renderPos.x
                it1.renderPos.y= renderPos.y
            }
            pointer++
            renderPos.y = 0 - pointer * (MapCFG.TILESIZE / 2 - MapCFG.TILESIZE / 8)
            renderPos.x = 0 + pointer * MapCFG.TILESIZE / 2
        }
    }
}