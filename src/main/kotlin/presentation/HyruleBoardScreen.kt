package presentation

import domain.Node
import domain.PositionType
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.table.TableColumn

class HyruleBoardScreen(private val board: List<List<Node>>) {

    fun createScreen() {
        val frame = JFrame()
        frame.title = "Hyrule Board"

        val hyruleTableModel = HyruleTableAdapter(board)
        val table = JTable(hyruleTableModel)

        val cellRenderer = HyruleTableCellRenderer()
        table.setDefaultRenderer(PositionType::class.java, cellRenderer)

        table.tableHeader.isVisible = false


        defineColumnSize(table)
        frame.add(JScrollPane(table))

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.preferredSize = Dimension(500, 500)
        frame.setLocationRelativeTo(null)
        frame.pack()

        frame.isVisible = true
    }

    private fun defineColumnSize(table: JTable) {
        val boardSize = board.size

        for (index in 0 until boardSize) {
            val column: TableColumn = table.columnModel.getColumn(index)
            column.minWidth = 20
            column.maxWidth = 20
            column.preferredWidth = 20
        }
    }
}