package view

import util.*
import model.Tabuleiro
import model.TabuleiroEvento
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class CampoMinado {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            TelaPrincipal()
        }
    }
}

class TelaPrincipal : JFrame() {

    var tabuleiro = Tabuleiro(
        qtdeLinhas = QUANTIDADE_LINHAS_PADRAO,
        qtdeColunas = QUANTIDADE_COLUNAS_PADRAO,
        qtdeMinas = QUANTIDADE_MINAS_PADRAO
    )
    var painelTabuleiro = PainelTabuleiro(tabuleiro)

    init {

        val optionValue =
            JOptionPane.showConfirmDialog(this, TEXTO_PERGUNTA_CUSTOMIZAR, title, JOptionPane.YES_NO_OPTION)


        optionValue.takeUnless { it > JOptionPane.YES_OPTION }?.let {

            do {
                var check = true

                try {

                    val lineSize = JOptionPane.showInputDialog(this, TEXTO_PERGUNTA_QUANTIDADE_LINHAS)
                        .takeIf { !it.isEmpty() && it.toInt() < QUANTIDADE_LINHAS_PADRAO && it.toInt() > NUMERO_ZERO }
                        .let { it?.toInt() } ?: QUANTIDADE_LINHAS_PADRAO

                    val columnSize = JOptionPane.showInputDialog(this, TEXTO_PERGUNTA_QUANTIDADE_COLUNAS)
                        .takeIf { !it.isEmpty() && it.toInt() < QUANTIDADE_COLUNAS_PADRAO && it.toInt() > NUMERO_ZERO }
                        .let { it?.toInt() } ?: QUANTIDADE_COLUNAS_PADRAO

                    val mineQuantity = JOptionPane.showInputDialog(this, TEXTO_PERGUNTA_QUANTIDADE_MINAS)
                        .takeIf { !it.isEmpty() && it.toInt() < QUANTIDADE_MINAS_PADRAO && it.toInt() > NUMERO_ZERO }
                        .let { it?.toInt() } ?: QUANTIDADE_MINAS_PADRAO

                    tabuleiro = Tabuleiro(qtdeLinhas = lineSize, qtdeColunas = columnSize, qtdeMinas = mineQuantity)

                } catch (ex: NumberFormatException) {

                    JOptionPane.showMessageDialog(this, TEXTO_EXCEPTION_NUMERO_INVALIDO)
                    check = false
                }

            } while (!check)

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