package presentation

import domain.PositionType
import java.awt.Color
import java.awt.Component
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class HyruleTableCellRenderer : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable,
        value: Any?,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val positionType = value as PositionType
        super.setBackground(positionType.color)
        return this
    }
}