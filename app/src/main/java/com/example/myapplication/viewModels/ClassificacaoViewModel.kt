package com.example.myapplication.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Classificacao
import com.example.myapplication.service.JogosRepository
import kotlinx.coroutines.launch

class ClassificacaoViewModel (private val id: Int): ViewModel() {
    val classificacao = MutableLiveData<Classificacao>()
    private val jogoRepository = JogosRepository()

    fun verificarClassificacao() {
        viewModelScope.launch {
            try {
                val response = jogoRepository.getCampeonato(id)
                classificacao.postValue(response)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}