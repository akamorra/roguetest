package com.rgl.game.world.game_objects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

interface Drawable :Comparable<Drawable>{
    fun render(batch: Batch, x:Float, y:Float)
    var isDrawable: Boolean
    var zDepth:Int
    var renderPos: Vector2


}