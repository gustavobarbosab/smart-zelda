package search

import domain.Node
import domain.PathToGoal
import domain.exceptions.ImpossibleGoalException

class UniformCostSearch(private val board: List<List<Node>>) {

    private var nonVisitedNodes = mutableListOf<Node>()
    private val visitedNodes = mutableListOf<Node>()

    fun findPath(currentPosition: Pair<Int, Int>, goal: Pair<Int, Int>): PathToGoal {
        val currentNode = board[currentPosition.first][currentPosition.second]
        nonVisitedNodes.add(currentNode)

        while (nonVisitedNodes.isNotEmpty()) {
            val nodeToVisit = readTheLowestCostInNonVisitedNodes()
            visitedNodes.add(nodeToVisit)

            nodeToVisit.getNeighbors().forEach { neighborPosition ->
                val neighbor = board[neighborPosition.first][neighborPosition.second]

                if (neighborPosition == goal) {
                    visitedNodes.add(neighbor)

                    val totalCost = visitedNodes.sumOf { it.type.cost }

                    return TODO()
                }

                // Exists a node do not visited better than this neighbor?
                val nonVisitedItemLessThanNeighbor = nonVisitedNodes.firstOrNull { nonVisitedItem ->
                    val nonVisitedPosition = nonVisitedItem.position
                    nonVisitedPosition == neighborPosition && nonVisitedItem.type.cost <= neighbor.type.cost
                }

                // If exists, skip this neighbor
                if (nonVisitedItemLessThanNeighbor != null) {
                    return@forEach
                }

                // Exists a visited node better than this neighbor?
                val visitedItemLessThanNeighbor = visitedNodes.firstOrNull { visitedItem ->
                    val visitedPosition = visitedItem.position
                    visitedPosition == neighborPosition && visitedItem.type.cost <= neighbor.type.cost
                }

                // If not exists, add this neighbor to be visited
                if (visitedItemLessThanNeighbor == null) {
                    nonVisitedNodes.add(neighbor)
                }
            }
        }

        throw ImpossibleGoalException("This goal can not be found")
    }

    private fun readTheLowestCostInNonVisitedNodes(): Node {
        val nodeToVisit = nonVisitedNodes.minByOrNull { it.type.cost } ?: throw Exception()
        nonVisitedNodes.remove(nodeToVisit)
        return nodeToVisit
    }
}