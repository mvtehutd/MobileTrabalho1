package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.JogosApplication


/**
 * Provides Factory to create instance of ViewModel for the entire Game app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for GameViewModel
        initializer {
            JogoViewModel(jogosApplication().container.palpiteRepository)
        }

    }

}

fun CreationExtras.jogosApplication(): JogosApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as JogosApplication)