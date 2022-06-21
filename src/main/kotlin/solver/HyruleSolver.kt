package solver

import domain.HyruleMainPositions
import domain.Node
import domain.PositionType
import domain.exceptions.AllDungeonsVisitedException
import search.AStarSearch
import search.UniformCostSearch
import solver.HyruleSolver.Companion.NearestDungeon.*
import kotlin.math.cos

class HyruleSolver(private val board: List<List<Node>>) {

    private val mainPositions = HyruleMainPositions()
    private val aStarHyruleSearch = AStarSearch(board)
    private val uniformSearchHyrule = UniformCostSearch(board)

    private val dungeonsToVisit = hashMapOf(
        First to false,
        Second to false,
        Third to false
    )

    init {
        mapPositions()
        mainPositions.printResume()
    }

    fun goToNearestDungeon() {
        val bestDungeonPosition = findNextDungeonToGo() ?: throw AllDungeonsVisitedException("All dungeons visited")

        val pathToSolution = aStarHyruleSearch.findGreatPath(mainPositions.currentPosition, bestDungeonPosition)

        println("\nTotal path cost: ${pathToSolution.pathComplete.totalCost}")
        println("Great path cost: ${pathToSolution.pathGreat.totalCost}")
        println("Great path to dungeon $bestDungeonPosition")
        pathToSolution.pathGreat.path.forEach {
            print("${it.position} | ")
        }
        println()
        mainPositions.currentPosition = bestDungeonPosition
    }

    private fun findNextDungeonToGo(): Pair<Int, Int>? {
        val nodesToVisit = mutableListOf<Pair<NearestDungeon, Int>>()

        if (dungeonsToVisit[First] == false) {
            val costToFirstDungeon = uniformSearchHyrule.findCostToGoal(
                mainPositions.currentPosition,
                mainPositions.firstDungeon
            )
            nodesToVisit.add(Pair(First, costToFirstDungeon))
        }

        if (dungeonsToVisit[Second] == false) {
            val costToSecondDungeon = uniformSearchHyrule.findCostToGoal(
                mainPositions.currentPosition,
                mainPositions.secondDungeon
            )
            nodesToVisit.add(Pair(Second, costToSecondDungeon))
        }

        if (dungeonsToVisit[Third] == false) {
            val costToThirdDungeon = uniformSearchHyrule.findCostToGoal(
                mainPositions.currentPosition,
                mainPositions.thirdDungeon
            )
            nodesToVisit.add(Pair(Third, costToThirdDungeon))
        }

        val lowerPosition = nodesToVisit.minByOrNull { it.second }
        val selectedDungeonPosition = when (lowerPosition?.first) {
            First -> mainPositions.firstDungeon
            Second -> mainPositions.secondDungeon
            Third -> mainPositions.thirdDungeon
            null -> null
        } ?: return null

        dungeonsToVisit[lowerPosition!!.first] = true
        return selectedDungeonPosition
    }

    fun goToLostWoods() {
        val pathToSolution = aStarHyruleSearch.findGreatPath(mainPositions.currentPosition, mainPositions.lostWoods)
        println("\nTotal path cost: ${pathToSolution.pathComplete.totalCost}")
        println("Great path cost: ${pathToSolution.pathGreat.totalCost}")
        println("Great path to lost woods")
        pathToSolution.pathGreat.path.forEach {
            print("${it.position} | ")
        }
    }

    private fun mapPositions() {
        board.forEach { row ->
            row.forEach { item ->
                savePosition(item)
                print(item)
            }
            println()
        }
        mainPositions.startCurrentPosition()
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