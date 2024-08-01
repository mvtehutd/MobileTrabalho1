package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.theme.AliceBlue
import com.example.myapplication.ui.theme.CelestialBlue
import com.example.myapplication.ui.theme.NavyBlue

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = CelestialBlue
    ) {
        if (currentRoute != null) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.SportsSoccer, contentDescription = stringResource(id = R.string.rota_jogos)) },
                label = { Text(stringResource(id = R.string.rota_jogos)) },
                selected = currentRoute.startsWith("jogos"),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CelestialBlue,
                    unselectedIconColor = AliceBlue,
                    selectedTextColor = NavyBlue,
                    unselectedTextColor = AliceBlue,
                    indicatorColor = NavyBlue
                ),
                onClick = {
                    navController.navigate("jogos") {
                        // Evita recriar o destino quando o usuário clica no item novamente
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                }
            )
            NavigationBarItem(
                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = stringResource(id = R.string.rota_campeonatos)) },
                label = { Text(stringResource(id = R.string.rota_campeonatos)) },
                selected = currentRoute.startsWith("campeonatos"),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CelestialBlue,
                    unselectedIconColor = AliceBlue,
                    selectedTextColor = NavyBlue,
                    unselectedTextColor = AliceBlue,
                    indicatorColor = NavyBlue
                ),
                onClick = {
                    navController.navigate("campeonatos") {
                        // Evita recriar o destino quando o usuário clica no item novamente
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                }
            )
        }
    }
}
