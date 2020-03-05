package view

import util.*
import model.Tabuleiro
import model.TabuleiroEvento
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class CampoMinado {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            TelaPrincipal()
        }
    }
}

class TelaPrincipal : JFrame() {

    var tabuleiro = Tabuleiro(
        qtdeLinhas = QUANTIDADE_LINHA_PADRAO,
        qtdeColunas = QUANTIDADE_COLUNA_PADRAO,
        qtdeMinas = QUANTIDADE_MINAS_PADRAO
    )
    var painelTabuleiro = PainelTabuleiro(tabuleiro)

    init {

        val optionValue =
            JOptionPane.showConfirmDialog(this, TEXTO_PERGUNTA_CUSTOMIZAR, title, JOptionPane.YES_NO_OPTION)

        optionValue.takeUnless { it > JOptionPane.YES_OPTION }?.let {

            val lineSize: String = JOptionPane.showInputDialog(this, TEXTO_PERGUNTA_QUANTIDADE_LINHAS)
            val columnSize: String = JOptionPane.showInputDialog(this, TEXTO_PERGUNTA_QUANTIDADE_COLUNAS)
            val mineQuantity: String = JOptionPane.showInputDialog(this, TEXTO_PERGUNTA_QUANTIDADE_MINAS)


            tabuleiro = Tabuleiro(
                qtdeLinhas = lineSize.takeIf { !it.isEmpty() }.let { it?.toInt() } ?: QUANTIDADE_LINHA_PADRAO,
                qtdeColunas = columnSize.takeIf { !it.isEmpty() }.let { it?.toInt() } ?: QUANTIDADE_COLUNA_PADRAO,
                qtdeMinas = mineQuantity.takeIf { !it.isEmpty() }.let { it?.toInt() } ?: QUANTIDADE_MINAS_PADRAO
            )

            painelTabuleiro = PainelTabuleiro(tabuleiro)

        }

        tabuleiro.onEvento(this::mostrarResutado)
        add(painelTabuleiro)

        setSize(690, 438)
        setLocationRelativeTo(null) //o padrão será o centro da tela
        defaultCloseOperation = EXIT_ON_CLOSE
        title = TEXTO_TITULO
        isVisible = true

    }

    private fun mostrarResutado(evento: TabuleiroEvento) {

        SwingUtilities.invokeLater {
            val message = when (evento) {
                TabuleiroEvento.VITORIA -> TEXTO_VITORIA
                TabuleiroEvento.DERROTA -> TEXTO_DERROTA
            }

            JOptionPane.showMessageDialog(this, message)
            tabuleiro.reiniciar()

            painelTabuleiro.repaint()
            painelTabuleiro.validate()
        }

    }

}