package presentation

import domain.Path
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.SwingUtilities

class ZeldaBoardScreen {

    private val frame: JFrame = JFrame()
    private val table = JTable()
    private val currentBoard: MutableList<List<ZeldaCellModel>> = mutableListOf()
    private val tableAdapter = ZeldaTableAdapter(currentBoard)

    init {
        createScreen()
    }

    private fun createScreen() {
        val cellRenderer = ZeldaTableCellRenderer()
        table.setDefaultRenderer(ZeldaCellModel::class.java, cellRenderer)
        table.tableHeader.isVisible = false
        table.model = tableAdapter
        frame.add(JScrollPane(table))
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.preferredSize = Dimension(INITIAL_SCREEN_DIMENSION, INITIAL_SCREEN_DIMENSION)
        frame.setLocationRelativeTo(null)
        frame.pack()
        frame.isVisible = true
    }

    fun updateTable(boardName: String, board: List<List<ZeldaCellModel>>) {
        frame.title = boardName
        currentBoard.clear()
        currentBoard.addAll(board)
        tableAdapter.fireTableStructureChanged()
    }

    fun updateBoardWithVisitedNodes(path: Path) {
        path.nodes.forEach { node ->
            val position = node.position
            val newItem = currentBoard[position.first][position.second]
            newItem.thisNodeWasVisited()
            SwingUtilities.invokeLater {
                tableAdapter.setValueAt(newItem, position.first, position.second)
                tableAdapter.fireTableStructureChanged()
            }
            Thread.sleep(DELAY_TO_MOVE)
        }
    }

    companion object {
        const val DELAY_TO_MOVE = 150L
        const val INITIAL_SCREEN_DIMENSION = 500
    }
}