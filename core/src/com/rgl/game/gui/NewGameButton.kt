package com.rgl.game.gui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG

object NewGameButton {
    var renderPos: Vector2 = Vector2(0.0f,0.0f)
    private var tempV3: Vector3 = Vector3(0.0f,0.0f,0.0f)

    fun render(batch:SpriteBatch, camera:OrthographicCamera){
        tempV3 =camera.unproject(Vector3((0).toFloat(),(MapCFG.VIEWPORTHEIGHT).toFloat(),0.0f))
        renderPos =Vector2(tempV3.x+MapCFG.VIEWPORTWIDTH/2-MapCFG.VIEWPORTWIDTH/8,0.0f+MapCFG.VIEWPORTHEIGHT/2+tempV3.y)
        batch.draw(
            TextureRepo.getGui("startbutton"), renderPos.x, renderPos.y,
            MapCFG.START_GUI_WIDTH * MapCFG.START_GUI_SCALE,
            MapCFG.START_GUI_HEIGTH * MapCFG.START_GUI_SCALE
        )
    }
}