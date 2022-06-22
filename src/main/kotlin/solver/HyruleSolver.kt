package solver

import domain.DungeonFound
import domain.HyruleMainPositions
import domain.NearestDungeon
import domain.NearestDungeon.First
import domain.NearestDungeon.Second
import domain.NearestDungeon.Third
import domain.Node
import domain.Path
import domain.PositionType
import domain.exceptions.AllDungeonsVisitedException
import search.AStarSearch
import search.UniformCostSearch

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

    fun goToNearestDungeon(): DungeonFound {
        val bestDungeon = findNextDungeonToGo() ?: throw AllDungeonsVisitedException("All dungeons visited")

        val bestDungeonPosition = when (bestDungeon) {
            First -> mainPositions.firstDungeon
            Second -> mainPositions.secondDungeon
            Third -> mainPositions.thirdDungeon
        }

        val pathToSolution = aStarHyruleSearch.findGreatPath(mainPositions.currentPosition, bestDungeonPosition)
        mainPositions.currentPosition = bestDungeonPosition
        return DungeonFound(bestDungeon, pathToSolution.pathGreat, bestDungeonPosition)
    }

    private fun findNextDungeonToGo(): NearestDungeon? {
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
        lowerPosition?.first?.let { dungeonsToVisit[it] = true }
        return lowerPosition?.first
    }

    fun goToLostWoods(): Path {
        val pathToSolution = aStarHyruleSearch.findGreatPath(mainPositions.currentPosition, mainPositions.lostWoods)
        return pathToSolution.pathGreat
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
}