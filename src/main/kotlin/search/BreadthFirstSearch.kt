// package search
//
// import domain.Node
// import domain.Path
// import domain.PathToGoal
// import domain.calculator.NodeAStarCalculator
// import domain.calculator.NodeCalculator
// import domain.calculator.NodeUniformCostCalculator
// import domain.exceptions.ImpossibleGoalException
// import domain.mapper.mapToNodeGenerator
//
// open abstract class BreadthFirstSearch<T : NodeCalculator>(private val board: List<List<Node>>) {
//
//     private var nonVisitedNodes = mutableListOf<T>()
//     private val visitedNodes = mutableListOf<T>()
//
//     abstract fun createNodeCalculator(son: Node, father: Node?, accumulatedCost: Int): T
//
//     fun findCostToGoal(currentPosition: Pair<Int, Int>, goal: Pair<Int, Int>): Int {
//         resetSearch()
//
//         // Initializing non visited nodes
//         val currentNode =  board[currentPosition.first][currentPosition.second]
//         val currentNodeCalculator = createNodeCalculator(
//             son = currentNode,
//             father = null,
//             accumulatedCost = 0
//         )
//         nonVisitedNodes.add(currentNodeCalculator)
//
//         while (nonVisitedNodes.isNotEmpty()) {
//             val nodeCalculatorToVisit = readTheLowestCostInNonVisitedNodes()
//             visitedNodes.add(nodeCalculatorToVisit)
//
//             nodeCalculatorToVisit.node.getNeighbors().forEach { neighbor ->
//                 val neighborNode = board[neighbor.first][neighbor.second]
//                 val neighborCalculator = createNodeCalculator(
//                     son = neighborNode,
//                     father = nodeCalculatorToVisit,
//                     accumulatedCost = nodeCalculatorToVisit.gFunction
//                 )
//
//                 // Neighbor is the goal?
//                 if (neighbor == goal) {
//                     println("Encontramos o objetivo:")
//                     visitedNodes.add(neighborCalculator)
//                     val greatPath = createGreatPath(neighborCalculator, goal)
//                     val visitedPath = Path(visitedNodes.map { it.node }, visitedNodes.sumOf { it.node.type.cost })
//                     return PathToGoal(
//                         pathGreat = greatPath,
//                         pathComplete = visitedPath
//                     )
//                 }
//
//                 if (neighborCalculator.node.type.ignoreToExpand) {
//                     return@forEach
//                 }
//
//                 // Exists a node do not visited better than this neighbor?
//                 val nonVisitedItemLessThanNeighbor = nonVisitedNodes.firstOrNull { nonVisitedItem ->
//                     val nonVisitedPosition = nonVisitedItem.node.position
//                     nonVisitedPosition == neighbor && nonVisitedItem.heuristic <= neighborCalculator.heuristic
//                 }
//
//                 // If exists, skip this neighbor
//                 if (nonVisitedItemLessThanNeighbor != null) {
//                     return@forEach
//                 }
//
//                 // Exists a visited node better than this neighbor?
//                 val visitedItemLessThanNeighbor = visitedNodes.firstOrNull { visitedItem ->
//                     val visitedPosition = visitedItem.node.position
//                     visitedPosition == neighbor && visitedItem.heuristic <= neighborCalculator.heuristic
//                 }
//
//                 // If not exists, add this neighbor to be visited
//                 if (visitedItemLessThanNeighbor == null) {
//                     nonVisitedNodes.add(neighborCalculator)
//                 }
//             }
//         }
//
//         throw ImpossibleGoalException("This goal can not be found")
//     }
//
//     private fun readTheLowestCostInNonVisitedNodes(): T {
//         val nodeToVisit = nonVisitedNodes.minByOrNull { it.calcCost() } ?: throw Exception()
//         nonVisitedNodes.remove(nodeToVisit)
//         return nodeToVisit
//     }
//
//     private fun resetSearch() {
//         nonVisitedNodes.clear()
//         visitedNodes.clear()
//     }
//
//     private fun createGreatPath(
//         goalCalculator: T,
//         goal: Pair<Int, Int>
//     ): Path {
//         var father = goalCalculator.father
//         val greatPath = mutableListOf(board[goal.first][goal.second])
//         while (father != null) {
//             val fatherPosition = father.node.position
//             greatPath.add(board[fatherPosition.first][fatherPosition.second])
//             father = father.father
//         }
//         greatPath.reverse()
//
//         return Path(greatPath, goalCalculator.calcCost())
//     }
// }