package com.rgl.game.world

object MapCFG {
    const val TILESIZE = 256.0f
    const val MAPSIZE = 150
    const val VIEWPORTWIDTH = 1920
    const val VIEWPORTHEIGHT = 1080
    const val ROOMSIZE=5
    const val ROOMCOUNT=5
    const val MULTIPLIER= (MAPSIZE/ROOMSIZE)/4.toInt()
    const val ZOOM=10
    const val MAXZOOM=101.0f
    const val MINZOOM=1.0f

    //GUI
    const val INSPECT_GUI_ICON_SIZE=128.0f
    const val INSPECT_GUI_WIDTH=600.0f
    const val INSPECT_GUI_HEIGHT=480.0f
    const val INSPECT_GUI_SCALE=0.80f

    //ITEMS
    const val ITEM_ONMAP_SIZE= TILESIZE/2
    const val ITEM_INVENTORY_SIZE=TILESIZE/4

    //
    const val PLAYER_STRENGTH_RATIO_PER_LEVEL=1.25f
    const val PLAYER_AGILITY_RATIO_PER_LEVEL=1.25f
}