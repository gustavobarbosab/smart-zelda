package domain

data class PathToGoal(
    val pathComplete: List<Node>,
    val greatPath: List<Node>,
    val costGreat: Int,
    val costComplete: Int
)