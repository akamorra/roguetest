package com.rgl.game.world.level

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG.TILESIZE
import com.rgl.game.world.game_objects.Drawable

class Tile(
    override var renderPos: Vector2, override var textureID: Byte, override var index: Index,
    override var isDrawable: Boolean
) : TileInterface, Drawable {
    override var zDepth: Int = 0
    var prev:Index?=null
    var containsInstance=false
    class Index(var x: Int, var y: Int) {

        @Override
        override fun toString(): String {
            return "($x;$y)"
        }

        @Override
        override fun hashCode(): Int {
            return this.toString().hashCode()
        }
    }

    var isObstacle: Boolean = false
    fun isInspectable(): Boolean {
        return !isObstacle
    }

    override fun render(batch: Batch, x: Float, y: Float) {
        if (isDrawable) {
            batch.draw(
                TextureRepo.getTexture(
                    textureID
                ), x, y, TILESIZE, TILESIZE * 2
            )
        }

    }

    @Override
    override fun toString(): String {
        return "RenderPos (" + renderPos.x + ";" + renderPos.y + ")" +
                "\nGlobalIndex:" + index.toString() + "\nTextureID:(" + textureID + ")+\n ContainsInstance: "+containsInstance.toString()
    }

    fun getCenter():Vector2{
        return Vector2(renderPos.x+TILESIZE/2,renderPos.y+TILESIZE/4+44)
    }

    override fun compareTo(other: Drawable): Int {
        zDepth=index.x+index.y*10
        return zDepth-other.zDepth
    }
}