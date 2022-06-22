package presentation

import java.awt.Color

class ZeldaCellModel(
    private var _backgroundColor: Color,
    private var visited: Boolean = false
) {
    val backgroundColor: Color
        get() = if (visited) Color.CYAN else _backgroundColor

    fun thisNodeWasVisited() {
        visited = true
    }
}