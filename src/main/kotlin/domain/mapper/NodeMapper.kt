package domain.mapper

import domain.Node
import domain.calculator.NodeAStarCalculator

fun Node.mapToNodeGenerator(goal: Pair<Int, Int>, accumulatedCost: Int, father: NodeAStarCalculator?): NodeAStarCalculator =
    NodeAStarCalculator(this, father, accumulatedCost, goal)

