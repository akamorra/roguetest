package com.rgl.game.world.game_objects.drawable.items.parents

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Quality
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG.ITEM_INVENTORY_HEIGHT
import com.rgl.game.world.MapCFG.ITEM_INVENTORY_WIDTH
import com.rgl.game.world.MapCFG.ITEM_ONMAP_SIZE
import com.rgl.game.world.game_objects.Drawable
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.level.Tile
import java.util.UUID

open abstract class Item : ItemInterface, Drawable {
    override fun render(batch: Batch, x: Float, y: Float) {
        batch.draw(TextureRepo.getItemTexture(textureID),renderPos.x,renderPos.y,ITEM_ONMAP_SIZE,ITEM_ONMAP_SIZE)
    }
    var tintTextureID:Byte = 0
    var quality: Quality =Quality.SIMPLE
    open fun renderInv(batch: Batch, x: Float, y: Float, player: Player) {
        batch.setColor(Quality.getColor(quality));
        batch.draw(TextureRepo.getItemTexture(tintTextureID),renderPos.x,renderPos.y,
            ITEM_INVENTORY_WIDTH, ITEM_INVENTORY_HEIGHT)
        if((player.checkRequirements(this)))batch.setColor(Color.WHITE)
        else batch.setColor(Color.DARK_GRAY)
        batch.draw(TextureRepo.getItemTexture(textureID),renderPos.x,renderPos.y,
            ITEM_INVENTORY_WIDTH, ITEM_INVENTORY_HEIGHT)
        batch.setColor(Color.WHITE)
    }
    open fun getstatistics():MutableSet<Pair<ListOfStats,Int>>{
        return mutableSetOf()
    }
    var requiresStrength: Float = 0.0f
    var requiresAgility: Float = 0.0f
    var requiresLevel: Int = 0

    override var isDrawable: Boolean = true
    override var isPickedUP: Boolean = false
    override var index: Tile.Index = Tile.Index(0, 0)
    override var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    override fun spawn(t:Tile) {
        index.x=t.index.x
        index.y=t.index.y
        renderPos=t.getCenter()
        renderPos.x-=ITEM_ONMAP_SIZE/2
        renderPos.y-=ITEM_ONMAP_SIZE/4
    }

    var textureID: Byte = 0
    var itemType: ItemType? = null
    var key: String? = null
    override var zDepth: Int =0
    init {
        key = UUID.randomUUID().toString()
    }

    override fun toString(): String {
        return  "\nUUID:"+key+"\n RenderPos: ("+renderPos.x+";"+renderPos.y+")\n"+"At Tile: ("+index.x+";"+index.y+")"+
                "\nRequiresStrength: "+requiresStrength+
                "\nRequiresAgility: "+requiresAgility+
                "\nRequiresLevel: "+requiresLevel
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    fun getCenter():Vector2{
        return Vector2(renderPos.x+ ITEM_ONMAP_SIZE /2,renderPos.y+ITEM_ONMAP_SIZE/2+ ITEM_ONMAP_SIZE/16)
    }

    override fun compareTo(other: Drawable): Int {
        zDepth=index.x+index.y*10
        return zDepth-other.zDepth
    }

}