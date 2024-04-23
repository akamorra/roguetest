package com.rgl.game.world.game_objects

import com.badlogic.gdx.graphics.g2d.Batch

interface Drawable {
    fun render(batch: Batch, x: Float, y: Float)

}