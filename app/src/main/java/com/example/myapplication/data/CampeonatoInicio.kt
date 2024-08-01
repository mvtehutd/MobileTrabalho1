package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class CampeonatoInicio(
    @SerializedName("campeonato")
    val nome: String,
    val jogos: List<JogoInicio>
)