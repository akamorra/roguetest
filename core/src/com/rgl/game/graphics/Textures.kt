package com.rgl.game.graphics

enum class Textures(val id: Byte) {
    STONE1(126),
    STONE2(125),
    STONE3(124),
    DOOR(123),
    STONEWALL(122),
    STONEWALL_TRANSPARENT_W(121),
    STONEWALL_TRANSPARENT_E(120),
    STONEWALL_TRANSPARENT(119),
    STONEWALL_TOP_TRANSPARENT(118),
    EMPTY_TEXTURE(127);
    companion object {
        val listOfObstacles:Set<Byte> = setOf(122,121,120,119,118)
        val listOfWalls:Set<Byte> =setOf(122,121,120,119,118)
        val listOfTransparents:Set<Byte> = setOf(121,120,119,118)
        val listOfWalkable:Set<Byte> = setOf(126,125,124,123)
    }

}