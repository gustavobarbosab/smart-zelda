package domain

import data.entitiy.PositionTypeEnum

sealed class PositionType(
    val cost: Int = -1,
    val enum: PositionTypeEnum
) {
    object Grass : PositionType(10, PositionTypeEnum.GRASS)
    object Sand : PositionType(20, PositionTypeEnum.SAND)
    object Forest : PositionType(100, PositionTypeEnum.FOREST)
    object Mountain : PositionType(150, PositionTypeEnum.MOUNTAIN)
    object Water : PositionType(180, PositionTypeEnum.WATER)
    object FirstDungeon : PositionType(enum = PositionTypeEnum.FIRST_DUNGEON)
    object SecondDungeon : PositionType(enum = PositionTypeEnum.SECOND_DUNGEON)
    object ThirdDungeon : PositionType(enum = PositionTypeEnum.THIRD_DUNGEON)
    object Link : PositionType(enum = PositionTypeEnum.LINK)
    object LostWoods : PositionType(enum = PositionTypeEnum.LOST_WOODS)
    object Pendant : PositionType(enum = PositionTypeEnum.PENDANT)
}