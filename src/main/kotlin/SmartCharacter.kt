import common.log
import domain.NearestDungeon.First
import domain.NearestDungeon.Second
import domain.NearestDungeon.Third
import domain.Node
import presentation.ZeldaBoardScreen
import presentation.ZeldaCellModel
import solver.DungeonSolver
import solver.HyruleSolver
import java.util.Timer
import java.util.TimerTask
import javax.swing.SwingUtilities

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
    private val board = ZeldaBoardScreen()

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

        if (dungeonFound.nearestDungeon == First) {
            SwingUtilities.invokeLater {
                // board.createScreen()
                val items = firstDungeonBoard.map { row -> row.map { ZeldaCellModel(it.type.color) } }
                board.updateTable("First Dungeon",items)
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        board.updateBoardWithVisitedNodes(pathToSolution)
                    }
                }, 3000)
            }
        }

        totalCost += 2 * pathToSolution.totalCost

        println("\n\nGreat path to dungeon ${dungeonFound.position}")
        println("Great path cost to dungeon: ${dungeonFound.greatPath.totalCost}")
        print("Path traveled in Hyrule: ")
        dungeonFound.greatPath.nodes.forEach {
            print("${it.position} | ")
        }

        println("\nGreat path cost inside dungeon: ${pathToSolution.totalCost}")
        print("Path traveled in Dungeon: ")
        pathToSolution.nodes.forEach {
            print("${it.position} | ")
        }
        println("\n-------------------------------------------------------")
    }

    fun goBackToLinksHouse() = apply {
        val pathToSolution = hyruleSolver.goBackToLinksHouse()
        println("\n\nGreat path cost: ${pathToSolution.totalCost}")
        println("Great path back to link house")
        pathToSolution.nodes.forEach {
            print("${it.position} | ")
        }
        println("\n-------------------------------------------------------")
        totalCost += pathToSolution.totalCost
    }

    fun goToLostWoods() = apply {
        val pathToSolution = hyruleSolver.goToLostWoods()
        println("\n\nGreat path cost: ${pathToSolution.totalCost}")
        println("Great path to lost woods")
        pathToSolution.nodes.forEach {
            print("${it.position} | ")
        }
        println("\n-------------------------------------------------------")

        totalCost += pathToSolution.totalCost

        println()
        log("Total cost considering the path traveled in Hyrule and three dungeons: $totalCost")
    }
}