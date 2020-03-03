package model

import java.util.*

enum class TabuleiroEvento { VITORIA, DERROTA }

class Tabuleiro(val qtdeLinhas: Int, val qtdeColunas: Int, private val qtdeMinas: Int) {

    private val campos = arrayListOf(arrayListOf<Campo>())
    private val callbacks = arrayListOf<(TabuleiroEvento) -> Unit>()

    init {
        gerarCampos()
        associarVizinhos()
        sortearMinas()
    }


    private fun gerarCampos() {
        for (linha in 0 until qtdeLinhas) { //quantidade de linhas não incluso no for
            campos.add(arrayListOf())
            for (coluna in 0 until qtdeColunas) {
                val novoCampo = Campo(linha, coluna)
                campos[linha].add(novoCampo)
            }
        }
    }

    private fun associarVizinhos() {
        forEachCampo { associarVizinhos(it) }
    }

    private fun associarVizinhos(campo: Campo) {
        val (linha, coluna) = campo
        val linhas = arrayListOf(linha -1, linha, linha + 1)
        val colunas = arrayListOf(coluna -1, coluna, coluna + 1)

        linhas.forEach { l ->
            colunas.forEach {c ->
                val atual = campos.getOrNull(l)?.getOrNull(c)
                atual?.takeIf { campo != it }?.let { campo.addVizinho(it) }
            }
        }
    }

    private fun sortearMinas() {
        val gerador = Random()

        var linhaSorteada = -1
        var colunaSorteada = -1
        var qtdeMinasAtual = 0

        while (qtdeMinasAtual < this.qtdeMinas) {
            linhaSorteada = gerador.nextInt(qtdeLinhas)
            colunaSorteada = gerador.nextInt(qtdeColunas)

            val campoSorteado = campos[linhaSorteada][colunaSorteada]
            if(campoSorteado.seguro) {
                campoSorteado.minar()
                qtdeMinasAtual++
            }
        }
    }

    fun forEachCampo(callback: (Campo) -> Unit) {
        campos.forEach { linha -> linha.forEach(callback) }
    }


}