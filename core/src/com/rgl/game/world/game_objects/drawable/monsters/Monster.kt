package com.rgl.game.world.game_objects.drawable.monsters

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.PlayerSprites
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.Drawable
import com.rgl.game.world.game_objects.drawable.player.HasArmor
import com.rgl.game.world.game_objects.drawable.player.HasAttack
import com.rgl.game.world.game_objects.drawable.player.HasHP
import com.rgl.game.world.game_objects.drawable.player.Observer
import com.rgl.game.world.game_objects.drawable.player.PathFinder
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.game_objects.drawable.player.PlayersStates
import com.rgl.game.world.level.Level
import com.rgl.game.world.level.Tile
import java.util.UUID
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

open class Monster(var player: Player, var lvl: Level) : Observer, Drawable, HasArmor, HasAttack, HasHP {
    override var HP = 0
    private var timer:Float = Gdx.graphics.deltaTime
    override var ARMOR = 0
    override var ATTACK = 0
    private var attacked:Boolean =false
    var isdead = false
     var waiting:Boolean=false
    var index: Tile.Index = Tile.Index(0, 0) //current tile player is at
    override var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    override var zDepth: Int = 0
    override var isDrawable: Boolean = true
    open var textureID: Byte =0
    var walkTexture: Byte = 0
    var idleTexture:Byte=0
    var AttackTexture:Byte=0
    val stateMachine: StateMachine = StateMachine()
    var path = mutableSetOf<Tile>()
    private var pathFinder: PathFinder = PathFinder()
    private var posTile: Tile = Tile(Vector2(0.0f, 0.0f), 0, Tile.Index(0, 0), true)
    private var marched: Float = 0.0f
    var key: String? = null
    open var font: BitmapFont = BitmapFont()
    private var destination: Tile? = null
    var reward = 0
    var TOTAL_HP=0
    open var MONSTER_WIDTH=MapCFG.PLAYER_WIDTH
    open var MONSTER_HEIGHT=MapCFG.PLAYER_HEIGHT

    init {
        player.attach(this)
        key = UUID.randomUUID().toString()
        walkTexture = PlayerSprites.WALK2.id
        idleTexture = PlayerSprites.IDLE1.id
        AttackTexture = PlayerSprites.IDLE2.id
        HP = Random.nextInt(9, 16)
        ARMOR = 0
        ATTACK = Random.nextInt(4, 6+player.ARMOR)
        TOTAL_HP=HP
        reward = TOTAL_HP
        stateMachine.setState(PlayersStates.IDLE,this)
    }

    open class StateMachine {
        private var currentState: Int = 1
        fun setState(state: PlayersStates, monster: Monster) {
            when (state) {
                PlayersStates.ATTACKING -> {
                    currentState = PlayersStates.ATTACKING.id
                    monster.textureID = monster.AttackTexture
                }

                PlayersStates.IDLE -> {
                    currentState = PlayersStates.IDLE.id
                    monster.textureID = monster.idleTexture
                }

                PlayersStates.MOVETO -> {
                    PlayersStates.MOVETO.id
                    currentState = PlayersStates.MOVETO.id
                    monster.textureID = monster.walkTexture

                }
            }
        }

        fun getState() = currentState
    }

    override fun render(batch: Batch, x: Float, y: Float) {
        if(attacked&&timer<0.2f){
            batch.setColor(Color.RED)
            timer+=Gdx.graphics.deltaTime
        }
        if(timer>=0.2f){
            batch.setColor(Color.WHITE)
            timer=0.0f
            attacked=false
        }
        batch.draw(
            TextureRepo.getMonsterTexture(textureID),
            renderPos.x,
            renderPos.y,
            MONSTER_WIDTH,
            MONSTER_HEIGHT
        )
        batch.setColor(Color.WHITE)
        batch.draw(TextureRepo.getGui("hpbar"),getCenter().x-64,getCenter().y+ MONSTER_HEIGHT -56,128.0f,32.0f)
        font.color = Color.ORANGE
        font.data.setScale(1.7f, 1.7f)
        font.draw(
            batch,
            "HP:" + HP.toString()+"/"+TOTAL_HP.toString(),
            getCenter().x-48,
            getCenter().y + MapCFG.PLAYER_HEIGHT - 30
        )
    }

    open fun attack(player: Player) {
        player.getAttacked(this)
    }

