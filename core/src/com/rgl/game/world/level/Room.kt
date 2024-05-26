package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import kotlin.random.Random

data class Room(var width: Int, var height: Int) {
    private var direction: Int = 0
    private var amountOfDoors: Int = 0
    private var pointer: Int = 0
    private var src: Array<Array<Tile>> =
        Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 1, Tile.Index(0, 0), false) } }

    init {
        src = getNext()
    }

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
        amountOfDoors = Random.nextInt(2) + 1
        for (i in 1..amountOfDoors) {
            direction = Random.nextInt(4) + 1
            when (direction) {
                1 -> {
                    pointer = Random.nextInt(2, src.size - 2)

                    src[pointer + 1][0].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[pointer][0].textureID = Textures.GRASS.id
                    src[pointer][0].isObstacle = false
                }

                2 -> {
                    pointer = Random.nextInt(2, src[0].size - 2)

                    src[src.lastIndex][pointer + 1].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[src.lastIndex][pointer - 1].textureID = Textures.STONEWALL_TRANSPARENT.id
                    src[src.lastIndex][pointer].isObstacle = false
                    src[src.lastIndex][pointer].textureID = Textures.GRASS.id
                }

                3 -> {
                    pointer = Random.nextInt(2, src.size - 2)

                    src[pointer - 1][src[0].lastIndex].textureID = Textures.STONEWALL_TRANSPARENT.id
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
        }
    }
}