import common.log
import domain.DungeonFound
import domain.NearestDungeon.First
import domain.NearestDungeon.Second
import domain.NearestDungeon.Third
import domain.Node
import domain.Path
import domain.exceptions.AllDungeonsVisitedException
import domain.exceptions.PositionNotFoundException
import solver.DungeonSolver
import solver.HyruleSolver

class SmartCharacter(
    hyruleBoard: List<List<Node>>,
    val firstDungeonBoard: List<List<Node>>,
    secondDungeonBoard: List<List<Node>>,
    thirdDungeonBoard: List<List<Node>>
) {
    private val hyruleSolver = HyruleSolver(hyruleBoard)
    private val firstDungeonSolver = DungeonSolver(firstDungeonBoard)
    private val secondDungeonSolver = DungeonSolver(secondDungeonBoard)
    private val thirdDungeonSolver = DungeonSolver(thirdDungeonBoard)
    private var dungeonFound: DungeonFound? = null

    var totalCost = 0

    fun findNearestDungeon(): DungeonFound {
        dungeonFound = hyruleSolver.goToNearestDungeon()
        val greatPath = dungeonFound?.greatPath ?: throw NullPointerException("Dungeon not found")
        totalCost += greatPath.totalCost
        return dungeonFound!!
    }

    fun findPendant(): Path {
        val pathToSolution = when (dungeonFound?.nearestDungeon) {
            First -> firstDungeonSolver.findPendant()
            Second -> secondDungeonSolver.findPendant()
            Third -> thirdDungeonSolver.findPendant()
            else -> throw PositionNotFoundException("Dungeon not found")
        }
        totalCost += pathToSolution.totalCost
        return pathToSolution
    }

    fun goBackToLinkHouse(): Path {
        val pathToSolution = hyruleSolver.goBackToLinksHouse()
        totalCost += pathToSolution.totalCost
        return pathToSolution
    }

    fun goToLostWoods(): Path {
        val pathToSolution = hyruleSolver.goToLostWoods()
        totalCost += pathToSolution.totalCost
        return pathToSolution
    }
}