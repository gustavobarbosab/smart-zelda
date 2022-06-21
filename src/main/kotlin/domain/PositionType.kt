package domain

import data.entitiy.PositionTypeEnum

sealed class PositionType(
    val cost: Int = 0,
    val enum: PositionTypeEnum,
    val ignoreToExpand: Boolean = false
) {
    object Grass : PositionType(10, PositionTypeEnum.GRASS)
    object Sand : PositionType(20, PositionTypeEnum.SAND)
    object Forest : PositionType(100, PositionTypeEnum.FOREST)
    object Mountain : PositionType(150, PositionTypeEnum.MOUNTAIN)
    object Water : PositionType(180, PositionTypeEnum.WATER)
    object FirstDungeon : PositionType(enum = PositionTypeEnum.FIRST_DUNGEON, ignoreToExpand = true)
    object SecondDungeon : PositionType(enum = PositionTypeEnum.SECOND_DUNGEON, ignoreToExpand = true)
    object ThirdDungeon : PositionType(enum = PositionTypeEnum.THIRD_DUNGEON, ignoreToExpand = true)
    object Link : PositionType(enum = PositionTypeEnum.LINK, ignoreToExpand = true)
    object LostWoods : PositionType(enum = PositionTypeEnum.LOST_WOODS, ignoreToExpand = true)
    object Pendant : PositionType(enum = PositionTypeEnum.PENDANT, ignoreToExpand = true)
}