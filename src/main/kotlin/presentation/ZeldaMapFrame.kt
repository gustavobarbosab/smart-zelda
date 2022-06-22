package presentation

import domain.Path
import java.awt.Color.DARK_GRAY
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable

class ZeldaMapFrame(screenTitle: String) {

    var listener: ScreenListener? = null
    private val frame: JFrame = JFrame()
    private val table = JTable()
    private val panel = JPanel()
    private val button = JButton()
    private var tableAdapter = ZeldaTableAdapter()

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
        panel.layout = GridBagLayout()

        val gridConstraint = GridBagConstraints()
        gridConstraint.fill = GridBagConstraints.HORIZONTAL
        gridConstraint.gridx = 0
        gridConstraint.gridy = 0
        gridConstraint.ipady = 40
        panel.add(table, gridConstraint)

        gridConstraint.fill = GridBagConstraints.CENTER
        gridConstraint.gridx = 0
        gridConstraint.gridy = 1
        gridConstraint.ipady = 0
        panel.add(button, gridConstraint)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.preferredSize = Dimension(INITIAL_SCREEN_DIMENSION, INITIAL_SCREEN_DIMENSION)
        setLocationRelativeTo(null)
        pack()
        isVisible = true
    }

    private fun setupButton() = button.addActionListener {
        listener?.onButtonClicked()
    }

    fun setButtonText(buttonName: String) {
        button.text = buttonName
    }

    fun updateBoardWithVisitedNodes(path: Path) {
        path.nodes.forEach { node ->
            tableAdapter.updateToVisited(node.position)
        }
    }

    interface ScreenListener {
        fun onButtonClicked()
    }

    companion object {
        const val INITIAL_SCREEN_DIMENSION = 600
    }
}