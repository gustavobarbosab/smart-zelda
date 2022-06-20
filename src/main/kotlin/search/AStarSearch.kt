package search

import domain.Node
import domain.NodeAStarCalculator
import domain.mapper.mapToNodeGenerator
import domain.exceptions.ImpossibleGoalException

class AStarSearch(private val board: List<List<Node>>) {

    private var nonVisitedNodes = mutableListOf<NodeAStarCalculator>()
    private val visitedNodes = mutableListOf<NodeAStarCalculator>()
    private var greatPath = mutableListOf<NodeAStarCalculator>()

    fun findGreatPath(currentPosition: Pair<Int, Int>, goal: Pair<Int, Int>): List<Pair<Int, Int>> {
        resetSearch()

        // Initializing non visited nodes
        val currentNodeCalculator = board[currentPosition.first][currentPosition.second].mapToNodeGenerator(
            goal = goal,
            father = null,
            accumulatedCost = 0
        )
        nonVisitedNodes.add(currentNodeCalculator)

        while (nonVisitedNodes.isNotEmpty()) {
            val nodeCalculatorToVisit = readTheLowestHeuristicInNonVisitedNodes()
            visitedNodes.add(nodeCalculatorToVisit)

            nodeCalculatorToVisit.node.getNeighbors().forEach { neighbor ->
                val neighborCalculator = board[neighbor.first][neighbor.second].mapToNodeGenerator(
                    goal = goal,
                    father = nodeCalculatorToVisit,
                    accumulatedCost = nodeCalculatorToVisit.gFunction
                )

                // Neighbor is the goal?
                if (neighbor == goal) {
                    println("Encontramos o objetivo:")
                    return createGreatPath(nodeCalculatorToVisit, goal)
                }

                if (neighborCalculator.node.type.cost < 0) {
                    return@forEach
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

    private fun readTheLowestHeuristicInNonVisitedNodes(): NodeAStarCalculator {
        val nodeToVisit = nonVisitedNodes.minByOrNull { it.heuristic } ?: throw Exception()
        nonVisitedNodes.remove(nodeToVisit)
        return nodeToVisit
    }

    private fun resetSearch() {
        nonVisitedNodes.clear()
        visitedNodes.clear()
    }

    private fun createGreatPath(
        goalFatherCalculator: NodeAStarCalculator,
        goal: Pair<Int, Int>
    ): MutableList<Pair<Int, Int>> {
        var father = goalFatherCalculator.father
        val greatPath = mutableListOf<Pair<Int, Int>>()
        greatPath.add(goal)
        greatPath.add(goalFatherCalculator.node.position)
        while (father != null) {
            greatPath.add(father.node.position)
            father = father.father
        }
        greatPath.reverse()
        return greatPath
    }
}
