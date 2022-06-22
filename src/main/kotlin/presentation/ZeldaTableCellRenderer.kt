package presentation

import java.awt.Component
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class ZeldaTableCellRenderer : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val positionType = value as ZeldaCellModel
        super.setBackground(positionType.backgroundColor)
        val tableColumn = table.columnModel.getColumn(column)
        tableColumn.minWidth = 20
        tableColumn.maxWidth = 20
        tableColumn.preferredWidth = 20
        return this
    }
}