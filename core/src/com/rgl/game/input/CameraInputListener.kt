package com.rgl.game.input

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.rgl.game.world.MapCFG
import kotlin.math.round

class CameraInputListener(camera: OrthographicCamera) : GestureListener {
    private val cam = camera


    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        cam.translate(-deltaX * cam.zoom * 0.8f, deltaY * cam.zoom * 0.8f, 0.0f)
        cam.update()
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        if (cam.zoom <= MapCFG.MAXZOOM && initialDistance < distance && cam.zoom > MapCFG.MINZOOM) {
            cam.zoom -= (distance - initialDistance) / 10000
            cam.update()
            return true
        }
        if (cam.zoom >= MapCFG.MINZOOM && initialDistance > distance && cam.zoom <= MapCFG.MAXZOOM) {
            cam.zoom += (initialDistance - distance) / 10000
            cam.update()
            return true
        }
        if (cam.zoom > MapCFG.MAXZOOM || cam.zoom < MapCFG.MINZOOM) {
            cam.zoom = round(cam.zoom)
            cam.update()
            return true
        }
        return false
    }

    override fun pinch(
        initialPointer1: Vector2?,
        initialPointer2: Vector2?,
        pointer1: Vector2?,
        pointer2: Vector2?
    ): Boolean {
        return false
    }

    override fun pinchStop() {

    }

}