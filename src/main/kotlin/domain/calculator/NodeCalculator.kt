package domain.calculator

import domain.Node

interface NodeCalculator {
    val node: Node
    val father: NodeCalculator?
    fun calcCost(): Int
}