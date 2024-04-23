package com.rgl.game.world.level


import com.badlogic.gdx.math.Vector2
import com.rgl.game.world.World
import kotlin.random.Random


object LevelGenerator {
    private var sizex:Int=0
    private var sizey:Int=0

    fun generate() {
        World.clear()
        addLevel()
    }

    fun addLevel() {
        //генерация и добавление нового уровня
        sizex = Random.nextInt(100) + 50
        sizey = Random.nextInt(100) + 50
        Level.data = Array(sizex) { Array(sizey) { Tile(Vector2(1.0f, 1.0f), 1) } }
        calculate(Level.data)
        World.addLevel(Level)
    }

    private fun calculate(level: Array<Array<Tile>>) { //процедурный генератор уровня]
        var i: Float = 0.0f
        var j: Float = 0.0f
        for (it in level) {
            for (it1 in it) {
                it1.pos = Vector2(i, j)
                it1.textureID = 1
                i++
            }
            j++
            i = 0.0f
        }
    }
}