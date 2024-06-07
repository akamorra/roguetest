package com.rgl.game.gui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG
import com.rgl.game.world.MapCFG.INVENTORY_GUI_HEIGHT
import com.rgl.game.world.MapCFG.INVENTORY_GUI_WIDTH
import com.rgl.game.world.game_objects.drawable.player.Player

object Inventory{
    private var tempV3:Vector3 = Vector3(0.0f,0.0f,0.0f)
    private var renderPos: Vector2 =Vector2(0.0f,0.0f)
    private var isDrawable=false
    private var font: BitmapFont = BitmapFont()

    fun render(batch: Batch, camera: OrthographicCamera, player: Player) {
        tempV3 =camera.unproject(Vector3((0).toFloat(),(MapCFG.VIEWPORTHEIGHT).toFloat(),0.0f))
        renderPos =Vector2(tempV3.x, MapCFG.VIEWPORTHEIGHT - tempV3.y)
        when(isDrawable){
            true->{
                GuiInspect.hide()
                renderPos =Vector2(tempV3.x, tempV3.y+ MapCFG.VIEWPORTHEIGHT - INVENTORY_GUI_HEIGHT * MapCFG.INVENTORY_GUI_SCALE)
            }
            false->{
                renderPos = Vector2(tempV3.x- INVENTORY_GUI_WIDTH /2, tempV3.y+ MapCFG.VIEWPORTHEIGHT + INVENTORY_GUI_HEIGHT /2)
            }
        }
        batch.draw(
            TextureRepo.getGui("inventory"), renderPos.x, renderPos.y,
            INVENTORY_GUI_WIDTH * MapCFG.INVENTORY_GUI_SCALE,
            INVENTORY_GUI_HEIGHT * MapCFG.INVENTORY_GUI_SCALE
        )
        font.color = Color.BLACK
        font.data.setScale(2.0f)
        font.draw(batch," HP: "+player.HP,
            renderPos.x+ (INVENTORY_GUI_WIDTH/2-13) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y+ MapCFG.VIEWPORTHEIGHT-(INVENTORY_GUI_HEIGHT/6+8))
        font.color = Color.BROWN
        font.data.setScale(2.0f)
        font.draw(batch,"Strength: "+player.STRENGTH,
            renderPos.x+ (INVENTORY_GUI_WIDTH/2-13) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y-40 +MapCFG.VIEWPORTHEIGHT-(INVENTORY_GUI_HEIGHT/6+8))
        font.color = Color.FOREST
        font.data.setScale(2.0f)
        font.draw(batch,"Agility: "+player.AGILITY,
            renderPos.x+ (INVENTORY_GUI_WIDTH/2-13) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y-80+ MapCFG.VIEWPORTHEIGHT-(INVENTORY_GUI_HEIGHT/6+8))
        font.color = Color.RED
        font.data.setScale(2.0f)
        font.draw(batch," ATTACK: "+player.ATTACK,
            renderPos.x+ (INVENTORY_GUI_WIDTH/5*3) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y+ MapCFG.VIEWPORTHEIGHT-(INVENTORY_GUI_HEIGHT/6+8))
        font.color = Color.BLUE
        font.data.setScale(2.0f)
        font.draw(batch,"ARMOR: "+player.ARMOR,
            renderPos.x+ (INVENTORY_GUI_WIDTH/5*3) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y-40 +MapCFG.VIEWPORTHEIGHT-(INVENTORY_GUI_HEIGHT/6+8))
        font.color = Color.GOLDENROD
        font.data.setScale(2.0f)
        font.draw(batch,"LEVEL: "+player.Level+" Progress: "+player.currentProgress+"/"+player.requiresProgress,
            renderPos.x+ (INVENTORY_GUI_WIDTH/5*3) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y-80+ MapCFG.VIEWPORTHEIGHT-(INVENTORY_GUI_HEIGHT/6+8))

    }
    fun hide(){
        isDrawable =false
    }
    fun show(){
        isDrawable =true
    }
}