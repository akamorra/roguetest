package com.rgl.game.graphics

import com.badlogic.gdx.graphics.Color

enum class Quality(var quality:String) {
    SIMPLE("simple"),    //gray
    COMMON("common"),    //green
    RARE("rare"),        //blue
    UNIQUE("unique"),    //purple
    PERFECT("perfect");   //gold
    companion object{
        fun getColor(quality:Quality): Color {
            when(quality){
                SIMPLE -> return Color.LIGHT_GRAY
                COMMON -> return Color.GREEN
                RARE -> return Color.BLUE
                UNIQUE -> return Color.VIOLET
                PERFECT -> return Color.GOLDENROD
            }
        }
    }
}