package com.rgl.game.world.game_objects.drawable.items

import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.graphics.Quality
import com.rgl.game.world.game_objects.drawable.items.parents.ListOfStats
import com.rgl.game.world.game_objects.drawable.items.parents.Weapon
import kotlin.random.Random

class Axe(var playerlevel:Int):Weapon(){
    var unknownTextureID:Byte=ItemsSprites.AXE_UNKNOWN.id

    init {
        var rand = Random.nextInt(0, 4)
        when (rand) {
            1 ->{
                textureID = ItemsSprites.AXE1.id
                tintTextureID = ItemsSprites.AXE_TINT1.id
                requiresLevel = Random.nextInt(0, playerlevel+2)
                requiresStrength= 1.75f * requiresLevel
                requiresAgility = 1.75f * requiresLevel
                if (requiresLevel/(playerlevel+2)<0.5) quality= Quality.COMMON else quality=
                    Quality.SIMPLE
            }
            2 -> {
                textureID = ItemsSprites.AXE2.id
                tintTextureID = ItemsSprites.AXE_TINT2.id
                requiresLevel = Random.nextInt(3, playerlevel+5)
                requiresStrength= 1.95f * requiresLevel
                requiresAgility = 1.95f * requiresLevel
                if (requiresLevel/(playerlevel+5)<0.5) quality=Quality.RARE else quality=Quality.COMMON
            }
            3 -> {
                textureID = ItemsSprites.AXE3.id
                tintTextureID = ItemsSprites.AXE_TINT3.id
                requiresLevel = Random.nextInt(playerlevel, playerlevel+8)
                requiresStrength= 2.15f * requiresLevel
                requiresAgility = 2.15f * requiresLevel
                if (requiresLevel/(playerlevel+8)<0.25) quality=Quality.PERFECT else quality=Quality.UNIQUE
            }
            else -> {
                textureID = ItemsSprites.AXE1.id
                tintTextureID = ItemsSprites.AXE_TINT1.id
                requiresLevel = Random.nextInt(0, playerlevel+2)
                requiresStrength= 1.75f * requiresLevel
                requiresAgility = 1.75f * requiresLevel
                if (requiresLevel/(playerlevel+2)>0.5) quality=Quality.COMMON else quality=Quality.SIMPLE
            }
        }
        when(quality){
            Quality.SIMPLE -> stats.add(Pair(ListOfStats.ATTACK,1+requiresStrength.toInt()))
            Quality.COMMON -> {
                stats.add(Pair(ListOfStats.ATTACK,1+requiresStrength.toInt()))
                stats.add(Pair(ListOfStats.AGILITY,1+requiresAgility.toInt()))
            }
            Quality.RARE -> {
                stats.add(Pair(ListOfStats.ATTACK,1+requiresStrength.toInt()))
                stats.add(Pair(ListOfStats.AGILITY,1+(requiresAgility).toInt()))
            }
            Quality.UNIQUE -> {
                stats.add(Pair(ListOfStats.ATTACK,1+requiresStrength.toInt()))
                stats.add(Pair(ListOfStats.AGILITY,1+(requiresAgility*1.2f).toInt()))
                stats.add(Pair(ListOfStats.STRENGTH,1+(requiresStrength*0.5f).toInt()))
            }
            Quality.PERFECT ->{
                stats.add(Pair(ListOfStats.ATTACK,1+requiresStrength.toInt()))
                stats.add(Pair(ListOfStats.AGILITY,1+(requiresAgility*1.2f).toInt()))
                stats.add(Pair(ListOfStats.STRENGTH,1+(requiresStrength*0.5f).toInt()))
                stats.add(Pair(ListOfStats.HP,1+(requiresLevel/2).toInt()))
            }
        }
    }

    override fun toString(): String {

        return "ItemType: Axe "+super.toString()
    }

    override fun generateRequirements(playerLevel: Int) {
        //
    }
}