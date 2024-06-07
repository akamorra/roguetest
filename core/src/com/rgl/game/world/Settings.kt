package com.rgl.game.world

import com.badlogic.gdx.Gdx

object MapCFG {
    const val TILESIZE = 256.0f
    const val MAPSIZE = 150
    var VIEWPORTWIDTH = Gdx.app.graphics.width
    var VIEWPORTHEIGHT = Gdx.app.graphics.height
    const val ROOMSIZE=5
    const val ROOMCOUNT=5
    const val MULTIPLIER= (MAPSIZE/ROOMSIZE)/ 4
    const val ZOOM=1.1f
    const val MAXZOOM=2.0f
    const val MINZOOM=1.0f

    //GUI
    const val INSPECT_GUI_ICON_SIZE=128.0f
    const val INSPECT_GUI_WIDTH=600.0f
    const val INSPECT_GUI_HEIGHT=480.0f
    const val INSPECT_GUI_SCALE=0.80f
    var INVENTORY_GUI_WIDTH=Gdx.app.graphics.width/904.0f*904.0f
    var INVENTORY_GUI_HEIGHT=Gdx.app.graphics.height/512.0f*512.0f
    const val INVENTORY_GUI_SCALE=1.0f
    const val START_GUI_WIDTH=360.0f
    const val START_GUI_HEIGTH=128.0f
    const val START_GUI_SCALE=2.0f
    const val SKIP_GUI_WIDTH=64.0f
    const val SKIP_GUI_HEIGTH=64.0f
    const val SKIP_GUI_SCALE=2.0f


    //ITEMS
    const val ITEM_ONMAP_SIZE= TILESIZE/2
    val ITEM_INVENTORY_SCALE_WIDTH=Gdx.app.graphics.width/904.0f
    val ITEM_INVENTORY_SCALE_HEIGHT=Gdx.app.graphics.height/512.0f
    val ITEM_INVENTORY_WIDTH=96.0f*ITEM_INVENTORY_SCALE_HEIGHT
    val ITEM_INVENTORY_HEIGHT=96.0f*ITEM_INVENTORY_SCALE_HEIGHT


    //PLAYER
    const val PLAYER_WIDTH=128.0f
    const val PLAYER_HEIGHT= PLAYER_WIDTH*2
    const val PLAYER_SPRITE_WIDTH=128.0f
    const val PLAYER_SPRITE_HEIGHT=256.0f
    const val PLAYER_STRENGTH_RATIO_PER_LEVEL=1.25f
    const val PLAYER_AGILITY_RATIO_PER_LEVEL=1.25f
}