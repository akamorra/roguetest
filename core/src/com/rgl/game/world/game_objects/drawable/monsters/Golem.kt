package com.rgl.game.world.game_objects.drawable.monsters
import com.rgl.game.graphics.MonsterSprites
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.game_objects.drawable.player.PlayersStates
import com.rgl.game.world.level.Level
import java.util.UUID
import kotlin.random.Random

class Golem(player: Player, lvl: Level):Monster(player, lvl) {
    var updateCount:Int=0
    override var MONSTER_WIDTH= MapCFG.PLAYER_WIDTH*2
    override var MONSTER_HEIGHT= MapCFG.PLAYER_HEIGHT
    override var textureID=MonsterSprites.GOLEM_IDLE3.id
    init {
        player.attach(this)
        key = UUID.randomUUID().toString()
        walkTexture = MonsterSprites.GOLEM_WALK2.id
        idleTexture = MonsterSprites.GOLEM_IDLE1.id
        AttackTexture = MonsterSprites.GOLEM_IDLE3.id
        HP = Random.nextInt(19, 36)
        ARMOR = 0
        ATTACK = Random.nextInt(10, 14+player.ARMOR)
        reward = HP
        TOTAL_HP=HP
        stateMachine.setState(PlayersStates.IDLE,this)
    }

    override fun turn() {
        if(updateCount==2) {
            super.turn()
            updateCount=0
        } else {
            waiting=true
            updateCount++
        }
    }
}