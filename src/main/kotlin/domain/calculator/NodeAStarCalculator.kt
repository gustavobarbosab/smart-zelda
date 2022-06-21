package domain.calculator

import domain.Node
import kotlin.math.abs

data class NodeAStarCalculator(
    override val node: Node,
    override val father: NodeAStarCalculator?,
    private val accumulatedCost: Int,
    private val goal: Pair<Int, Int>
) : NodeCalculator {

    var gFunction: Int = node.type.cost + accumulatedCost
    var hFunction: Int = calculateDistanceToGoal()

    private fun calculateDistanceToGoal(): Int {
        val currentNodePosition = node.position
        val absoluteX = abs(goal.first - currentNodePosition.first)
        val absoluteY = abs(goal.second - currentNodePosition.second)
        return absoluteX + absoluteY
    }

    override fun calcCost(): Int = gFunction + hFunction
}