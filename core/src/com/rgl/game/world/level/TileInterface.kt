package com.rgl.game.world.level

import com.badlogic.gdx.math.Vector2

interface TileInterface {
    var pos: Vector2
    val textureID: Byte
    //val containsEntity:Entity;
}