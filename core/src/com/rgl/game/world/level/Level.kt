package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures

//Level - Singleton класс для хранения,обработки данных и отрисовки текущего уровня
object Level {

    private var font: BitmapFont = BitmapFont()

    private var objectsManager: ObjectsManager = ObjectsManager()

    private var data: Array<Array<Tile>> = Array(1) {
        Array(1) {
            Tile(
                Vector2(1.0f, 1.0f), Textures.EMPTY_TEXTURE.id,
                Tile.Index(0, 0), false
            )
        }
    }

    fun get(): Array<Array<Tile>> {
        return data
    }

    fun set(src: Array<Array<Tile>>) {
        data = src
    }

    fun render(batch: Batch) {

        for (it in data) {
            for (it1 in it) {
                it1.render(batch, it1.renderPos.x, it1.renderPos.y)
                /*
                if (it1.isDrawable) font.draw(
                    batch,
                    it1.index.toString(),
                    it1.renderPos.x + MapCFG.TILESIZE / 2,
                    it1.renderPos.y + MapCFG.TILESIZE*2 / 4-14
                )
                */
            }
        }

    }

    fun getNew(rooms: Int): Array<Array<Tile>> {
        data = DungeonLevelGenerator.getDungeonLevel(rooms)
        return data
    }

    fun update() {
        data = DungeonLevelGenerator.get()
    }
}