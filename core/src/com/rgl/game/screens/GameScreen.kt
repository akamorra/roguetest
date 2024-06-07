package com.rgl.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.rgl.game.Main
import com.rgl.game.graphics.Textures
import com.rgl.game.gui.GuiInspect.render
import com.rgl.game.gui.Inventory.render
import com.rgl.game.gui.SkipTurnButton
import com.rgl.game.input.CameraInputListener
import com.rgl.game.input.GuiInputListener
import com.rgl.game.input.WorldInputListener
import com.rgl.game.world.MapCFG
import com.rgl.game.world.World
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.level.Level
import com.rgl.game.world.level.Tile

class GameScreen(val game: Main) : Screen {
    private var end: Boolean = false
    private val batch = game.batch
    private val camera = game.camera
    private val uicamera = game.uicamera
    private val player = Player()
    private var deltatime = 0.0f
    private lateinit var currentLevel: Level
    private lateinit var cameraInputListener: CameraInputListener
    private lateinit var worldInputListener: WorldInputListener
    private lateinit var guiInputListener: GuiInputListener
    private var inputMultiplexer: InputMultiplexer = InputMultiplexer()
    private lateinit var skipButtonListener: SkipButtonInput

    init {
        World.addLevel(player)
        currentLevel = World.getLevel()
        uicamera.setToOrtho(false, MapCFG.VIEWPORTWIDTH.toFloat(), MapCFG.VIEWPORTHEIGHT.toFloat())
        cameraInputListener = CameraInputListener(camera)
        worldInputListener = WorldInputListener(camera, batch, currentLevel, player)
        guiInputListener = GuiInputListener(uicamera)
        skipButtonListener = SkipButtonInput(uicamera, player)
        camera.setToOrtho(false, MapCFG.VIEWPORTWIDTH.toFloat(), MapCFG.VIEWPORTHEIGHT.toFloat())

        inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(GestureDetector(guiInputListener))
        inputMultiplexer.addProcessor(GestureDetector(skipButtonListener))
        inputMultiplexer.addProcessor(GestureDetector(cameraInputListener))
        inputMultiplexer.addProcessor(GestureDetector(worldInputListener))
        Gdx.input.inputProcessor = inputMultiplexer
        camera.zoom = MapCFG.ZOOM
        camera.position.set(player.renderPos.x, player.renderPos.y, 0.0f)
    }

    override fun show() {
        Gdx.input.inputProcessor = inputMultiplexer
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        currentLevel.check()
        camera.update()

        deltatime += Gdx.graphics.deltaTime
        if (deltatime > 0.025) {
            player.update(currentLevel)
            deltatime = 0.0f
        }

        batch.projectionMatrix = camera.combined
        batch.begin()
        currentLevel.render(batch)
        batch.projectionMatrix = uicamera.combined
        batch.end()
        batch.begin()
        render(batch, uicamera)
        render(batch, uicamera, player)
        SkipTurnButton.render(batch, uicamera)
        batch.end()
        if (player.HP <= 0) {
            game.screen = game.endgame
            game.dispose()
            World.clear()
        }
        if (player.posTile.textureID == Textures.END_GATE.id) {
            ScreenUtils.clear(0f, 0f, 0f, 1f)
            World.CURRENTLVL++
            player.posTile = Tile(
                Vector2(0.0f, 0.0f), 1, Tile.Index(1, 1), true
            )
            deltatime = 0.0f

            game.screen = (GameScreen(game))

        }

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