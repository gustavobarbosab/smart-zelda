package presentation

import domain.Node
import domain.PositionType
import javax.swing.table.AbstractTableModel

class HyruleTableAdapter(private val hyruleBoard: List<List<Node>>) : AbstractTableModel() {

    override fun getColumnClass(columnIndex: Int): Class<*> = PositionType::class.java

    override fun getRowCount(): Int = hyruleBoard.size

    override fun getColumnCount(): Int = hyruleBoard.size

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = hyruleBoard[rowIndex][columnIndex].type

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true
}