package search

import domain.Node
import domain.calculator.NodeUniformCostCalculator
import domain.exceptions.ImpossibleGoalException

class UniformCostSearch(private val board: List<List<Node>>) {

    private var nonVisitedNodes = mutableListOf<NodeUniformCostCalculator>()
    private val visitedNodes = mutableListOf<NodeUniformCostCalculator>()

    fun findCostToGoal(currentPosition: Pair<Int, Int>, goal: Pair<Int, Int>): Int {
        resetSearch()

        val currentNode = board[currentPosition.first][currentPosition.second]
        val currentNodeCalculator = NodeUniformCostCalculator(
            currentNode,
            null
        )
        nonVisitedNodes.add(currentNodeCalculator)

        while (nonVisitedNodes.isNotEmpty()) {
            val nodeToVisit = readTheLowestCostInNonVisitedNodes()
            visitedNodes.add(nodeToVisit)

            nodeToVisit.node.getNeighbors().forEach { neighborPosition ->
                val neighbor = board[neighborPosition.first][neighborPosition.second]
                val neighborNodeCalculator = NodeUniformCostCalculator(
                    neighbor,
                    nodeToVisit
                )

                if (neighborPosition == goal) {
                    visitedNodes.add(neighborNodeCalculator)
                    return neighborNodeCalculator.calcCost()
                }

                if (neighbor.type.ignoreToExpand) {
                    return@forEach
                }

                // Exists a node do not visited better than this neighbor?
                val nonVisitedItemLessThanNeighbor = nonVisitedNodes.firstOrNull { nonVisitedItem ->
                    val nonVisitedPosition = nonVisitedItem.node.position
                    nonVisitedPosition == neighborPosition && nonVisitedItem.calcCost() <= neighborNodeCalculator.calcCost()
                }

                // If exists, skip this neighbor
                if (nonVisitedItemLessThanNeighbor != null) {
                    return@forEach
                }

                // Exists a visited node better than this neighbor?
                val visitedItemLessThanNeighbor = visitedNodes.firstOrNull { visitedItem ->
                    val visitedPosition = visitedItem.node.position
                    visitedPosition == neighborPosition && visitedItem.calcCost() <= neighborNodeCalculator.calcCost()
                }

                // If not exists, add this neighbor to be visited
                if (visitedItemLessThanNeighbor == null) {
                    nonVisitedNodes.add(neighborNodeCalculator)
                }
            }
        }

        throw ImpossibleGoalException("This goal can not be found")
    }

    private fun resetSearch() {
        nonVisitedNodes.clear()
        visitedNodes.clear()
    }

    private fun readTheLowestCostInNonVisitedNodes(): NodeUniformCostCalculator {
        val nodeToVisit = nonVisitedNodes.minByOrNull { it.calcCost() } ?: throw Exception()
        nonVisitedNodes.remove(nodeToVisit)
        return nodeToVisit
    }
}