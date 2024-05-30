package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import kotlin.random.Random

data class Room(var width: Int, var height: Int) {
    var listOfDoors: MutableSet<Tile.Index> = mutableSetOf()
    private var center: Vector2 = Vector2(0.0f, 0.0f)
    private var centerIndex: Tile.Index = Tile.Index(0, 0)
    companion object{
        val generator:Room=Room(1,1)
    }
    private var src: Array<Array<Tile>> =
        Array(width) { Array(height) { Tile(Vector2(1.0f, 1.0f), 1, Tile.Index(0, 0), false) } }

    fun getCenter(): Vector2 = center


    fun getSrc() = src

    fun print() {
        for (it in src) {
            for (it1 in it) {
                System.out.print(it1.renderPos.toString())
            }
            System.out.println()
        }
    }

    fun getNextRandom(w: Int,h: Int) : Room {
        width = Random.nextInt(2, w) + w
        height = Random.nextInt(2,h) + h
        var temp:Room=Room(width,height)
        temp.fill()
        return temp
    }
    fun fillW(){

        if(Textures.listOfTransparents.contains(src[0][0].textureID)) src[0][0].textureID=Textures.STONEWALL_TRANSPARENT.id
        if(Textures.listOfTransparents.contains(src[0][src[0].lastIndex].textureID)) src[0][src[0].lastIndex].textureID=Textures.STONEWALL_TRANSPARENT.id
        if(Textures.listOfTransparents.contains(src[src.lastIndex][0].textureID)) src[src.lastIndex][0].textureID=Textures.STONEWALL_TRANSPARENT.id
        if(Textures.listOfTransparents.contains(src[src.lastIndex][src[0].lastIndex].textureID)) src[src.lastIndex][src[0].lastIndex].textureID=Textures.STONEWALL_TRANSPARENT.id

    }
     private fun fill() {
        for (it in src) {
            for (it1 in it) {
                it1.textureID = (Random.nextInt(3) + 1).toByte()
                it1.isObstacle = false
            }
        }
        for (it in 0..<src.size) {
            if(src[it][0].textureID!=Textures.DOOR.id)
            src[it][0].textureID = Textures.STONEWALL.id; src[it][0].isObstacle = true
        }
        for (it in 1..<src.size) {
            if(src[it][src[0].lastIndex].textureID!=Textures.DOOR.id)
            src[it][src[0].lastIndex].textureID =
                Textures.STONEWALL_TRANSPARENT_W.id; src[it][src[0].lastIndex].isObstacle = true
        }
        for (it in 0..<src[0].size) {
            if(src[0][it].textureID !=Textures.DOOR.id)
            src[0][it].textureID = Textures.STONEWALL.id; src[0][it].isObstacle = true
        }
        for (it in 1..<src[0].size) {
            if(src[src.lastIndex][it].textureID!=Textures.DOOR.id)src[src.lastIndex][it].textureID =
                Textures.STONEWALL_TRANSPARENT_E.id; src[src.lastIndex][it].isObstacle = true
        }
        src[src.lastIndex][src[0].lastIndex].textureID = Textures.STONEWALL_TRANSPARENT.id
        src[src.lastIndex][src[0].lastIndex].isObstacle = true
    }

    fun setCenter() {
        if ((src.lastIndex + 1) % 2 > 0) {
            centerIndex.x = src[(src.lastIndex) / 2][0].index.x
        } else {
            centerIndex.x = src[(src.lastIndex) / 2][0].index.x
        }
        if ((src[0].lastIndex + 1) % 2 > 0) {
            centerIndex.y = src[0][(src[0].lastIndex) / 2].index.y
        } else {
            centerIndex.y = src[0][(src[0].lastIndex) / 2].index.y
        }
        center.x = getFromIndex(centerIndex).renderPos.x + MapCFG.TILESIZE / 2
        center.y = getFromIndex(centerIndex).renderPos.y + MapCFG.TILESIZE / 2 - 28
    }

    fun getFromIndex(index: Tile.Index): Tile {
        for (it in src) {
            for (it1 in it) {
                if ((index.x == it1.index.x) && (index.y == it1.index.y))
                    return it1
            }
        }
        return src[0][0]
    }

