package domain

import kotlin.math.abs

class NodeAStarCalculator(
    val node: Node,
    val father: NodeAStarCalculator?,
    accumulatedCost: Int,
    private val goal: Pair<Int, Int>
) {
    var gFunction: Int = node.type.cost + accumulatedCost
    var hFunction: Int = calculateDistanceToGoal()

    val heuristic
        get() = gFunction + hFunction

    private fun calculateDistanceToGoal(): Int {
        val currentNodePosition = node.position
        val absoluteX = abs(goal.first - currentNodePosition.first)
        val absoluteY = abs(goal.second - currentNodePosition.second)
        return absoluteX + absoluteY
    }
}