package com.example.myapplication.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Campeonato
import com.example.myapplication.service.JogosRepository
import kotlinx.coroutines.launch

class CampeonatosViewModel(): ViewModel() {
    val campeonatos = MutableLiveData<List<Campeonato>>()
    private val jogoRepository = JogosRepository()

    fun verificarCampeonatos() {
        viewModelScope.launch {
            try {
                val response = jogoRepository.getCampeonatos()
                campeonatos.postValue(response)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}