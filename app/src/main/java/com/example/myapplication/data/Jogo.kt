package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Jogo(
    val id: Int,
    @SerializedName("mandante")
    val timeCasa: String,
    @SerializedName("url_logo_mandante")
    val logoCasaUrl: String,
    @SerializedName("siglaMandante")
    val siglaCasa: String,
    @SerializedName("visitante")
    val timeVisitante: String,
    @SerializedName("url_logo_visitante")
    val logoVisitanteUrl: String,
    val siglaVisitante: String,
    val golsMandante: Int? = null,
    val golsVisitante: Int? = null,
    val tempo: String,
    val campeonato: String,
    val fase: String,
    val escalacao: Escalacao,
    val lances: List<Lance>
)
