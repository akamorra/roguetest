package com.rgl.game.world.game_objects.drawable.items.parents

import com.rgl.game.world.level.Tile

interface ItemInterface {

    var isPickedUP:Boolean
    var index: Tile.Index

    fun spawn(t:Tile)


    fun pickUp(){

        isPickedUP=true
    }

    fun isPickedUp():Boolean{

        return isPickedUP}

}