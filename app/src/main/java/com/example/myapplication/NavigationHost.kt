package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.campeonato.CampeonatosTela
import com.example.myapplication.campeonato.ClassificacaoTela
import com.example.myapplication.inicio.InicioTela
import com.example.myapplication.jogo.JogoTela
import com.example.myapplication.viewModels.CampeonatosViewModel
import com.example.myapplication.viewModels.ClassificacaoViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "jogos", modifier = modifier) {
        composable("jogos") { InicioTela(navController) }
        composable("jogos/{jogoId}") { backStackEntry ->
            val jogoId = backStackEntry.arguments?.getString("jogoId")
            jogoId?.let {

                JogoTela(navController, jogoId.toInt())
            }
        }
        composable("campeonatos") {
            val viewModel = CampeonatosViewModel()
            CampeonatosTela(viewModel, navController)
        }
        composable("campeonatos/{campeonatoId}") { backStackEntry ->
            val campeonatoId = backStackEntry.arguments?.getString("campeonatoId")
            campeonatoId?.let {
                val viewModel = ClassificacaoViewModel(campeonatoId.toInt())
                ClassificacaoTela(viewModel, navController)
            }
        }
    }
}