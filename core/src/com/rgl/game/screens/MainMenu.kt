package com.rgl.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.rgl.game.Main
import com.rgl.game.gui.NewGameButton
import com.rgl.game.world.MapCFG


class MainMenu (val game: Main): Screen {
    val batch=game.batch
    val camera=game.camera
    val uicamera=game.uicamera
    val font= BitmapFont()
    var tempV3: Vector3 = Vector3(0.0f,0.0f,0.0f)
    override fun show() {
        Gdx.input.inputProcessor= GestureDetector(StartButtonInput(uicamera,this))
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        batch.begin()
        font.color = Color.GOLDENROD
        font.data.setScale(1.0f,1.0f)
        NewGameButton.render(batch,uicamera)
        font.draw(batch, "(r)2024 yury lebedev https://github.com/akamorra/roguetest" , tempV3.x+100,0.0f+MapCFG.VIEWPORTHEIGHT+tempV3.y-100)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        Gdx.input.inputProcessor=null
    }

    override fun dispose() {
        font.dispose()

    }
}