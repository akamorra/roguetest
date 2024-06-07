package com.rgl.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.rgl.game.Main
import com.rgl.game.world.MapCFG

class EndGame(val game: Main): Screen {
    var batch: SpriteBatch
    var camera: OrthographicCamera
    var uicamera: OrthographicCamera
    var font: BitmapFont
    var tempV3: Vector3 = Vector3(0.0f, 0.0f, 0.0f)
    var deltatime=0.0f
    init{

            batch = game.batch
            camera = game.camera
            uicamera = game.uicamera
            font = BitmapFont()

    }

    override fun show() {
        if(game.batch==null)game.batch= SpriteBatch()
    }

    override fun render(delta: Float) {

        tempV3 = uicamera.unproject(Vector3((0).toFloat(), (MapCFG.VIEWPORTHEIGHT).toFloat(), 0.0f))
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f)
        deltatime += Gdx.graphics.deltaTime
        if (deltatime > 5.0f) {
            deltatime=0.0f
            game.screen=game.mainmenu
        }
        batch.begin()
        font.color = Color.RED
        font.data.setScale(8.0f, 8.0f)
        font.draw(
            batch,
            "GAME OVER",
            tempV3.x + MapCFG.VIEWPORTWIDTH / 2 - MapCFG.VIEWPORTWIDTH / 8,
            0.0f + MapCFG.VIEWPORTHEIGHT / 2 + tempV3.y
        )
        batch.end()

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun dispose() {

    }
}