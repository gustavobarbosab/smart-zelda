package data

import common.log
import domain.Node
import domain.PositionType
import data.entitiy.PositionTypeEnum
import data.entitiy.PositionTypeEnum.FIRST_DUNGEON
import data.entitiy.PositionTypeEnum.FOREST
import data.entitiy.PositionTypeEnum.GRASS
import data.entitiy.PositionTypeEnum.LINK
import data.entitiy.PositionTypeEnum.LOST_WOODS
import data.entitiy.PositionTypeEnum.MOUNTAIN
import data.entitiy.PositionTypeEnum.SAND
import data.entitiy.PositionTypeEnum.SECOND_DUNGEON
import data.entitiy.PositionTypeEnum.THIRD_DUNGEON
import data.entitiy.PositionTypeEnum.WATER
import presentation.ZeldaCellModel

class BoardGenerator(
    private val hyruleFilename: String,
    private val firstDungeonFilename: String,
    private val secondDungeonFilename: String,
    private val thirdDungeonFilename: String,
) {

    val hyruleBoard: List<List<Node>> by lazy {
        log("Generating Hyrule board")
        readAndMapToNode(hyruleFilename)
    }

    val firstDungeonBoard: List<List<Node>> by lazy {
        log("Generating first dungeon board")
        readAndMapToNode(firstDungeonFilename)
    }

    val secondDungeonBoard: List<List<Node>> by lazy {
        log("Generating second dungeon board")
        readAndMapToNode(secondDungeonFilename)
    }

    val thirdDungeonBoard: List<List<Node>> by lazy {
        log("Generating third dungeon board")
        readAndMapToNode(thirdDungeonFilename)
    }

    val hyruleBoardModel
        get() = mapToModel(hyruleBoard)

    val dungeonOneBoardModel
        get() = mapToModel(firstDungeonBoard)

    val dungeonTwoBoardModel
        get() = mapToModel(secondDungeonBoard)

    val dungeonThreeBoardModel
        get() = mapToModel(thirdDungeonBoard)

    private fun mapToModel(board: List<List<Node>>) = board.map { row -> row.map { ZeldaCellModel(it.type.color) } }

    private fun readAndMapToNode(filename: String): List<List<Node>> {
        val board = FileReader(filename).read<ArrayList<ArrayList<String>>>()
        return board.mapIndexed { column, line ->
            line.mapIndexed { row, enum ->
                mapToNode(
                    Pair(column, row),
                    Pair(board.size, line.size),
                    enum
                )
            }
        }
    }

    private fun mapToNode(
        position: Pair<Int, Int>,
        maxSize: Pair<Int, Int>,
        positionTypeEnum: String
    ): Node {
        val positionType = when (PositionTypeEnum.valueOf(positionTypeEnum)) {
            GRASS -> PositionType.Grass
            SAND -> PositionType.Sand
            FOREST -> PositionType.Forest
            MOUNTAIN -> PositionType.Mountain
            WATER -> PositionType.Water
            FIRST_DUNGEON -> PositionType.FirstDungeon
            SECOND_DUNGEON -> PositionType.SecondDungeon
            THIRD_DUNGEON -> PositionType.ThirdDungeon
            LINK -> PositionType.Link
            LOST_WOODS -> PositionType.LostWoods
            PositionTypeEnum.PENDANT -> PositionType.Pendant
            PositionTypeEnum.D -> PositionType.Dark
            PositionTypeEnum.L -> PositionType.Light
            PositionTypeEnum.SWORD -> PositionType.Sword
        }
        return Node(position, positionType, maxSize)
    }
}