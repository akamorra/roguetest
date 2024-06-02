package com.rgl.game.input

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.gui.GuiInspect
import com.rgl.game.world.MapCFG
import com.rgl.game.world.level.Level
import kotlin.math.pow


class WorldInputListener(val camera: OrthographicCamera, val batch: SpriteBatch) : GestureListener {
    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {

        return false
    }

    private var tapPos: Vector3 = Vector3(0.0f, 0.0f, 0.0f)

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        tapPos = camera.unproject(Vector3(x, y, 0.0f))
        //System.out.println(" " + x + ";" + y)
        //System.out.println(" " + tapPos.x + ";" + tapPos.y)
        Level.objectsManager.getList().forEach {
            if ((it.value.renderPos.x + MapCFG.ITEM_ONMAP_SIZE / 2 - tapPos.x).pow(2) / (MapCFG.ITEM_ONMAP_SIZE / 2).pow(
                    2
                ) + (it.value.renderPos.y + MapCFG.ITEM_ONMAP_SIZE / 2 - 14 - tapPos.y).pow(
                    2
                ) / (MapCFG.ITEM_ONMAP_SIZE / 2).pow(2) < 1
            ) {
                GuiInspect.show("ObjectHash:"+it.value.hashCode(),"Item",it.value.toString(),it.value.textureID)
                return true
            }
        }
        for (it in Level.get()) {
            for (it1 in it) {
                if ((it1.renderPos.x + MapCFG.TILESIZE / 2 - tapPos.x).pow(2) / (MapCFG.TILESIZE / 2).pow(
                        2
                    ) + (it1.renderPos.y + MapCFG.TILESIZE / 2 - 14 - tapPos.y).pow(
                        2
                    ) / (MapCFG.TILESIZE / 2).pow(2) < 1
                ) {
                    if (it1.isInspectable()) {
                        GuiInspect.show(
                            "ObjectHash:" + it1.hashCode(), it1.index.toString(), it1.toString(),
                            it1.textureID
                        )
                        return false
                    } else {
                        GuiInspect.hide()
                    }
                }
            }
        }

        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {

        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
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