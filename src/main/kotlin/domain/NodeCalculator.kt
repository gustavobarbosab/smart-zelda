package domain

import kotlin.math.abs

class NodeCalculator(
    val node: Node,
    val father: Node?,
    private val goal: Pair<Int, Int>
) {
    private var gFunction: Int = node.type.cost
    private var hFunction: Int = calculateDistanceToGoal()

    val heuristic
        get() = gFunction + hFunction

    private fun calculateDistanceToGoal(): Int {
        val currentNodePosition = node.position
        val absoluteX = abs(goal.first - currentNodePosition.first)
        val absoluteY = abs(goal.second - currentNodePosition.second)
        return absoluteX + absoluteY
    }
}