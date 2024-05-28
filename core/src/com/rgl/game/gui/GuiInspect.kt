package com.rgl.game.gui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG

object GuiInspect{
    private var icon: TextureRegion = TextureRepo.getTexture(Textures.EMPTY_TEXTURE.id)
    private var title:String = ""
    private var header:String=""
    private var description:String=""
    private var font: BitmapFont = BitmapFont()
    private var renderPos: Vector2 =Vector2(0.0f,0.0f)
    private var tempV3:Vector3= Vector3(0.0f,0.0f,0.0f)
    fun render(batch: Batch, camera: OrthographicCamera) {
        tempV3=camera.unproject(Vector3((MapCFG.VIEWPORTWIDTH-450).toFloat(),(MapCFG.VIEWPORTHEIGHT-550).toFloat(),0.0f))
        renderPos=Vector2(tempV3.x, tempV3.y)
        when(isDrawable){
            true->{
                renderPos=Vector2(tempV3.x, tempV3.y)
            }
            false->{
               renderPos= Vector2(tempV3.x-300, tempV3.y+MapCFG.VIEWPORTHEIGHT+240)
            }
        }
        batch.draw(TextureRepo.getGuiTexture("inspect"), renderPos.x, renderPos.y,600.0f,480.0f) //
        font.setColor(255.0f,0.0f,0.0f,1.0f)
        font.draw(batch, title,renderPos.x+220, renderPos.y+372)
        font.setColor(255.0f,255.0f,255.0f,1.0f)
        font.draw(batch, header,renderPos.x+220, renderPos.y+372-16)
        font.draw(batch, description,renderPos.x+80, renderPos.y+240)
    }

    fun hide(){
        isDrawable=false
        header=""
        title=""
        description=""
        icon=TextureRepo.getTexture(Textures.EMPTY_TEXTURE.id)
    }
    fun show(title:String, header:String, description:String, icon:TextureRegion){
        isDrawable=true
        GuiInspect.title=title
        GuiInspect.header=header
        GuiInspect.description= description
        GuiInspect.icon=icon
    }
    private var isDrawable: Boolean = false



}