import common.log
import domain.NearestDungeon.First
import domain.NearestDungeon.Second
import domain.NearestDungeon.Third
import domain.Node
import presentation.HyruleBoardScreen
import solver.DungeonSolver
import solver.HyruleSolver
import javax.swing.SwingUtilities




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
    val board = HyruleBoardScreen(firstDungeonBoard)

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

        println("\n\nGreat path to dungeon ${dungeonFound.position}")
        println("Great path cost to dungeon: ${dungeonFound.greatPath.totalCost}")
        print("Path traveled in Hyrule: ")
        dungeonFound.greatPath.path.forEach {
            print("${it.position} | ")
        }

        println("\nGreat path cost inside dungeon: ${pathToSolution.totalCost}")
        print("Path traveled in Dungeon: ")
        pathToSolution.path.forEach {
            print("${it.position} | ")
        }
        println("\n-------------------------------------------------------")
    }

    fun goToLostWoods() = apply {
        val pathToSolution = hyruleSolver.goToLostWoods()
        println("\n\nGreat path cost: ${pathToSolution.totalCost}")
        println("Great path to lost woods")
        pathToSolution.path.forEach {
            print("${it.position} | ")
        }
        println("\n-------------------------------------------------------")

        totalCost += pathToSolution.totalCost

        println()
        log("Total cost considering the path traveled in Hyrule and three dungeons: $totalCost")

        SwingUtilities.invokeLater {
            board.createScreen()
        }
    }
}