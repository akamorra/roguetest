package com.rgl.game.gui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG

object SkipTurnButton {
        var renderPos: Vector2 = Vector2(0.0f,0.0f)
        private var tempV3: Vector3 = Vector3(0.0f,0.0f,0.0f)

        fun render(batch: SpriteBatch, camera: OrthographicCamera){
            tempV3 =camera.unproject(Vector3((0).toFloat(),(MapCFG.VIEWPORTHEIGHT).toFloat(),0.0f))
            renderPos =
                Vector2(tempV3.x+ 50,0.0f+tempV3.y+50)
            batch.draw(
                TextureRepo.getGui("skipbutton"), renderPos.x, renderPos.y,
                MapCFG.SKIP_GUI_WIDTH * MapCFG.SKIP_GUI_SCALE,
                MapCFG.SKIP_GUI_HEIGTH * MapCFG.SKIP_GUI_SCALE
            )
        }

}