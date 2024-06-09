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
import com.rgl.game.world.MapCFG.ITEM_INVENTORY_HEIGHT
import com.rgl.game.world.MapCFG.ITEM_INVENTORY_SCALE_HEIGHT
import com.rgl.game.world.MapCFG.ITEM_INVENTORY_SCALE_WIDTH
import com.rgl.game.world.MapCFG.ITEM_INVENTORY_WIDTH
import com.rgl.game.world.game_objects.drawable.items.Pot
import com.rgl.game.world.game_objects.drawable.items.Scroll
import com.rgl.game.world.game_objects.drawable.items.parents.Armor
import com.rgl.game.world.game_objects.drawable.items.parents.Item
import com.rgl.game.world.game_objects.drawable.items.parents.ListOfStats
import com.rgl.game.world.game_objects.drawable.items.parents.Weapon
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.level.Tile

object Inventory {
    private var tempV3: Vector3 = Vector3(0.0f, 0.0f, 0.0f)
    private var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    var isDrawable = false
    private var font: BitmapFont = BitmapFont()
    private var inventoryGrid: Array<Array<Item?>> = Array(2) { Array(7) { null } }
    private var equipGrid: Array<Array<Item?>> = Array(2) { Array(2) { null } }
    public var selected: Tile.Index?=null
    fun getInventory(): Array<Array<Item?>> = inventoryGrid

