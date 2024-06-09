package com.rgl.game.screens

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.gui.NewGameButton
import com.rgl.game.world.MapCFG
import com.rgl.game.world.World
import com.rgl.game.world.game_objects.drawable.player.Player

class StartButtonInput(var camera: OrthographicCamera, var screen: MainMenu):GestureListener {
    var tempV3:Vector3 = Vector3(0.0f,0.0f,0.0f)

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        tempV3 =camera.unproject(Vector3((0).toFloat(),(MapCFG.VIEWPORTHEIGHT).toFloat(),0.0f))
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        tempV3 =camera.unproject(Vector3((0).toFloat(),(MapCFG.VIEWPORTHEIGHT).toFloat(),0.0f))
        if (x>NewGameButton.renderPos.x&& x<NewGameButton.renderPos.x+MapCFG.START_GUI_WIDTH*MapCFG.START_GUI_SCALE&&
            MapCFG.VIEWPORTHEIGHT-y>NewGameButton.renderPos.y && MapCFG.VIEWPORTHEIGHT-y<NewGameButton.renderPos.y+MapCFG.START_GUI_HEIGTH*MapCFG.START_GUI_SCALE){
            World.clear()
            screen.game.screen = GameScreen(screen.game, Player())
            screen.game.dispose()
            return true
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