package com.rgl.game.world.game_objects.drawable.items

import com.badlogic.gdx.graphics.g2d.Batch
import com.rgl.game.graphics.ItemsSprites
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.drawable.items.parents.Item
import com.rgl.game.world.game_objects.drawable.items.parents.ItemType
import com.rgl.game.world.game_objects.drawable.items.parents.ListOfEffects
import com.rgl.game.world.game_objects.drawable.player.Player
import kotlin.random.Random

class Pot : Item() {
    var effect: Byte = 0
    private var amount: Int = 0

    override fun renderInv(batch: Batch, x: Float, y: Float, player: Player) {
        batch.draw(
            TextureRepo.getItemTexture(textureID),renderPos.x,renderPos.y,
            MapCFG.ITEM_INVENTORY_WIDTH, MapCFG.ITEM_INVENTORY_HEIGHT
        )
    }
    init {

        itemType = ItemType.POT
        var rand = Random.nextInt(0, 5)
        when (rand) {
            1 -> {
                effect = ListOfEffects.HEAL.id
                textureID = ItemsSprites.POT1.id
                amount = 2
            }

            2 -> {
                ListOfEffects.POISON.id
                textureID = ItemsSprites.POT2.id
                amount = 2
            }

            3 -> {
                ListOfEffects.POISON.id
                textureID = ItemsSprites.POT3.id
                amount = 3
            }

            4 -> {
                ListOfEffects.HEAL.id
                textureID = ItemsSprites.POT4.id
                amount = 3
            }

            else -> textureID = ItemsSprites.POT1.id
        }
    }

    fun consume(player: Player) {
        when (this.effect) {
            ListOfEffects.HEAL.id -> {
                if (player.HP+amount*player.Level < player.TOTAL_HP)
                player.HP+=amount*player.Level
                else
                    player.HP=player.TOTAL_HP
            }

            ListOfEffects.POISON.id -> {
                player.HP-=amount*(player.TOTAL_HP/5).toInt()
            }
        }
    }

    override fun toString(): String {

        return "ItemType: Pot " + super.toString()
    }


}