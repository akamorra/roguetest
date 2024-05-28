package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import kotlin.random.Random

data class Room(var width: Int, var height: Int) {
    private var listOfDoors: ArrayList<Tile.Index> = ArrayList<Tile.Index>()
    private var center: Vector2 = Vector2(0.0f, 0.0f)
    private var centerIndex:Tile.Index=Tile.Index(0,0)
    private var amountOfDoors: Int = 0
    private var src: Array<Array<Tile>> =
        Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 1, Tile.Index(0, 0), false) } }

    fun getCenter(): Vector2 = center

    init {
        src = getNext()
    }

    fun getAmountOfConnections(): Int = listOfDoors.size

    private fun getNext(): Array<Array<Tile>> {
        getNextRandom()
        src =
            Array(width) { Array(height) { Tile(Vector2(1.0f, 1.0f), 1, Tile.Index(0, 0), false) } }
        fill()
        return src
    }

    fun getSrc() = src

    fun print() {
        for (it in src) {
            for (it1 in it) {
                System.out.print(it1.renderPos.toString())
            }
            System.out.println()
        }
    }

    private fun getNextRandom() {
        width = Random.nextInt(width) + 5
        height = Random.nextInt(height) + 5
    }

    private fun fill() {
        for (it in src) {
            for (it1 in it) {
                it1.textureID = (Random.nextInt(3) + 1).toByte()
                it1.isObstacle = false
            }
        }
        for (it in 0..<src.size) {
            src[it][0].textureID = Textures.STONEWALL.id; src[it][0].isObstacle = true
        }
        for (it in 1..<src.size) {
            src[it][src[0].lastIndex].textureID =
                Textures.STONEWALL_TRANSPARENT_W.id; src[it][src[0].lastIndex].isObstacle = true
        }
        for (it in 0..<src[0].size) {
            src[0][it].textureID = Textures.STONEWALL.id; src[0][it].isObstacle = true
        }
        for (it in 1..<src[0].size) {
            src[src.lastIndex][it].textureID =
                Textures.STONEWALL_TRANSPARENT_E.id; src[src.lastIndex][it].isObstacle = true
        }
        src[src.lastIndex][src[0].lastIndex].textureID = Textures.STONEWALL_TRANSPARENT.id
        src[src.lastIndex][src[0].lastIndex].isObstacle = true
    }

    fun setCenter() {
        if ((src.lastIndex + 1) % 2 > 0) {
            centerIndex.x=src[(src.lastIndex)/2-1][0].index.x
        } else {
            centerIndex.x=src[(src.lastIndex)/2][0].index.x
        }
        if ((src[0].lastIndex + 1) % 2 > 0) {
            centerIndex.y=src[0][(src[0].lastIndex)/2-1].index.y
        } else {
            centerIndex.y=src[0][(src[0].lastIndex)/2].index.y
        }
        center.x=getFromIndex(centerIndex).renderPos.x+MapCFG.TILESIZE/2
        center.y=getFromIndex(centerIndex).renderPos.y+MapCFG.TILESIZE/2-28
    }

    fun getFromIndex(index:Tile.Index):Tile {
        for (it in src){
            for (it1 in it){
                if ((index.x==it1.index.x)&&(index.y==it1.index.y))
                    return it1
            }
        }
        return src[0][0]
    }
    fun makeConnection(room: Room) {
        /*
        if (!listOfConnections.contains(room)) {
            var connectToCenter = room.getCenter()
            var minusX: Boolean = true
            var minusY: Boolean = true
            var angle: Double = 0.0
            minusX = center.x > connectToCenter.x
            minusY = center.y > connectToCenter.y
            if (!minusX) {
                if (!minusY) angle =
                    asin(sin(
                        sqrt((connectToCenter.x).pow(2) + (center.y).pow(2)) / sqrt(
                            (connectToCenter.x - center.x).pow(
                                2
                            ) + (connectToCenter.y - center.y).pow(2)
                        )
                    )).toDouble()
                else -1* acos(cos(
                    sqrt((connectToCenter.x).pow(2) + (center.y).pow(2)) / sqrt(
                        (connectToCenter.x - center.x).pow(
                            2
                        ) + (connectToCenter.y - center.y).pow(2)
                    )
                ))
            } else {
                if (!minusY) angle = PI/2+ acos(cos(
                    sqrt((connectToCenter.x).pow(2) + (center.y).pow(2)) / sqrt(
                        (connectToCenter.x - center.x).pow(2) + (connectToCenter.y - center.y).pow(2)
                    )
                )).toDouble()
                else -1*PI/2-1*acos(cos(
                    sqrt((connectToCenter.x).pow(2) + (center.y).pow(2)) / sqrt(
                        (connectToCenter.x - center.x).pow(
                            2
                        ) + (connectToCenter.y - center.y).pow(2)
                    )
                ))
            }
            val ang = toDegrees(angle)
            if ((ang >= 90) && (ang < 180)) direction = 2
            if ((ang >= 0) && (ang < 90)) direction = 3
            if ((ang >= -90) && (ang < 0)) direction = 4
            if ((ang >= -180) && (ang <= -90)) direction = 1

            amountOfDoors = 1
            when (direction) {
                1 -> {
                    pointer = Random.nextInt(2, src.size - 2)

                    src[pointer + 1][0].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[pointer][0].textureID = Textures.GRASS.id
                    src[pointer][0].isObstacle = false
                }

                2 -> {
                    pointer = Random.nextInt(2, src[0].size - 2)

                    src[src.lastIndex][pointer + 1].textureID =
                        Textures.STONEWALL_TRANSPARENT.id
                    src[src.lastIndex][pointer - 1].textureID =
                        Textures.STONEWALL_TRANSPARENT.id
                    src[src.lastIndex][pointer].isObstacle = false
                    src[src.lastIndex][pointer].textureID = Textures.GRASS.id
                }

                3 -> {
                    pointer = Random.nextInt(2, src.size - 2)

                    src[pointer - 1][src[0].lastIndex].textureID =
                        Textures.STONEWALL_TRANSPARENT.id
                    src[pointer][src[0].lastIndex].isObstacle = false
                    src[pointer][src[0].lastIndex].textureID = Textures.GRASS.id

                }

                4 -> {
                    pointer = Random.nextInt(2, src[0].size - 2)

                    src[0][pointer + 1].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[0][pointer].isObstacle = false
                    src[0][pointer].textureID = Textures.GRASS.id
                }
            }
            listOfConnections.add(room)
            room.makeConnection(this)
        }

         */
    }
}