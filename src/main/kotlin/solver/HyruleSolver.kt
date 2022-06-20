package solver

import domain.HyruleMainPositions
import domain.Node
import domain.PositionType
import search.AStarSearch

class HyruleSolver(private val board: List<List<Node>>) {

    private val mainPositions = HyruleMainPositions()

    init {
        mapPositions()
        mainPositions.printResume()
    }

    fun goToNearestDungeon() {
        // TODO here we need decided what is the best dungeon
        val bestDungeonToGo = mainPositions.firstDungeon

        val search = AStarSearch(board)
        val linkHouse = mainPositions.linkHouse
        val pathToSolution = search.findGreatPath(linkHouse, bestDungeonToGo)

        pathToSolution.forEach {
            print("${it.position} | ")
        }
    }

    fun goToLostWoods() {
        TODO()
    }

    private fun mapPositions() {
        board.forEach { row ->
            row.forEach { item ->
                savePosition(item)
                print(item)
            }
            println()
        }
    }

    private fun savePosition(item: Node) {
        when (item.type) {
            PositionType.FirstDungeon -> mainPositions.firstDungeon = item.position
            PositionType.SecondDungeon -> mainPositions.secondDungeon = item.position
            PositionType.ThirdDungeon -> mainPositions.thirdDungeon = item.position
            PositionType.Link -> mainPositions.linkHouse = item.position
            PositionType.LostWoods -> mainPositions.lostWoods = item.position
            else -> {
            }
        }
    }

    private fun print(node: Node) =
        print("\t ${node.position}-${node.type.enum.name.subSequence(0..3)} |")

    companion object {
        sealed class NearestDungeon {
            object First : NearestDungeon()
            object Second : NearestDungeon()
            object Third : NearestDungeon()
        }
    }
}