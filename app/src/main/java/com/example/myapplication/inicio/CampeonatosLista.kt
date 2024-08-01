package com.example.myapplication.inicio
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.CampeonatoInicio

@Composable
fun CampeonatosLista(campeonatos: List<CampeonatoInicio>, navController: NavHostController, innerPadding: PaddingValues){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(vertical = 5.dp)
    ) {
        items(campeonatos) { campeonato ->
            CampeonatoItem(campeonato, navController)
        }
    }
}
