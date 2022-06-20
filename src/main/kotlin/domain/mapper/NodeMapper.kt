package domain.mapper

import domain.Node
import domain.NodeCalculator

fun Node.mapToNodeGenerator(goal: Pair<Int, Int>, father: Node?): NodeCalculator =
    NodeCalculator(this, father, goal)

