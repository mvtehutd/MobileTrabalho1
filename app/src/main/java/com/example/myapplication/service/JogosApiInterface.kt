package com.example.myapplication.service

import com.example.myapplication.data.Campeonato
import com.example.myapplication.data.CampeonatoInicio
import com.example.myapplication.data.Classificacao
import com.example.myapplication.data.Jogo
import retrofit2.http.GET
import retrofit2.http.Path

interface JogosApiInterface {
        @GET("jogos")
        suspend fun getJogosHoje(): List<CampeonatoInicio>

        @GET("jogos/{id}")
        suspend fun getJogo(@Path("id") id: Int): Jogo

        @GET("campeonatos")
        suspend fun getCampeonatos(): List<Campeonato>

        @GET("campeonatos/{id}")
        suspend fun getCampeonato(@Path("id") id: Int): Classificacao

}