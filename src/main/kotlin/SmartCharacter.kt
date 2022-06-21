import domain.Node
import solver.DungeonSolver
import solver.HyruleSolver
import solver.HyruleSolver.Companion.NearestDungeon.First
import solver.HyruleSolver.Companion.NearestDungeon.Second
import solver.HyruleSolver.Companion.NearestDungeon.Third

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

    fun findFirstPendant() = findPendant()

    fun findSecondPendant() = findPendant()

    fun findThirdPendant() = findFirstPendant()

    private fun findPendant() = apply {
        when (hyruleSolver.goToNearestDungeon()) {
            First -> firstDungeonSolver.findPendant()
            Second -> secondDungeonSolver.findPendant()
            Third -> thirdDungeonSolver.findPendant()
        }
    }

    fun goToLostWoods() = apply {
        hyruleSolver.goToLostWoods()
    }
}