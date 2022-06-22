import common.log
import domain.NearestDungeon.First
import domain.NearestDungeon.Second
import domain.NearestDungeon.Third
import domain.Node
import solver.DungeonSolver
import solver.HyruleSolver

class SmartCharacter(
    hyruleBoard: List<List<Node>>,
    firstDungeonBoard: List<List<Node>>,
    secondDungeonBoard: List<List<Node>>,
    thirdDungeonBoard: List<List<Node>>
) {
    private val hyruleSolver = HyruleSolver(hyruleBoard)
    private val firstDungeonSolver = DungeonSolver(firstDungeonBoard)
    private val secondDungeonSolver = DungeonSolver(secondDungeonBoard)
    private val thirdDungeonSolver = DungeonSolver(thirdDungeonBoard)

    private var totalCost = 0

    fun findFirstPendant() = findPendant()

    fun findSecondPendant() = findPendant()

    fun findThirdPendant() = findFirstPendant()

    private fun findPendant() = apply {
        val dungeonFound = hyruleSolver.goToNearestDungeon()

        val pathToSolution = when (dungeonFound.nearestDungeon) {
            First -> firstDungeonSolver.findPendant()
            Second -> secondDungeonSolver.findPendant()
            Third -> thirdDungeonSolver.findPendant()
        }

        totalCost += pathToSolution.totalCost

        println("\nTotal path cost: ${pathToSolution.totalCost}")
        println("Great path cost: ${pathToSolution.totalCost}")
        println("Great path to dungeon ${dungeonFound.position}")
        pathToSolution.path.forEach {
            print("${it.position} | ")
        }
        println()
    }

    fun goToLostWoods() = apply {
        val pathToSolution = hyruleSolver.goToLostWoods()
        println("Great path cost: ${pathToSolution.totalCost}")
        println("Great path to lost woods")
        pathToSolution.path.forEach {
            print("${it.position} | ")
        }

        totalCost += pathToSolution.totalCost

        log("Total cost considering the path traveled in Hyrule and three dungeons: $totalCost")
    }
}