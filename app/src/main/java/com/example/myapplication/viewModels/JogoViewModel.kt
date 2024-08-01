package com.example.myapplication.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Jogo
import com.example.myapplication.repository.Palpite
import com.example.myapplication.repository.PalpiteRepository
import com.example.myapplication.service.JogosRepository
import kotlinx.coroutines.launch

class JogoViewModel(private val palpiteRepository: PalpiteRepository) : ViewModel() {
    private val _jogoData = MutableLiveData<Jogo>()
    val jogoData: LiveData<Jogo> get() = _jogoData
    private val jogoRepository = JogosRepository()
    private var idJogo: Int = 0
    var palpite = MutableLiveData<Palpite>()

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
                if (idJogo != 0) {
                    val response = jogoRepository.getJogo(idJogo)
                    _jogoData.postValue(response)
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun recarregar(){
        verificarAoVivo()
    }

    fun iniciarVerificacaoAoVivo(id: Int) {
        idJogo = id
        viewModelScope.launch {
            val response = palpiteRepository.obterPalpite(idJogo)
            if (response != null)
                palpite.postValue(response)
        }
        handler.post(runnable)
    }

    fun pararVerificacaoAoVivo() {
        handler.removeCallbacks(runnable)
    }

    fun inserirPalpite(id: Int, time: String){
        var palpiteNovo = Palpite(id, time)
        viewModelScope.launch {
            palpiteRepository.inserirPalpite(palpiteNovo)
        }
        palpite.postValue(palpiteNovo)
    }
    fun removerPalpite(id: Int){
        viewModelScope.launch {
            palpiteRepository.deletarPalpite(id)
            palpite.postValue(palpiteRepository.obterPalpite(id))
        }
    }
}