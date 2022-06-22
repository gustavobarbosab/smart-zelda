package solver

import domain.Node
import domain.Path
import domain.PositionType
import search.AStarSearch

class DungeonSolver(board: List<List<Node>>) {

    private var currentPosition = Pair(0, 0);
    private var goal = Pair(0, 0);
    private val search = AStarSearch(board)

    init {
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
    }

    fun findPendant(): Path = search.findGreatPath(currentPosition, goal).pathGreat
}