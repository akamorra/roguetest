package com.rgl.game.world.game_objects.drawable.player

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.PlayerSprites
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG.PLAYER_AGILITY_RATIO_PER_LEVEL
import com.rgl.game.world.MapCFG.PLAYER_HEIGHT
import com.rgl.game.world.MapCFG.PLAYER_STRENGTH_RATIO_PER_LEVEL
import com.rgl.game.world.MapCFG.PLAYER_WIDTH
import com.rgl.game.world.game_objects.Drawable
import com.rgl.game.world.game_objects.drawable.items.parents.Item
import com.rgl.game.world.game_objects.drawable.monsters.Monster
import com.rgl.game.world.level.Level
import com.rgl.game.world.level.Tile
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class Player : Turning(), Drawable, HasArmor, HasAttack, HasHP {
    var index: Tile.Index = Tile.Index(0, 0) //current tile player is at
    override var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    override var zDepth: Int = 0
    override var isDrawable: Boolean = true
    var textureID: Byte = PlayerSprites.IDLE1.id
    private var path = mutableSetOf<Tile>()
    private var pathFinder: PathFinder = PathFinder()
    var posTile: Tile = Tile(Vector2(0.0f, 0.0f), 0, Tile.Index(0, 0), true)
    private var marched: Float = 0.0f
     override var HP = 0
     override var ARMOR = 0
     override var ATTACK = 0
    var BASE_ATTACK = 5
    var BASE_ARMOR = 0
    var BASE_HP = 0
     var STRENGTH = 0.0f
    var AGILITY = 0.0f
    var Level = 0
    var nextLevel = 0
     var requiresProgress = 0
     var currentProgress = 0
    var TOTAL_HP=0
    private var inventory = mutableMapOf<String, Item>()
    private var font: BitmapFont = BitmapFont()
    var destination: Tile? = null

    fun getInventory():MutableMap<String, Item>{
        return inventory
    }
    init {
        BASE_HP = 10
        BASE_ARMOR = 0
        BASE_ATTACK = 5
        STRENGTH = 2.0f
        HP=BASE_HP+STRENGTH.toInt()*3
        TOTAL_HP=HP
        AGILITY = 1.0f
        Level = 1
        nextLevel = Level + 1
        requiresProgress = nextLevel * 5
        currentProgress = 0
    }

    class StateMachine {
        private var currentState: Int = 1
        fun setState(state: PlayersStates, player: Player) {
            when (state) {
                PlayersStates.ATTACKING -> {
                    currentState = PlayersStates.ATTACKING.id

                }

                PlayersStates.IDLE -> {
                    currentState = PlayersStates.IDLE.id
                    player.textureID = PlayerSprites.IDLE1.id
                }

                PlayersStates.MOVETO -> {
                    PlayersStates.MOVETO.id
                    player.textureID = PlayerSprites.WALK1.id
                    currentState = PlayersStates.MOVETO.id
                }
            }
        }

        fun getState() = currentState
    }

    private fun updateHP() {
        TOTAL_HP=BASE_HP+STRENGTH.toInt()*3
    }

    private fun updateATTACK() {
        this.ATTACK = BASE_ATTACK + (STRENGTH / 2).toInt()
    }

    private fun updateARMOR() {
        this.ARMOR = BASE_ARMOR + (AGILITY / 2).toInt()
    }

    val stateMachine: StateMachine = StateMachine()

    fun getAttacked(monster: Monster) {
        updateATTACK()
        updateARMOR()
        HP -= (monster.ATTACK - ARMOR)
    }

    override fun render(batch: Batch, x: Float, y: Float) {
        updateATTACK()
        updateHP()
        updateARMOR()
        batch.draw(
            TextureRepo.getPlayerTexture(textureID),
            renderPos.x,
            renderPos.y,
            PLAYER_WIDTH,
            PLAYER_HEIGHT
        )
        batch.draw(TextureRepo.getGui("hpbar"),getCenter().x-64,getCenter().y+PLAYER_HEIGHT-56,128.0f,32.0f)
        font.color = Color.GREEN
        font.data.setScale(1.8f, 1.8f)
        font.draw(
            batch,
            "HP:" + HP.toString()+"/"+TOTAL_HP.toString(),
            getCenter().x-48,
            getCenter().y + PLAYER_HEIGHT - 30
        )
    }

    fun update(lvl: Level) {
        when (stateMachine.getState()) {
            PlayersStates.MOVETO.id -> {
                if (path.isEmpty()) {
                    stateMachine.setState(PlayersStates.IDLE, this)
                } else {
                    if (!path.first().containsInstance) {

                        destination = path.first()
                        destination!!.containsInstance = true
                        val oldpos = Vector2(renderPos.x, renderPos.y)
                        this.renderPos.x += (destination!!.getCenter().x - posTile.getCenter().x) / 4
                        this.renderPos.y += (destination!!.getCenter().y - posTile.getCenter().y) / 4
                        marched += sqrt(
                            (renderPos.x - oldpos.x).pow(2) + (renderPos.y - oldpos.y).pow(
                                2
                            )
                        )
                        callObservers()
                        if (destination!!.zDepth >= posTile.zDepth) this.index = destination!!.index
                        destination!!.containsInstance = false
                        if (marched > sqrt(
                                (destination!!.getCenter().x - posTile.getCenter().x).pow(2) + (destination!!.getCenter().y - posTile.getCenter().y).pow(
                                    2
                                )
                            )
                        ) {
                            destination!!.containsInstance = true
                            posTile.containsInstance = false
                            index = destination!!.index
                            posTile = destination!!

                            this.renderPos = posTile.getCenter()
                            this.renderPos.x -= PLAYER_WIDTH / 2
                            path.remove(path.first())
                            marched = 0.0f
                            bigCallObservers()

                            if (path.isNotEmpty())
                                destination = path.first()
                            else {
                                destination = null
                                pickUp(lvl)
                                stateMachine.setState(PlayersStates.IDLE, this)
                            }

                        }

                    } else stateMachine.setState(PlayersStates.IDLE, this)
                }
            }

            PlayersStates.ATTACKING.id -> {
            }

            PlayersStates.IDLE.id -> {
            }


        }
    }

    fun achieveExp(monster: Monster) {
        this.currentProgress += monster.reward
        if (currentProgress >= requiresProgress) {
            currentProgress = 0 + currentProgress - requiresProgress
            Level++
            nextLevel = Level + 1
            requiresProgress = nextLevel * 5*Level
            STRENGTH += PLAYER_STRENGTH_RATIO_PER_LEVEL
            AGILITY += PLAYER_AGILITY_RATIO_PER_LEVEL
            HP=BASE_HP+STRENGTH.toInt()*3
        }
    }

    fun moveTo(tile: Tile, lvl: Level) {
        var flag = false
        if (tile != lvl.get()[index.x][index.y]) {
            lvl.monstersManager.getList().forEach {
                if (tile.index.x == it.value.index.x && tile.index.y == it.value.index.y && abs(it.value.index.x - this.index.x) + abs(
                        it.value.index.y - this.index.y
                    ) < 2
                ) {
                    attack(it.value)
                    flag = true

                }
            }
            if (!flag) {
                path = mutableSetOf()
                stateMachine.setState(PlayersStates.MOVETO, this)
                path = pathFinder.findPath(tile, lvl.get()[index.x][index.y], lvl.get())
            }
        } else {
            stateMachine.setState(PlayersStates.IDLE, this)
        }
    }

    fun attack(monster: Monster) {
        updateATTACK()
        monster.getAttacked(this)
        bigCallObservers()
        stateMachine.setState(PlayersStates.ATTACKING, this)
    }

    fun pickUp(lvl: Level) {
        if (inventory.count()<18) {
            lvl.objectsManager.getList().forEach {
                if (it.value.index.x == this.index.x && it.value.index.y == this.index.y && inventory.count()<14) {
                    inventory.put(it.key, it.value)
                    it.value.isPickedUP = true
                    lvl.listOfDrawable.remove(it.value)
                }
            }
            inventory.forEach {
                lvl.objectsManager.destroyItem(it.key)
            }

        }
    }

    fun getCenter(): Vector2 {
        return Vector2(renderPos.x + PLAYER_WIDTH / 2, renderPos.y)
    }

    fun spawn(where: Tile) {
        index = where.index
        renderPos = Vector2(where.getCenter().x - PLAYER_WIDTH / 2, where.getCenter().y)
        posTile = where
        where.containsInstance = true
    }

    override fun compareTo(other: Drawable): Int {
        zDepth = index.x + (index.y) * 10
        return zDepth - other.zDepth + 1
    }


}