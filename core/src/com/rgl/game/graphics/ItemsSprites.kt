package com.rgl.game.graphics

enum class ItemsSprites(var id: Byte) {
    SWORD1(1),
    SWORD2(2),
    SWORD3(3),
    SWORD_UNKNOWN(10),
    AXE1(4),
    AXE2(5),
    AXE3(6),
    AXE_UNKNOWN(11),
    MACE1(7),
    MACE2(8),
    MACE3(9),
    MACE_UNKNOWN(12),
    CHESTPLATE1(13),
    CHESTPLATE2(14),
    CHESTPLATE3(15),
    ARMOR_UNKNOWN(19),
    BOOTS1(16),
    BOOTS2(17),
    BOOTS3(18),
    BOOTS_UNKNOWN(20),

    POT1(21),
    POT2(22),
    POT3(23),
    POT4(24),
    SCROLL(25),
    SWORD_TINT1(26),
    SWORD_TINT2(27),
    SWORD_TINT3(28),
    AXE_TINT1(29),
    AXE_TINT2(30),
    AXE_TINT3(31),
    MACE_TINT1(32),
    MACE_TINT2(33),
    MACE_TINT3(34),
    ARMOR_TINT1(35),
    ARMOR_TINT2(36),
    ARMOR_TINT3(37),
    BOOTS_TINT1(38),
    BOOTS_TINT2(39),
    BOOTS_TINT3(40);
    companion object {
        val listOfSwords: Set<Byte> = setOf(1, 2, 3)
        val listOfAxes: Set<Byte> = setOf(4, 5, 6)
        val listOfMaces: Set<Byte> = setOf(7, 8, 9)
        val listOfWeapons: Set<Byte> = listOfSwords + listOfAxes + listOfMaces
        val listOfUnknownWeapons: Set<Byte> = setOf(10, 11, 12)

        val listOfChestplates: Set<Byte> = setOf(13, 14, 15)
        val listOfBoots: Set<Byte> = setOf(16, 17, 18)
        val listOfArmor: Set<Byte> = listOfChestplates + listOfBoots
        val listOfUnknownArmor: Set<Byte> = setOf(19, 20)
        val listOfScrolls: Set<Byte> = setOf(25)
        val listOfPots: Set<Byte> = setOf(21, 22, 23, 24)

    }
}