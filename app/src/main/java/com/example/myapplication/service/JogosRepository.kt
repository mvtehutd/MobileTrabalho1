package com.example.myapplication.service

import com.example.myapplication.data.Campeonato
import com.example.myapplication.data.CampeonatoInicio
import com.example.myapplication.data.Classificacao
import com.example.myapplication.data.Jogo

class JogosRepository {
    private val api = RetrofitInstance.api

    suspend fun getJogo(id: Int) : Jogo {
        return api.getJogo(id)
    }

    suspend fun getJogos() : List<CampeonatoInicio> {
        return api.getJogosHoje()
    }

    suspend fun getCampeonatos() : List<Campeonato> {
        return api.getCampeonatos()
    }

    suspend fun getCampeonato(id: Int) : Classificacao {
        return api.getCampeonato(id)
    }
}