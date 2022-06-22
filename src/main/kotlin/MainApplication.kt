import data.BoardGenerator
import domain.DungeonFound
import domain.NearestDungeon
import domain.Path
import movements.NextMoveDungeon
import movements.NextMoveHyrule
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

    var dungeonOneInitialState = NextMoveDungeon.FindPendant
    val dungeonOneBoard = ZeldaMapFrame("Dungeon One Map")
    dungeonOneBoard.setButtonText("Find pendant")
    dungeonOneBoard.loadMap(boardGenerator.dungeonOneBoardModel)


    hyruleBoard.listener = {
        val path: Path? = when (hyruleMoveState) {
            NextMoveHyrule.FindFirstNearestDungeon,
            NextMoveHyrule.FindSecondNearestDungeon,
            NextMoveHyrule.FindThirdNearestDungeon -> {
                val dungeonFound: DungeonFound = smartCharacter.findNearestDungeon()
                val dungeonMap = when (dungeonFound.nearestDungeon) {
                    NearestDungeon.First -> dungeonOneBoard
                    NearestDungeon.Second -> dungeonOneBoard
                    NearestDungeon.Third -> dungeonOneBoard
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