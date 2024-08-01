package com.example.myapplication.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CampeonatoInicio
import com.example.myapplication.service.JogosRepository
import kotlinx.coroutines.launch

class InicioViewModel(): ViewModel() {
    private val _jogosData = MutableLiveData<List<CampeonatoInicio>>()
    val jogosData: LiveData<List<CampeonatoInicio>> get() = _jogosData
    private val jogoRepository = JogosRepository()

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    init {
        runnable = Runnable {
            verificarAoVivo()
            handler.postDelayed(runnable, 10000) // 10 segundos
        }
    }

    private fun verificarAoVivo() {
        viewModelScope.launch {
            try {
                val response = jogoRepository.getJogos()
                _jogosData.postValue(response)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun recarregar(){
        verificarAoVivo()
    }

    fun iniciarVerificacaoAoVivo() {
        handler.post(runnable)
    }

    fun pararVerificacaoAoVivo() {
        handler.removeCallbacks(runnable)
    }
}