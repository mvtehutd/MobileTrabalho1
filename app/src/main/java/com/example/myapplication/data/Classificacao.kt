package com.example.myapplication.data

data class Classificacao(
    val id: Int,
    val campeonato: String,
    val classificacao: List<Tabela>,
    val url_logo: String,
    val legenda: List<LinhaLegenda>
)
