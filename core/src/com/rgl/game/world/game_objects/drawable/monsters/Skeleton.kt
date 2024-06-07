package com.rgl.game.world.game_objects.drawable.monsters

import com.rgl.game.graphics.MonsterSprites
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.game_objects.drawable.player.PlayersStates
import com.rgl.game.world.level.Level
import java.util.UUID
import kotlin.random.Random

class Skeleton(player: Player, lvl: Level):Monster(player, lvl) {
    init {
        player.attach(this)
        key = UUID.randomUUID().toString()
        walkTexture = MonsterSprites.SKELETON_WALK2.id
        idleTexture = MonsterSprites.SKELETON_IDLE1.id
        AttackTexture = MonsterSprites.SKELETON_IDLE2.id
        HP = Random.nextInt(4, 11)
        ARMOR = 0
        ATTACK = Random.nextInt(2, 5+player.ARMOR)
        reward = HP
        TOTAL_HP=HP
        stateMachine.setState(PlayersStates.IDLE,this)
    }



}