    fun render(batch: Batch, camera: OrthographicCamera, player: Player) {
        tempV3 = camera.unproject(Vector3((0).toFloat(), (MapCFG.VIEWPORTHEIGHT).toFloat(), 0.0f))
        renderPos = Vector2(tempV3.x, MapCFG.VIEWPORTHEIGHT - tempV3.y)
        clear()
        updateGridArray(player)
        updateInventoryArray(player)
        when (isDrawable) {
            true -> {
                GuiInspect.hide()
                renderPos = Vector2(
                    tempV3.x,
                    tempV3.y + MapCFG.VIEWPORTHEIGHT - INVENTORY_GUI_HEIGHT * MapCFG.INVENTORY_GUI_SCALE
                )
            }

            false -> {
                renderPos = Vector2(
                    tempV3.x - INVENTORY_GUI_WIDTH / 2,
                    tempV3.y + MapCFG.VIEWPORTHEIGHT + INVENTORY_GUI_HEIGHT / 2
                )
                selected=null
            }
        }
        batch.draw(
            TextureRepo.getGui("inventory"), renderPos.x, renderPos.y,
            INVENTORY_GUI_WIDTH * MapCFG.INVENTORY_GUI_SCALE,
            INVENTORY_GUI_HEIGHT * MapCFG.INVENTORY_GUI_SCALE
        )
        font.color = Color.BLACK
        font.data.setScale(2.0f)
        font.draw(
            batch,
            " HP: " + player.HP,
            renderPos.x + (INVENTORY_GUI_WIDTH / 2 - 13) * MapCFG.INVENTORY_GUI_SCALE,
            renderPos.y + MapCFG.VIEWPORTHEIGHT - (INVENTORY_GUI_HEIGHT / 6 + 8)
        )
        font.color = Color.BROWN
        font.data.setScale(2.0f)
        font.draw(
            batch,
            "Strength: " + player.STRENGTH,
            renderPos.x + (INVENTORY_GUI_WIDTH / 2 - 13) * MapCFG.INVENTORY_GUI_SCALE,
            renderPos.y - 40 + MapCFG.VIEWPORTHEIGHT - (INVENTORY_GUI_HEIGHT / 6 + 8)
        )
        font.color = Color.FOREST
        font.data.setScale(2.0f)
        font.draw(
            batch,
            "Agility: " + player.AGILITY,
            renderPos.x + (INVENTORY_GUI_WIDTH / 2 - 13) * MapCFG.INVENTORY_GUI_SCALE,
            renderPos.y - 80 + MapCFG.VIEWPORTHEIGHT - (INVENTORY_GUI_HEIGHT / 6 + 8)
        )
        font.color = Color.RED
        font.data.setScale(2.0f)
        font.draw(
            batch,
            " ATTACK: " + player.ATTACK,
            renderPos.x + (INVENTORY_GUI_WIDTH / 5 * 3) * MapCFG.INVENTORY_GUI_SCALE,
            renderPos.y + MapCFG.VIEWPORTHEIGHT - (INVENTORY_GUI_HEIGHT / 6 + 8)
        )
        font.color = Color.BLUE
        font.data.setScale(2.0f)
        font.draw(
            batch,
            "ARMOR: " + player.ARMOR,
            renderPos.x + (INVENTORY_GUI_WIDTH / 5 * 3) * MapCFG.INVENTORY_GUI_SCALE,
            renderPos.y - 40 + MapCFG.VIEWPORTHEIGHT - (INVENTORY_GUI_HEIGHT / 6 + 8)
        )
        font.color = Color.GOLDENROD
        font.data.setScale(2.0f)
        font.draw(
            batch,
            "LEVEL: " + player.Level + " Progress: " + player.currentProgress + "/" + player.requiresProgress,
            renderPos.x + (INVENTORY_GUI_WIDTH / 5 * 3) * MapCFG.INVENTORY_GUI_SCALE,
            renderPos.y - 80 + MapCFG.VIEWPORTHEIGHT - (INVENTORY_GUI_HEIGHT / 6 + 8)
        )
        updateInventoryArray(player)
        for (i in 0..1) {
            for (j in 0..<inventoryGrid[0].size) {
                if (inventoryGrid[i][j] != null) {
                    inventoryGrid[i][j]!!.renderPos = Vector2(
                        renderPos.x + (j + 1) * 84 * ITEM_INVENTORY_SCALE_WIDTH + 23 * (j) * ITEM_INVENTORY_SCALE_WIDTH,
                        renderPos.y + (1 - i) * 144 * MapCFG.ITEM_INVENTORY_SCALE_HEIGHT + i * 36 * MapCFG.ITEM_INVENTORY_SCALE_HEIGHT
                    )
                    inventoryGrid[i][j]!!.renderInv(
                        batch,
                        inventoryGrid[i][j]!!.renderPos.x,
                        inventoryGrid[i][j]!!.renderPos.y,player
                    )
                    if(selected!=null ) if( selected!!.x==i&& selected!!.y==j) batch.draw(TextureRepo.getGui("selected"),
                        inventoryGrid[i][j]!!.renderPos.x,inventoryGrid[i][j]!!.renderPos.y,ITEM_INVENTORY_WIDTH, ITEM_INVENTORY_HEIGHT)
                }
            }
        }
        updateGridArray(player)
        for (j in 0..1) {
            for (i in 0..1) {
                if (equipGrid[i][j] != null) {
                    equipGrid[i][j]!!.renderPos = Vector2(
                        renderPos.x + 228 * ITEM_INVENTORY_SCALE_WIDTH + (i) * ITEM_INVENTORY_WIDTH - (1 - i) * 8 * ITEM_INVENTORY_SCALE_WIDTH,
                        renderPos.y + 368 * MapCFG.ITEM_INVENTORY_SCALE_HEIGHT - j * ITEM_INVENTORY_HEIGHT - 8 * ITEM_INVENTORY_SCALE_HEIGHT
                    )
                    equipGrid[i][j]!!.renderInv(
                        batch,
                        equipGrid[i][j]!!.renderPos.x,
                        equipGrid[i][j]!!.renderPos.y,player
                    )
                }
            }
        }
        if(selected!=null){
            font.setColor(Color.DARK_GRAY)
            font.data.setScale(2.0f)
            font.draw(batch, "Item details:", renderPos.x+48* ITEM_INVENTORY_SCALE_WIDTH, renderPos.y+424* ITEM_INVENTORY_SCALE_HEIGHT)
            font.data.setScale(1.5f)
            font.draw(batch, "UUID:\n"+ inventoryGrid[selected!!.x][ selected!!.y]!!.key, renderPos.x+48* ITEM_INVENTORY_SCALE_WIDTH, renderPos.y+424* ITEM_INVENTORY_SCALE_HEIGHT-40)
            if (inventoryGrid[selected!!.x][ selected!!.y] is Scroll){
                font.setColor(Color.LIGHT_GRAY)
                font.data.setScale(2.5f)
                font.draw(batch, "Function not released yet", renderPos.x+48* ITEM_INVENTORY_SCALE_WIDTH, renderPos.y+424* ITEM_INVENTORY_SCALE_HEIGHT-80)
            }
            if (inventoryGrid[selected!!.x][ selected!!.y] is Pot){
                font.setColor(Color.LIGHT_GRAY)
                font.data.setScale(2.5f)
                font.draw(batch, "Function not released yet", renderPos.x+48* ITEM_INVENTORY_SCALE_WIDTH, renderPos.y+424* ITEM_INVENTORY_SCALE_HEIGHT-80)
            }
            if(inventoryGrid[selected!!.x][ selected!!.y] is Weapon || inventoryGrid[selected!!.x][ selected!!.y] is Armor){
                var s1=""
                var s2=""
                inventoryGrid[selected!!.x][ selected!!.y]!!.getstatistics().forEach {
                    when(it.first){
                        ListOfStats.ATTACK -> s1+="\nAttack: +"+it.second
                        ListOfStats.ARMOR -> s1+="\nArmor: +"+it.second
                        ListOfStats.HP -> s1+="\nHP: +"+it.second
                        ListOfStats.STRENGTH -> s1+="\nStrength: +"+it.second
                        ListOfStats.AGILITY -> s1+="\nAgility: +"+it.second
                    }
                }
                font.data.setScale(2.0f)
                font.setColor(Color.LIME)
                font.draw(batch, s1, renderPos.x+48* ITEM_INVENTORY_SCALE_WIDTH, renderPos.y+424* ITEM_INVENTORY_SCALE_HEIGHT-80)
                if (player.checkRequirements(inventoryGrid[selected!!.x][ selected!!.y]!!)){
                    font.setColor(Color.GREEN)
                    s2+="CAN BE EQUIPPED"
                } else {
                    font.setColor(Color.RED)
                    s2+="CAN'T BE EQUIPPED"
                }
                s2+="\nRequiresStrength:"+inventoryGrid[selected!!.x][ selected!!.y]!!.requiresStrength+
                        "\nRequiresAgility:"+inventoryGrid[selected!!.x][ selected!!.y]!!.requiresAgility+
                        "\nRequiresLevel:"+inventoryGrid[selected!!.x][ selected!!.y]!!.requiresLevel
                font.data.setScale(2.0f)
                font.draw(batch, s2, renderPos.x + (INVENTORY_GUI_WIDTH / 2 - 13) * MapCFG.INVENTORY_GUI_SCALE, renderPos.y+424* ITEM_INVENTORY_SCALE_HEIGHT-170)
            }

        }
    }

    fun hide() {
        isDrawable = false
    }

    fun clear() {
        inventoryGrid = Array(2) { Array(7) { null } }
        equipGrid= Array(2){Array(2){null} }

    }

    fun show() {
        isDrawable = true
    }

    fun updateGridArray(player: Player) {
        equipGrid[0][0] = player.getEquip()[0]
        equipGrid[0][1] = player.getEquip()[1]
        equipGrid[1][0] = player.getEquip()[2]
        equipGrid[1][1] = player.getEquip()[3]
    }

    fun updateInventoryArray(player: Player) {
        player.getInventory().forEach {
            for (j in 0..1) {
                for (i in 0..<inventoryGrid[j].size) {
                    if (inventoryGrid[j][i] == null && !inventoryGrid[0].contains(it.value) && !inventoryGrid[1].contains(
                            it.value
                        )
                    ) {
                        inventoryGrid[j][i] = it.value
                        break
                    } else {
                        if (inventoryGrid[j][i] != null && !player.getInventory()
                                .contains(inventoryGrid[j][i]!!.key)
                        )
                            inventoryGrid[j][i] = null
                    }
                }
            }
        }
    }
}