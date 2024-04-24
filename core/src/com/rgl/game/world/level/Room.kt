package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import kotlin.random.Random

object Room {
    private var width: Int = 0
    private var height: Int = 0
    private var direction: Int = 0
    private var amountOfDoors: Int = 0
    private var pointer: Int = 0
    private var src: Array<Array<Tile>> =
        Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 1, Tile.Index(0, 0)) } }

    fun getNext(): Array<Array<Tile>> {
        getNextRandom()
        src = Array(width) { Array(height) { Tile(Vector2(1.0f, 1.0f), 1, Tile.Index(0, 0)) } }
        fill()
        return src
    }

    fun print() {
        val pp: Array<Array<Tile>> = getNext()
        for (it in pp) {
            for (it1 in it) {
                System.out.print(it1.textureID)
            }
            System.out.println()
        }
    }

    private fun getNextRandom() {
        width = Random.nextInt(6) + 5
        height = Random.nextInt(6) + 5
    }

    private fun fill() {
        for (it in 0..<src.size) src[it][0].textureID = Textures.STONEWALL.id
        for (it in 0..<src.size) src[it][src[0].lastIndex].textureID = Textures.STONEWALL.id
        for (it in 0..<src[0].size) src[0][it].textureID = Textures.STONEWALL.id
        for (it in 0..<src[0].size) src[src.lastIndex][it].textureID = Textures.STONEWALL.id
        amountOfDoors = Random.nextInt(3) + 1
        for (i in 1..amountOfDoors) {
            direction = Random.nextInt(4) + 1
            when (direction) {
                1 -> {
                    pointer = Random.nextInt(src.size - 2) + 1
                    src[pointer][0].textureID = Textures.GRASS.id
                }

                2 -> {
                    pointer = Random.nextInt(src[0].size - 2) + 1
                    src[src.lastIndex][pointer].textureID = Textures.GRASS.id
                }

                3 -> {
                    pointer = Random.nextInt(src.size - 2) + 1
                    src[pointer][src[0].lastIndex].textureID = Textures.GRASS.id
                }

                4 -> {
                    pointer = Random.nextInt(src[0].size - 2) + 1
                    src[0][pointer].textureID = Textures.GRASS.id
                }
            }
        }
    }
}