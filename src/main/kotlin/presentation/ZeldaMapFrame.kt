package presentation

import domain.Path
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTable

class ZeldaMapFrame(
    screenTitle: String
) {

    var listener: () -> Unit = {  }
    private val frame: JFrame = JFrame()
    private val table = JTable()
    private val panel = JPanel()
    private val button = JButton()
    private val totalCostText = JLabel()
    private val pathCostText = JLabel()
    private var tableAdapter = ZeldaTableAdapter()
    var isVisible: Boolean = false
        set(value) {
            field = value
            frame.isVisible = isVisible
        }

    init {
        frame.title = screenTitle
        createScreen()
    }

    private fun createScreen() {
        setupTable()
        setupButton()
        setupFrame()
    }

    fun loadMap(board: List<List<ZeldaCellModel>>) {
        tableAdapter.reloadBoard(board)
    }

    private fun setupTable() = table.apply {
        val cellRenderer = ZeldaTableCellRenderer()
        setDefaultRenderer(ZeldaCellModel::class.java, cellRenderer)
        tableHeader.isVisible = false
        model = tableAdapter
    }

    private fun setupFrame() = frame.apply {
        frame.add(panel)
        val layoutManager = FlowLayout()
        panel.layout = layoutManager
        panel.add(table)
        panel.add(pathCostText)
        panel.add(totalCostText)
        panel.add(button)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        extendedState = JFrame.MAXIMIZED_BOTH
        setLocationRelativeTo(null)
        pack()
    }

    private fun setupButton() = button.addActionListener {
        listener.invoke()
    }

    fun setButtonText(buttonName: String) {
        button.text = buttonName
    }

    fun setTotalCost(cost: String) {
        totalCostText.text = "Total cost: $cost  "
    }

    fun setPathCost(goal: String, cost: String) {
        pathCostText.text = "Cost to go to $goal: $cost  "
    }

    fun updateBoardWithVisitedNodes(path: Path) {
        path.nodes.forEach { node ->
            tableAdapter.updateToVisited(node.position)
        }
    }
}