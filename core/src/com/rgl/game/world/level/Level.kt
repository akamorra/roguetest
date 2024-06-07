package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.game_objects.Drawable
import com.rgl.game.world.game_objects.drawable.monsters.Monster
import com.rgl.game.world.game_objects.drawable.player.Player

//Level - Singleton класс для хранения,обработки данных и отрисовки текущего уровня
class Level(var player: Player) {

    val listOfDrawable: MutableSet<Drawable> = mutableSetOf()
    val objectsManager: ObjectsManager = ObjectsManager()
    val monstersManager: MonstersManager = MonstersManager()
    var spawnPoint: Tile.Index = Tile.Index(0, 0)
    var endPoint: Tile.Index = Tile.Index(0, 0)
    private var UUID: String? = null

    init {
        UUID = java.util.UUID.randomUUID().toString()
    }

    private var data: Array<Array<Tile>> = Array(1) {
        Array(1) {
            Tile(
                Vector2(1.0f, 1.0f), Textures.EMPTY_TEXTURE.id,
                Tile.Index(0, 0), false
            )
        }
    }


    fun render(batch: Batch) {
        listOfDrawable.sorted().forEach {
            it.render(batch, it.renderPos.x, it.renderPos.y)
        }
    }

    fun getNew(rooms: Int): Level {
        data= Array(1) {
            Array(1) {
                Tile(
                    Vector2(1.0f, 1.0f), Textures.EMPTY_TEXTURE.id,
                    Tile.Index(0, 0), false
                )
            }
        }
        data = DungeonLevelGenerator.getDungeonLevel(rooms, this)
        for (it in data) {
            for (it1 in it) {
                listOfDrawable.add(it1)
            }
        }
        objectsManager.getList().forEach {
            listOfDrawable.add(it.value)
        }
        monstersManager.getList().forEach {
            listOfDrawable.add(it.value)
        }
        spawnPoint = DungeonLevelGenerator.getSpawnPoint()
        endPoint = DungeonLevelGenerator.getEndPoint()
        data[endPoint.x][endPoint.y].textureID=Textures.END_GATE.id
        player.spawn(data[spawnPoint.x][spawnPoint.y])
        listOfDrawable.add(player)
        return this
    }

    fun get(): Array<Array<Tile>> {
        return data
    }
    fun check(){
        var listToDestroy= mutableSetOf<Monster>()
        monstersManager.getList().forEach{
            if (it.value.isdead){
                listOfDrawable.remove(it.value)
                player.detach(it.value)
                listToDestroy.add(it.value)
            }
        }
        for(it in listToDestroy)monstersManager.getList().remove(it.key)

    }
    fun update() {
        data = DungeonLevelGenerator.get()
    }
}