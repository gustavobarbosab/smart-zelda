package domain

data class Node(
    val position: Pair<Int, Int>,
    val type: PositionType,
    val maxMatrixSize: Pair<Int, Int>
) {

    fun getNeighbors(): List<Pair<Int, Int>> {
        val maxX = maxMatrixSize.first
        val maxY = maxMatrixSize.second

        val currentX = position.first
        val currentY = position.second

        val neighbors = mutableListOf<Pair<Int, Int>>()

        // generating axis -x values
        var newValue = currentX.dec()
        if (newValue >= 0) {
            neighbors.add(Pair(newValue, currentY))
        }

        // generating axis +x values
        newValue = currentX.inc()
        if (newValue < maxX) {
            neighbors.add(Pair(newValue, currentY))
        }

        // generating axis -y values
        newValue = currentY.dec()
        if (newValue >= 0) {
            neighbors.add(Pair(currentX, newValue))
        }

        // generating axis +y values
        newValue = currentY.inc()
        if (newValue < maxY) {
            neighbors.add(Pair(currentX, newValue))
        }

        // log("Neighboars generate: $neighbors")
        return neighbors
    }
}