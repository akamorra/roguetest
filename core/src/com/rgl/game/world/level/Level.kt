package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.game_objects.Drawable

//Level - Singleton класс для хранения,обработки данных и отрисовки текущего уровня
object Level {

    private var font: BitmapFont = BitmapFont()
    val listOfDrawable: MutableSet<Drawable> = mutableSetOf()
    val objectsManager: ObjectsManager = ObjectsManager()

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




        /*
        objectsManager.getList().forEach{
            it.value.render(batch, it.value.renderPos.x, it.value.renderPos.y)
        }*/
        listOfDrawable.sorted().forEach{
            it.render(batch, it.renderPos.x, it.renderPos.y)
        }/*
        for (it in data) {
            for (it1 in it) {
                if (it1.isDrawable) font.draw(
                    batch,
                    it1.zDepth.toString(),
                    it1.renderPos.x + MapCFG.TILESIZE / 2,
                    it1.renderPos.y + MapCFG.TILESIZE * 2 / 4 - 14
                )
            }
        }*/
    }

    fun getNew(rooms: Int): Array<Array<Tile>> {
        data = DungeonLevelGenerator.getDungeonLevel(rooms)
        for (it in data){
            for (it1 in it) {
                listOfDrawable.add(it1)
            }
        }
        objectsManager.getList().forEach{
            listOfDrawable.add(it.value)
        }
        return data
    }

    fun update() {
        data = DungeonLevelGenerator.get()
    }
}