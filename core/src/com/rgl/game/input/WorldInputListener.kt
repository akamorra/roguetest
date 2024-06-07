package com.rgl.game.input

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.graphics.Textures
import com.rgl.game.gui.GuiInspect
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.level.Level
import com.rgl.game.world.level.Tile
import kotlin.math.pow


class WorldInputListener(val camera: OrthographicCamera, val batch: SpriteBatch, var lvl:Level, var player: Player) : GestureListener {
    private var selectedTile: Tile? = null
    private var selectedTile2:Tile?=null

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {

        return false
    }

    private var tapPos: Vector3 = Vector3(0.0f, 0.0f, 0.0f)

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        tapPos = camera.unproject(Vector3(x, y, 0.0f))
        lvl.objectsManager.getList().forEach {
            if ((it.value.renderPos.x + MapCFG.ITEM_ONMAP_SIZE / 2 - tapPos.x).pow(2) / (MapCFG.ITEM_ONMAP_SIZE / 2).pow(
                    2
                ) + (it.value.renderPos.y + MapCFG.ITEM_ONMAP_SIZE / 2 - 14 - tapPos.y).pow(
                    2
                ) / (MapCFG.ITEM_ONMAP_SIZE / 2).pow(2) < 1
            ) {
                if(selectedTile==null){selectedTile=lvl.get()[it.value.index.x][it.value.index.y]}
                else{
                    selectedTile2=lvl.get()[it.value.index.x][it.value.index.y]
                    if(selectedTile!!.index==selectedTile2!!.index){

                        player.moveTo(selectedTile2!!,lvl)
                        selectedTile=null
                        selectedTile2=null
                    } else {
                        selectedTile=selectedTile2
                        selectedTile2=null
                    }
                }
                GuiInspect.show("ObjectHash:"+it.value.hashCode(),"Item",it.value.toString(),it.value.textureID)

                return true
            }
        }
        for (it in lvl.get()) {
            for (it1 in it) {
                if ((it1.renderPos.x + MapCFG.TILESIZE / 2 - tapPos.x).pow(2) / (MapCFG.TILESIZE / 2).pow(
                        2
                    ) + (it1.renderPos.y + MapCFG.TILESIZE / 2 - 14 - tapPos.y).pow(
                        2
                    ) / (MapCFG.TILESIZE / 2).pow(2) < 1
                ) {
                    if (it1.isInspectable()&& Textures.listOfWalkable.contains(it1.textureID)) {
                        GuiInspect.show(
                            "ObjectHash:" + it1.hashCode(), it1.index.toString(), it1.toString(),
                            it1.textureID

                        )
                        if(selectedTile==null){selectedTile=lvl.get()[it1.index.x][it1.index.y]}
                        else{
                            selectedTile2=lvl.get()[it1.index.x][it1.index.y]
                            if(selectedTile!!.index==selectedTile2!!.index){
                                player.moveTo(selectedTile2!!,lvl)
                                selectedTile=null
                                selectedTile2=null
                            } else {
                                selectedTile=selectedTile2
                                selectedTile2=null
                            }
                        }
                        return true
                    } else {
                        GuiInspect.hide()
                        selectedTile=null
                        selectedTile2=null
                        return true
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