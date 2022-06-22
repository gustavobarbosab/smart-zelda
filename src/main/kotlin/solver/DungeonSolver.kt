package solver

import domain.Node
import domain.Path
import domain.PositionType
import search.AStarSearch

class DungeonSolver(private val board: List<List<Node>>) {

    var currentPosition = Pair(0, 0);
    var goal = Pair(0, 0);
    val search = AStarSearch(board)

    fun findPendant(): Path {
        board.forEach { line ->
            line.forEach { position ->
                if (position.type == PositionType.Link) {
                    currentPosition = position.position
                }
                if (position.type == PositionType.Pendant) {
                    goal = position.position
                }
            }
        }

        val pathToSolution = search.findGreatPath(currentPosition, goal)

        println("\nGreat path: cost ${pathToSolution.pathGreat.totalCost}")
        pathToSolution.pathGreat.path.forEach {
            print("${it.position} | ")
        }
        println("\nTotal path: cost ${pathToSolution.pathComplete.totalCost}")
        pathToSolution.pathComplete.path.forEach {
            print("${it.position} | ")
        }
        return pathToSolution.pathGreat
    }
}