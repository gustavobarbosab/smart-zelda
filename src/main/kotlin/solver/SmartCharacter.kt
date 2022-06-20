package solver

import domain.Node
import solver.HyruleSolver.Companion.NearestDungeon
import solver.HyruleSolver.Companion.NearestDungeon.*

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

    fun findFirstPendant() = apply {
        val nearestDungeon = hyruleSolver.goToNearestDungeon()
        // findPendant(nearestDungeon)
    }

    fun findSecondPendant() = apply {

    }

    fun findThirdPendant() = apply {

    }

    fun goToLostWoods() = apply {
        hyruleSolver.goToLostWoods()
    }

    private fun findPendant(dungeon: NearestDungeon) {
        when (dungeon) {
            First -> firstDungeonSolver.findPendant()
            Second -> secondDungeonSolver.findPendant()
            Third -> thirdDungeonSolver.findPendant()
        }
    }
}