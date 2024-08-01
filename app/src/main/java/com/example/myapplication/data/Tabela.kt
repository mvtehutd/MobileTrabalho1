package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Tabela(
    @SerializedName("tabela")
    val nome: String,
    val linhas: List<LinhaTabela>
)
