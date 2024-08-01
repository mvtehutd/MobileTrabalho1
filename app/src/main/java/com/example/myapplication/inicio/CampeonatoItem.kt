package com.example.myapplication.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.CampeonatoInicio

@Composable
fun CampeonatoItem(campeonato: CampeonatoInicio, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = campeonato.nome,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 5.dp, start = 3.dp)
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        campeonato.jogos.forEach { jogo ->
            JogoItem(jogo, navController)
        }
    }
}