    fun getDoor(room:Room):Tile{
        var connectToCenter = room.getCenter()
        var minusX = center.x < connectToCenter.x
        var minusY = center.y < connectToCenter.y
        var direction: Int = 0
        if (minusX && minusY) direction = 2
        if (!minusX && minusY) direction = 1
        if (!minusX && !minusY) direction = 3
        if (minusX && !minusY) direction = 4
        var door=src[0][0]
        when (direction) {
            1 -> {
                for(i in 1..<src[0].size-1){
                    if(src[0][i].textureID==Textures.DOOR.id)
                        door= src[0][i]
                }
            }

            2 -> {
                for(i in 1..<src.size-1){
                    if(src[i][0].textureID==Textures.DOOR.id)
                        door= src[i][0]
                }
            }

            3 -> {
                for(i in 1..<src.size-1){
                    if(src[i][src[0].lastIndex].textureID==Textures.DOOR.id)
                        door= src[i][src[0].lastIndex]
                }
            }

            4 -> {
                for(i in 1..<src[0].size-1){
                    if(src[src.lastIndex][i].textureID==Textures.DOOR.id)
                        door= src[src.lastIndex][i]
                }
            }
        }
        return door
    }
    fun makeDoor(room: Room) {
        var pointer: Int = 0
        var connectToCenter = room.getCenter()
        var minusX = center.x < connectToCenter.x
        var minusY = center.y < connectToCenter.y
        var direction: Int = 0
        if (minusX && minusY) direction = 2
        if (!minusX && minusY) direction = 1
        if (!minusX && !minusY) direction = 3
        if (minusX && !minusY) direction = 4
        when (direction) {
            1 -> {
                var flag=false
                for(i in 0..<src[0].size){
                    flag=src[0][i].textureID==Textures.DOOR.id
                    if (flag)break
                }
                if (!flag) {
                    pointer = Random.nextInt(3, src[0].size - 2)
                    src[0][pointer + 1].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[0][pointer].isObstacle = false
                    src[0][pointer].textureID = Textures.DOOR.id
                    //
                    listOfDoors.add(src[0][pointer].index)
                }

            }

            2 -> {
                var flag=false
                for(i in 0..<src.size){
                    flag=src[i][0].textureID==Textures.DOOR.id
                    if (flag)break
                }
                if (!flag) {
                    pointer = Random.nextInt(3, src.size - 2)
                    src[pointer + 1][0].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[pointer][0].textureID = Textures.DOOR.id
                    src[pointer][0].isObstacle = false
                    //
                    listOfDoors.add(src[pointer][0].index)
                }
            }

            3 -> {
                var flag=false
                for(i in 0..<src.size){
                    flag=src[i][src[0].lastIndex].textureID==Textures.DOOR.id
                    if (flag)break
                }
                if (!flag) {
                    pointer = Random.nextInt(1, src.size - 2)
                    src[pointer - 1][src[0].lastIndex].textureID =
                        Textures.STONEWALL_TRANSPARENT.id
                    src[pointer][src[0].lastIndex].isObstacle = false
                    src[pointer][src[0].lastIndex].textureID = Textures.DOOR.id
                    listOfDoors.add(src[pointer][src[0].lastIndex].index)
                }
            }

            4 -> {
                var flag=false
                for(i in 0..<src[0].size){
                    flag=src[src.lastIndex][i].textureID==Textures.DOOR.id
                    if (flag)break
                }
                if (!flag) {
                    pointer = Random.nextInt(3, src[0].size - 2)
                    src[src.lastIndex][pointer + 1].textureID =
                        Textures.STONEWALL_TRANSPARENT.id
                    src[src.lastIndex][pointer - 1].textureID =
                        Textures.STONEWALL_TRANSPARENT.id
                    src[src.lastIndex][pointer].isObstacle = false
                    src[src.lastIndex][pointer].textureID = Textures.DOOR.id
                    listOfDoors.add(src[src.lastIndex][pointer].index)
                }
            }
        }
    }
}