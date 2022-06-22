import data.BoardGenerator
import domain.DungeonFound
import domain.NearestDungeon
import domain.Path
import movements.NextMoveDungeon
import movements.NextMoveHyrule
import presentation.ZeldaCellModel
import presentation.ZeldaMapFrame

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

    var hyruleMoveState: NextMoveHyrule = NextMoveHyrule.FindFirstNearestDungeon
    val hyruleBoard = ZeldaMapFrame("Hyrule Map")
    hyruleBoard.setButtonText("Procurar primeira dungeon")
    hyruleBoard.loadMap(boardGenerator.hyruleBoardModel)
    hyruleBoard.isVisible = true

    val dungeonOneBoard = ZeldaMapFrame("Dungeon One Map")
    setupDungeon(dungeonOneBoard, smartCharacter, boardGenerator.dungeonOneBoardModel, NearestDungeon.First)

    val dungeonTwoBoard = ZeldaMapFrame("Dungeon Two Map")
    setupDungeon(dungeonTwoBoard, smartCharacter, boardGenerator.dungeonTwoBoardModel, NearestDungeon.Second)

    val dungeonThreeBoard = ZeldaMapFrame("Dungeon Three Map")
    setupDungeon(dungeonThreeBoard, smartCharacter, boardGenerator.dungeonThreeBoardModel, NearestDungeon.Third)

    hyruleBoard.listener = {
        val path: Path? = when (hyruleMoveState) {
            NextMoveHyrule.FindFirstNearestDungeon,
            NextMoveHyrule.FindSecondNearestDungeon,
            NextMoveHyrule.FindThirdNearestDungeon -> {
                val dungeonFound: DungeonFound = smartCharacter.findNearestDungeon()
                val dungeonMap = when (dungeonFound.nearestDungeon) {
                    NearestDungeon.First -> dungeonOneBoard
                    NearestDungeon.Second -> dungeonTwoBoard
                    NearestDungeon.Third -> dungeonThreeBoard
                }
                hyruleBoard.updateBoardWithVisitedNodes(dungeonFound.greatPath)
                dungeonMap.isVisible = true
                dungeonFound.greatPath
            }
            NextMoveHyrule.GoToLinkHouse -> smartCharacter.goBackToLinkHouse()
            NextMoveHyrule.GoToLostWoods -> smartCharacter.goToLostWoods()
            else -> null
        }

        path?.let {
            hyruleBoard.setButtonText(hyruleMoveState.buttonText)
            hyruleBoard.updateBoardWithVisitedNodes(path)
            hyruleBoard.setPathCost(it.nodes.last().position.toString(), path.totalCost.toString())
            hyruleBoard.setTotalCost(smartCharacter.totalCost.toString())
        }
        hyruleMoveState = hyruleMoveState.nextMove
    }
}

private fun setupDungeon(
    board: ZeldaMapFrame,
    smartCharacter: SmartCharacter,
    boardMatrix: List<List<ZeldaCellModel>>,
    nearestDungeon: NearestDungeon
) {
    var totalCost = 0
    board.setButtonText("Find pendant")
    board.loadMap(boardMatrix)
    var state: NextMoveDungeon = NextMoveDungeon.FindPendant
    board.listener = {
        board.setButtonText(state.textButton)
        when (state) {
            NextMoveDungeon.DungeonFinished -> board.isVisible = false
            NextMoveDungeon.FindPendant -> {
                val path = smartCharacter.findPendant(nearestDungeon)
                board.updateBoardWithVisitedNodes(path)
                board.setPathCost(path.nodes.last().position.toString(), path.totalCost.toString())
                totalCost += path.totalCost
                board.setTotalCost(totalCost.toString())
            }
            NextMoveDungeon.GoToTheEntry -> {
                board.setTotalCost((totalCost * 2).toString())
            }
        }
        state = state.nextMove
    }
}