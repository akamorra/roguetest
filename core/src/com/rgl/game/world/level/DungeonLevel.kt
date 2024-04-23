package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2
import kotlin.random.Random

object DungeonLevel {
    private var pointerX: Int = 0
    private var pointerY: Int = 0
    private var data: Array<Array<Tile>> =
        Array(100) { Array(100) { Tile(Vector2(1.0f, 1.0f), 0) } }
    private var amountOfRooms: Int = 0
    private var listOfRooms: ArrayList<Array<Array<Tile>>> = ArrayList()
    private var spawned: Boolean = false;
    private var breakFlag: Boolean = false;
    private var attemptsToSpawn:Int=0;

    fun getDungeonLevel(rooms: Int): Array<Array<Tile>> {
        clearData()
        amountOfRooms = Random.nextInt(rooms) + rooms
        for (it in 0..<rooms) {
            listOfRooms.add(Room.getNext())
        }
        for (it in listOfRooms) {
            spawned = false;
            attemptsToSpawn=0;
            while (!spawned && attemptsToSpawn!=5) {
                attemptsToSpawn++
                breakFlag = false;
                pointerX = Random.nextInt(100)
                pointerY = Random.nextInt(100)
                System.out.println("$pointerX $pointerY")
                if ((pointerX + it.size) > data.size) pointerX -= (pointerX + it.size) - data.size
                if ((pointerY + it[0].size) > data[0].size) pointerY -= (pointerY + it[0].size) - data[0].size
                for (i in 0..<it.size) {
                    if (!breakFlag) for (j in 0..<it[0].size) {
                        if (data[pointerX + i][pointerY + j].textureID.toInt() != 0) {
                            breakFlag = true;
                            break;
                        }
                    }
                }
                if (!breakFlag) spawned = true;
            }
            if(spawned) {
                for (i in 0..<it.size) {
                    for (j in 0..<it[0].size) {
                        data[pointerX + i][pointerY + j] = it[i][j];
                    }
                }
            }

        }
        return data
    }

    private fun clearData() {
        data = Array(100) { Array(100) { Tile(Vector2(1.0f, 1.0f), 0) } }
    }
}