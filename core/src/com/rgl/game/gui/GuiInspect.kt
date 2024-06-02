package com.rgl.game.gui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG.INSPECT_GUI_HEIGHT
import com.rgl.game.world.MapCFG.INSPECT_GUI_ICON_SIZE
import com.rgl.game.world.MapCFG.INSPECT_GUI_SCALE
import com.rgl.game.world.MapCFG.INSPECT_GUI_WIDTH
import com.rgl.game.world.MapCFG.VIEWPORTHEIGHT

object GuiInspect{
    private var icon: Byte = Textures.EMPTY_TEXTURE.id
    private var title:String = ""
    private var header:String=""
    private var description:String=""
    private var font: BitmapFont = BitmapFont()
    private var renderPos: Vector2 =Vector2(0.0f,0.0f)
    private var tempV3:Vector3= Vector3(0.0f,0.0f,0.0f)
    private var collisionRect:Rectangle= Rectangle(0.0f,0.0f,0.0f,0.0f)
    fun render(batch: Batch, camera: OrthographicCamera) {
        tempV3=camera.unproject(Vector3((0).toFloat(),(VIEWPORTHEIGHT).toFloat(),0.0f))
        renderPos=Vector2(tempV3.x, VIEWPORTHEIGHT-tempV3.y)
        when(isDrawable){
            true->{
                renderPos=Vector2(tempV3.x, tempV3.y+VIEWPORTHEIGHT-INSPECT_GUI_HEIGHT* INSPECT_GUI_SCALE)
            }
            false->{
               renderPos= Vector2(tempV3.x-INSPECT_GUI_WIDTH/2, tempV3.y+VIEWPORTHEIGHT+INSPECT_GUI_HEIGHT/2)
            }
        }
        batch.draw(TextureRepo.getGui("inspect"), renderPos.x, renderPos.y,INSPECT_GUI_WIDTH* INSPECT_GUI_SCALE,INSPECT_GUI_HEIGHT* INSPECT_GUI_SCALE) //
        batch.draw(TextureRepo.getTexture(icon),renderPos.x+58* INSPECT_GUI_SCALE,renderPos.y+280* INSPECT_GUI_SCALE,INSPECT_GUI_ICON_SIZE* INSPECT_GUI_SCALE,INSPECT_GUI_ICON_SIZE* INSPECT_GUI_SCALE)
        font.setColor(255.0f,0.0f,0.0f,1.0f)
        font.draw(batch, title,renderPos.x+ (INSPECT_GUI_WIDTH/3+20)* INSPECT_GUI_SCALE, renderPos.y+392* INSPECT_GUI_SCALE)
        font.setColor(255.0f,255.0f,255.0f,1.0f)
        font.draw(batch, header,renderPos.x+(INSPECT_GUI_WIDTH/3+20)*INSPECT_GUI_SCALE, renderPos.y+392* INSPECT_GUI_SCALE-20* INSPECT_GUI_SCALE)
        font.draw(batch, description,renderPos.x+58* INSPECT_GUI_SCALE, renderPos.y+240* INSPECT_GUI_SCALE)
        updateCollisionRect()
    }
    fun checkTouch(x:Float,y:Float):Boolean{
        return ((x in collisionRect.x..renderPos.x+collisionRect.width)&&
                (VIEWPORTHEIGHT-y in collisionRect.y..collisionRect.y+VIEWPORTHEIGHT- collisionRect.height))
    }

    private fun updateCollisionRect(){
        collisionRect= Rectangle(renderPos.x, renderPos.y, INSPECT_GUI_WIDTH* INSPECT_GUI_SCALE,
            INSPECT_GUI_HEIGHT* INSPECT_GUI_SCALE)
    }
    fun hide(){
        isDrawable=false
        header=""
        title=""
        description=""
        icon=Textures.EMPTY_TEXTURE.id
        updateCollisionRect()
    }
    fun show(title:String, header:String, description:String, icon:Byte){
        isDrawable=true
        GuiInspect.title=title
        GuiInspect.header=header
        GuiInspect.description= description
        GuiInspect.icon=icon
        updateCollisionRect()
    }
    private var isDrawable: Boolean = false



}