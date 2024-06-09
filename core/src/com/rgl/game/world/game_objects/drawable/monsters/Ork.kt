package com.rgl.game.world.game_objects.drawable.monsters

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.rgl.game.graphics.MonsterSprites
import com.rgl.game.graphics.TextureRepo
import com.rgl.game.world.MapCFG
import com.rgl.game.world.game_objects.drawable.player.Player
import com.rgl.game.world.game_objects.drawable.player.PlayersStates
import com.rgl.game.world.level.Level
import java.util.UUID
import kotlin.random.Random

class Ork(player: Player, lvl: Level) : Monster(player, lvl) {
    override var MONSTER_HEIGHT = MapCFG.PLAYER_HEIGHT

    init {
        player.attach(this)
        key = UUID.randomUUID().toString()
        walkTexture = MonsterSprites.GOBLIN_WALK2.id
        idleTexture = MonsterSprites.GOBLIN_IDLE1.id
        AttackTexture = MonsterSprites.GOBLIN_IDLE2.id
        HP = Random.nextInt(9, 16+player.TOTAL_HP/4)
        ARMOR = Random.nextInt(1, 3+player.ATTACK/4)
        ATTACK = Random.nextInt(3, 9+player.ARMOR/3)
        reward = HP
        TOTAL_HP = HP
        stateMachine.setState(PlayersStates.IDLE, this)
    }

    override fun render(batch: Batch, x: Float, y: Float) {
        batch.draw(
            TextureRepo.getMonsterTexture(textureID),
            renderPos.x,
            renderPos.y,
            MONSTER_WIDTH,
            MONSTER_HEIGHT
        )
        batch.draw(
            TextureRepo.getGui("hpbar"),
            getCenter().x - 64,
            getCenter().y + MONSTER_HEIGHT / 2 + 4,
            128.0f,
            32.0f
        )
        font.color = Color.ORANGE
        font.data.setScale(1.7f, 1.7f)
        font.draw(
            batch,
            "HP:" + HP.toString() + "/" + TOTAL_HP.toString(),
            getCenter().x - 48,
            getCenter().y + MapCFG.PLAYER_HEIGHT / 2 + 30
        )
    }
}