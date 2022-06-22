package domain

data class Path(
    val nodes: List<Node>,
    val totalCost: Int,
)