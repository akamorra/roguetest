package com.rgl.game.input

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.rgl.game.gui.Inventory

class GuiInputListener (camera:OrthographicCamera):GestureListener {

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        if(Inventory.isDrawable){






            return true
        }
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun pinch(
        initialPointer1: Vector2?,
        initialPointer2: Vector2?,
        pointer1: Vector2?,
        pointer2: Vector2?
    ): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun pinchStop() {

    }
}