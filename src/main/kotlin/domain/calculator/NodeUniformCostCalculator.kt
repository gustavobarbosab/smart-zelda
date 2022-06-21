package domain.calculator

import domain.Node

data class NodeUniformCostCalculator(
    override val node: Node,
    override val father: NodeUniformCostCalculator?
) : NodeCalculator {
    private val accumulatedCost: Int = father?.calcCost() ?: 0

    override fun calcCost(): Int = accumulatedCost + node.type.cost
}