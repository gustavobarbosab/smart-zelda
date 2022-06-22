package presentation

import javax.swing.SwingUtilities
import javax.swing.table.AbstractTableModel

class ZeldaTableAdapter : AbstractTableModel() {

    private val board: MutableList<List<ZeldaCellModel>> = mutableListOf()

    fun updateToVisited(position: Pair<Int, Int>) {
        SwingUtilities.invokeLater {
            board[position.first][position.second].thisNodeWasVisited()
            fireTableCellUpdated(position.first, position.second)
        }
    }

    fun reloadBoard(newBoard: List<List<ZeldaCellModel>>) {
        board.clear()
        board.addAll(newBoard)
        SwingUtilities.invokeLater {
            fireTableStructureChanged()
        }
    }

    override fun getColumnClass(columnIndex: Int): Class<*> = ZeldaCellModel::class.java

    override fun getRowCount(): Int = board.size

    override fun getColumnCount(): Int = board.size

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = board[rowIndex][columnIndex]

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true
}