    override fun update() {
        if(!waiting) {
            when (stateMachine.getState()) {
                PlayersStates.MOVETO.id -> {
                    if (path.isEmpty()) {
                        stateMachine.setState(PlayersStates.IDLE, this)
                    } else {
                        destination = path.first()
                        var flag = true
                        lvl.monstersManager.getList().forEach {
                            if (it.value.destination == this.destination && it.value != this) flag =
                                false
                            return@forEach
                        }
                        if (!path.first().containsInstance && flag) {
                            this.textureID = walkTexture
                            destination = path.first()
                            //destination!!.containsInstance = true
                            var oldpos = Vector2(renderPos.x, renderPos.y)
                            this.renderPos.x += (destination!!.getCenter().x - posTile.getCenter().x) / 4
                            this.renderPos.y += (destination!!.getCenter().y - posTile.getCenter().y) / 4
                            marched += sqrt(
                                (renderPos.x - oldpos.x).pow(2) + (renderPos.y - oldpos.y).pow(
                                    2
                                )
                            )
                            if (destination!!.zDepth >= posTile.zDepth) this.index =
                                destination!!.index
                            if (marched > sqrt(
                                    (destination!!.getCenter().x - posTile.getCenter().x).pow(2) + (destination!!.getCenter().y - posTile.getCenter().y).pow(
                                        2
                                    )
                                )
                            ) {
                                waiting = true
                                destination!!.containsInstance = true
                                posTile.containsInstance = false
                                index = destination!!.index
                                posTile = destination!!
                                this.renderPos = posTile.getCenter()
                                this.renderPos.x -= MONSTER_WIDTH / 2
                                path.remove(path.first())
                                marched = 0.0f
                                if (path.isNotEmpty()) {
                                    destination = path.first()
                                } else {
                                    destination = null
                                    stateMachine.setState(PlayersStates.IDLE, this)
                                }
                            }
                        }
                    }
                }

                PlayersStates.ATTACKING.id -> {
                }

                PlayersStates.IDLE.id -> {
                }


            }
        }

    }

    fun getAttacked(player: Player) {
        attacked=true
        this.HP -= player.ATTACK
        if (this.HP <= 0) {
            player.achieveExp(this)
            this.isdead = true
            this.posTile.containsInstance = false
            if (destination != null) this.destination!!.containsInstance = false

        }
    }
    override fun toString(): String {
        return  "\nUUID:"+key+"\n RenderPos: ("+renderPos.x+";"+renderPos.y+")\n"+"At Tile: ("+index.x+";"+index.y+")"+
                "\nCurrentHP: "+HP+"/"+TOTAL_HP+
                "\nATTACK: "+ATTACK+
                "\nARMOR: "+ARMOR+
                "\nReward Exp:"+reward
    }
    override fun turn() {
        update()
        update()
        update()
        update()
        update()
        waiting=false
        if (!isdead) {
            this.stateMachine.setState(PlayersStates.IDLE, this)
            if ((abs(this.index.x - player.index.x) + abs(this.index.y - player.index.y)) < 2) {
                path = mutableSetOf()
                stateMachine.setState(PlayersStates.ATTACKING, this)
            }
            if ((abs(this.index.x - player.index.x) + abs(this.index.y - player.index.y)) < 7 && (abs(
                    this.index.x - player.index.x
                ) + abs(this.index.y - player.index.y)) > 1
                && stateMachine.getState() != PlayersStates.ATTACKING.id
            ) {
                if (player.destination == null)
                    this.moveTo(lvl.get()[player.index.x][player.index.y], lvl)
                else this.moveTo(
                    lvl.get()[player.destination!!.index.x][player.destination!!.index.y],
                    lvl
                )
            } else
                when (stateMachine.getState()) {
                    PlayersStates.IDLE.id -> {
                        var rand = Random.nextInt(100)
                        if (rand.mod(5) == 0) {
                            var x = Random.nextInt(10) - 5
                            var y = Random.nextInt(10) - 5
                            if (Textures.listOfWalkable.contains(lvl.get()[index.x + x][index.y + y].textureID)) {
                                this.moveTo(lvl.get()[index.x + x][index.y + y], lvl)
                            }
                        }
                    }

                    PlayersStates.ATTACKING.id -> {
                        attack(player)
                    }
                }
        }
    }

    fun moveTo(tile: Tile, lvl: Level) {
        if (tile != lvl.get()[index.x][index.y]) {
            path = mutableSetOf()
            stateMachine.setState(PlayersStates.MOVETO, this)
            textureID = idleTexture
            path = pathFinder.findPath(tile, lvl.get()[index.x][index.y], lvl.get())

        } else {
            stateMachine.setState(PlayersStates.IDLE, this)
        }
    }

    fun pickUp() {

    }

    fun spawn(where: Tile) {
        index = where.index
        renderPos = Vector2(where.getCenter().x - MONSTER_WIDTH / 2, where.getCenter().y)
        posTile = where
        where.containsInstance = true
    }

    fun getCenter(): Vector2 {
        return Vector2(renderPos.x + MONSTER_WIDTH / 2, renderPos.y)
    }

    override fun compareTo(other: Drawable): Int {
        zDepth = index.x + index.y * 10
        return zDepth - other.zDepth
    }
}