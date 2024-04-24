package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

//Level - Singleton класс для хранения,обработки данных и отрисовки текущего уровня
object Level {
    //private var font: BitmapFont =BitmapFont()

    private var objectsManager: ObjectsManager = ObjectsManager()

    private var data: Array<Array<Tile>> = Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 1,
        Tile.Index(0,0)
    ) } }

    fun get(): Array<Array<Tile>> {return data}
    fun set(src:Array<Array<Tile>>){
        data=src
    }
    fun render(batch: Batch) {
        for(it in data){
            for (it1 in it){
                it1.render(batch,it1.renderPos.x,it1.renderPos.y)
                //if(it1.textureID.toInt() !=0)font.draw(batch, it1.renderPos.toString()+"\n"+it1.index.x+":"+it1.index.y, it1.renderPos.x+86, it1.renderPos.y+180)
            }
        }
    }
    fun getNew(rooms:Int):Array<Array<Tile>>{
        data=DungeonLevel.getDungeonLevel(rooms)
        return data
    }
    fun update() {

    }
}