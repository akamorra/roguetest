package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

//Level - Singleton класс для хранения данных и отрисовки текущего уровня
object Level {
    //private var font:BitmapFont=BitmapFont()
    private var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    private var pointer: Int = 0
    private var objectsManager: ObjectsManager = ObjectsManager()

    var data: Array<Array<Tile>> = Array(100) { Array(100) { Tile(Vector2(1.0f, 1.0f), 1) } }

    fun render(batch: Batch) {
        renderPos.x = 0.0f
        renderPos.y = 0.0f
        pointer = 0
        for (it in data) {
            for (it1 in it) {
                renderPos.x -= Tile.TILESIZE / 2
                renderPos.y -= (Tile.TILESIZE / 2 - Tile.TILESIZE / 8)
                it1.render(batch, renderPos.x, renderPos.y)
                it1.pos = Vector2(renderPos.x, renderPos.y)
                //font.draw(batch, renderPos.toString(), renderPos.x+86, renderPos.y+180);
            }
            pointer++
            renderPos.y = 0 - pointer * (Tile.TILESIZE / 2 - Tile.TILESIZE / 8)
            renderPos.x = 0 + pointer * Tile.TILESIZE / 2
        }
    }
    fun get(rooms:Int):Array<Array<Tile>>{
        data=DungeonLevel.getDungeonLevel(rooms)
        return data
    }
    fun update() {

    }
}