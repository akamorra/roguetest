package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2

interface TileInterface {
    val renderPos: Vector2
    val textureID: Byte
    val index: Tile.Index
}