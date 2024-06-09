package com.rgl.game.input

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.rgl.game.gui.Inventory
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.level.Tile

class GuiInputListener (var camera:OrthographicCamera, var player: Player):GestureListener {

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if(Inventory.isDrawable)return true
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        if(Inventory.isDrawable){
            for (i in 0..1){
                for (j in 0..<Inventory.getInventory()[0].size){
                    if (Inventory.getInventory()[i][j]!=null) {
                        if (x>Inventory.getInventory()[i][j]!!.renderPos.x&& x<Inventory.getInventory()[i][j]!!.renderPos.x+ MapCFG.ITEM_INVENTORY_WIDTH &&
                            MapCFG.VIEWPORTHEIGHT-y>Inventory.getInventory()[i][j]!!.renderPos.y&& MapCFG.VIEWPORTHEIGHT-y<Inventory.getInventory()[i][j]!!.renderPos.y+ MapCFG.ITEM_INVENTORY_HEIGHT)
                        {
                            if(Inventory.selected==null) {
                                Inventory.selected = Tile.Index(i, j)
                            } else {
                                if(Inventory.selected!!.x==i && Inventory.selected!!.y==j){
                                    player.equip(Inventory.getInventory()[i][j]!!)
                                    Inventory.selected=null
                                }else{
                                    Inventory.selected = Tile.Index(i, j)
                                }
                            }
                        }
                    }
                }
            }



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