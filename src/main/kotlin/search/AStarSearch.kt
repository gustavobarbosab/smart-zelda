package search

import domain.Node
import domain.NodeCalculator
import domain.mapper.mapToNodeGenerator
import domain.exceptions.ImpossibleGoalException

class AStarSearch(private val board: List<List<Node>>) {

    private var nonVisitedNodes = mutableListOf<NodeCalculator>()
    private val visitedNodes = mutableListOf<NodeCalculator>()
    private var greatPath = mutableListOf<NodeCalculator>()

    fun findGreatPath(currentPosition: Pair<Int, Int>, goal: Pair<Int, Int>): List<Node> {
        resetSearch()

        // Initializing non visited nodes
        val currentNodeCalculator = board[currentPosition.first][currentPosition.second].mapToNodeGenerator(
            goal = goal,
            father = null
        )
        nonVisitedNodes.add(currentNodeCalculator)

        while (nonVisitedNodes.isNotEmpty()) {
            val nodeCalculatorToVisit = readTheLowestHeuristicInNonVisitedNodes()
            visitedNodes.add(nodeCalculatorToVisit)
            updateGreatPath(nodeCalculatorToVisit)

            nodeCalculatorToVisit.node.getNeighbors().forEach { neighbor ->
                val neighborCalculator = board[neighbor.first][neighbor.second].mapToNodeGenerator(
                    goal = goal,
                    father = nodeCalculatorToVisit.node
                )

                // Neighbor is the goal?
                if (neighbor == goal) {
                    println("Encontramos o objetivo:")
                    val listToReturn = greatPath.map { it.node }.toMutableList()
                    listToReturn.add(neighborCalculator.node)
                    return listToReturn
                }

                // Exists a node do not visited better than this neighbor?
                val nonVisitedItemLessThanNeighbor = nonVisitedNodes.firstOrNull { nonVisitedItem ->
                    val nonVisitedPosition = nonVisitedItem.node.position
                    nonVisitedPosition == neighbor && nonVisitedItem.heuristic <= neighborCalculator.heuristic
                }

                // If exists, skip this neighbor
                if (nonVisitedItemLessThanNeighbor != null) {
                    return@forEach
                }

                // Exists a visited node better than this neighbor?
                val visitedItemLessThanNeighbor = visitedNodes.firstOrNull { visitedItem ->
                    val visitedPosition = visitedItem.node.position
                    visitedPosition == neighbor && visitedItem.heuristic <= neighborCalculator.heuristic
                }

                // If not exists, add this neighbor to be visited
                if (visitedItemLessThanNeighbor == null) {
                    nonVisitedNodes.add(neighborCalculator)
                }
            }
        }

        throw ImpossibleGoalException("This goal can not be found")
    }

    private fun readTheLowestHeuristicInNonVisitedNodes(): NodeCalculator {
        val nodeToVisit = nonVisitedNodes.minByOrNull { it.heuristic } ?: throw Exception()
        nonVisitedNodes.remove(nodeToVisit)
        return nodeToVisit
    }

    private fun resetSearch() {
        nonVisitedNodes.clear()
        visitedNodes.clear()
    }

    private fun updateGreatPath(nodeCalculatorToVisit: NodeCalculator) {
        val lastGreatFather = greatPath.lastOrNull()
        val visitFather = nodeCalculatorToVisit.father
        if (visitFather != lastGreatFather?.node) {
            val visitFatherPosition = greatPath.indexOf(lastGreatFather)
            greatPath = greatPath.subList(0, visitFatherPosition-1)
        }
        greatPath.add(nodeCalculatorToVisit)
    }
}
