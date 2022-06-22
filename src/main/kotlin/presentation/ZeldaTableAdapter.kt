package presentation

import javax.swing.table.AbstractTableModel

class ZeldaTableAdapter(val board: List<List<ZeldaCellModel>> = mutableListOf()) : AbstractTableModel() {

    override fun getColumnClass(columnIndex: Int): Class<*> = ZeldaCellModel::class.java

    override fun getRowCount(): Int = board.size

    override fun getColumnCount(): Int = board.size

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = board[rowIndex][columnIndex]

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true
}