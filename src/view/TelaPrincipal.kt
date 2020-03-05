package view

import util.*
import model.Tabuleiro
import model.TabuleiroEvento
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    TelaPrincipal()
}

class TelaPrincipal : JFrame() {

    var tabuleiro = Tabuleiro(qtdeLinhas = LINE_DEFAULT, qtdeColunas = COLUM_DEFAULT, qtdeMinas = MINE_DEFAULT)
    var painelTabuleiro = PainelTabuleiro(tabuleiro)

    init {

        val optionValue = JOptionPane.showConfirmDialog(this, OPTION_QUESTION, title, JOptionPane.YES_NO_OPTION)

        optionValue.takeUnless { it > JOptionPane.YES_OPTION }?.let {

            val lineSize: String = JOptionPane.showInputDialog(this, LINE_QUESTION)
            val columnSize: String = JOptionPane.showInputDialog(this, COLUMN_QUESTION)
            val mineQuantity: String = JOptionPane.showInputDialog(this, MINE_QUESTION)


            tabuleiro = Tabuleiro(
                qtdeLinhas = lineSize.takeIf { !it.isEmpty() }.let { it?.toInt() } ?: LINE_DEFAULT,
                qtdeColunas = columnSize.takeIf { !it.isEmpty() }.let { it?.toInt() } ?: COLUM_DEFAULT,
                qtdeMinas = mineQuantity.takeIf { !it.isEmpty() }.let { it?.toInt() } ?: MINE_DEFAULT
            )

            painelTabuleiro = PainelTabuleiro(tabuleiro)

        }

        tabuleiro.onEvento(this::mostrarResutado)
        add(painelTabuleiro)

        setSize(690, 438)
        setLocationRelativeTo(null) //o padrão será o centro da tela
        defaultCloseOperation = EXIT_ON_CLOSE
        title = TITLE
        isVisible = true

    }

    private fun mostrarResutado(evento: TabuleiroEvento) {

        SwingUtilities.invokeLater {
            val message = when (evento) {
                TabuleiroEvento.VITORIA -> VICTORY
                TabuleiroEvento.DERROTA -> DEFEAT
            }

            JOptionPane.showMessageDialog(this, message)
            tabuleiro.reiniciar()

            painelTabuleiro.repaint()
            painelTabuleiro.validate()
        }

    }

}