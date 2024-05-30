package com.rgl.game.graphics

enum class Textures(val id: Byte) {
    STONE1(1),
    STONE2(2),
    STONE3(3),
    DOOR(4),
    STONEWALL(5),
    STONEWALL_TRANSPARENT_W(6),
    STONEWALL_TRANSPARENT_E(7),
    STONEWALL_TRANSPARENT(8),
    STONEWALL_TOP_TRANSPARENT(9),
    EMPTY_TEXTURE(127);
    companion object {
        val listOfObstacles:Set<Byte> = setOf(5,6,7,8,9)
        val listOfWalls:Set<Byte> =setOf(5,6,7,8,9)
        val listOfTransparents:Set<Byte> = setOf(6,7,8,9)
        val listOfWalkable:Set<Byte> = setOf(1,2,3,4)
    }

}