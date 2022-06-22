import data.BoardGenerator

fun main(args: Array<String>) {

    val boardGenerator = BoardGenerator(
        hyruleFilename = "hyrule-board.json",
        firstDungeonFilename = "first-dungeon.json",
        secondDungeonFilename = "second-dungeon.json",
        thirdDungeonFilename = "third-dungeon.json"
    )

    val smartCharacter = SmartCharacter(
        hyruleBoard = boardGenerator.hyruleBoard,
        firstDungeonBoard = boardGenerator.firstDungeonBoard,
        secondDungeonBoard = boardGenerator.secondDungeonBoard,
        thirdDungeonBoard = boardGenerator.thirdDungeonBoard
    )

    smartCharacter
        .findFirstPendant()
        .findSecondPendant()
        .findThirdPendant()
        .goBackToLinksHouse()
        .goToLostWoods()
}