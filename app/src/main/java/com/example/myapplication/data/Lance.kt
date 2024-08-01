package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Lance(
    @SerializedName("indicador")
    val tipo: String,
    val minuto: String,
    @SerializedName("casa")
    val numeroJogador1: String,
    @SerializedName("nome")
    val nomeJogador1: String,
    @SerializedName("numeroSai")
    val numeroJogador2: String?,
    @SerializedName("nomeSai")
    val nomeJogador2: String?,
    @SerializedName("mandante")
    val isHomeTeam: Boolean
)
