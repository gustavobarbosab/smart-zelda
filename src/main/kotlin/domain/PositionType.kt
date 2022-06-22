package domain

import data.entitiy.PositionTypeEnum
import data.entitiy.PositionTypeEnum.*
import java.awt.Color

sealed class PositionType(
    val cost: Int = 0,
    val enum: PositionTypeEnum,
    val color: Color,
    val ignoreToExpand: Boolean = false
) {
    object Grass : PositionType(10, GRASS, Color(145, 208, 80))
    object Sand : PositionType(20, SAND, Color(196, 188, 150))
    object Forest : PositionType(100, FOREST, Color(0, 175, 80))
    object Mountain : PositionType(150, MOUNTAIN, Color(147, 138, 84))
    object Water : PositionType(180, WATER, Color(83, 142, 211))
    object FirstDungeon : PositionType(enum = FIRST_DUNGEON, ignoreToExpand = true, color = Color(255, 0, 0))
    object SecondDungeon : PositionType(enum = SECOND_DUNGEON, ignoreToExpand = true, color = Color(255, 234, 0))
    object ThirdDungeon : PositionType(enum = THIRD_DUNGEON, ignoreToExpand = true, color = Color(255, 153, 0))
    object Link : PositionType(enum = LINK, ignoreToExpand = true, color = Color(0, 255, 196))
    object LostWoods : PositionType(enum = LOST_WOODS, ignoreToExpand = true, color = Color(0, 51, 6))
    object Pendant : PositionType(enum = PENDANT, ignoreToExpand = true, color = Color(120, 70, 208))
    object Dark : PositionType(100000000, D, Color.DARK_GRAY)
    object Light : PositionType(10, L, Color.GRAY)
}