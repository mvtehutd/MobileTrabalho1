package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Campeonato(
    val id: Int,
    @SerializedName("campeonato")
    val nome: String
)