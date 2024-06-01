package com.rgl.game.world

object MapCFG {
    const val TILESIZE = 256.0f
    const val MAPSIZE = 100
    const val VIEWPORTWIDTH = 1920
    const val VIEWPORTHEIGHT = 1080
    const val ROOMSIZE=5
    const val ROOMCOUNT=15
    const val MULTIPLIER= (MAPSIZE/ROOMSIZE)/4.toInt()
    const val ZOOM=10
    const val MAXZOOM=101.0f
    const val MINZOOM=1.0f

    //GUI
    const val INSPECT_GUI_ICON_SIZE=128.0f
    const val INSPECT_GUI_WIDTH=600.0f
    const val INSPECT_GUI_HEIGHT=480.0f
    const val INSPECT_GUI_SCALE=0.80f
}