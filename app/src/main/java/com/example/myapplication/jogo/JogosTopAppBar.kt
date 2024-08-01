package com.example.myapplication.jogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.viewModels.JogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JogosTopAppBar(navController: NavHostController, scrollBehavior: TopAppBarScrollBehavior, viewModel: JogoViewModel) {

    val jogo by viewModel.jogoData.observeAsState()
    val isCollapsed = scrollBehavior.state.collapsedFraction < 0.3

    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            jogo?.let { response ->
                val homeLogo = response.logoCasaUrl
                val awayLogo = response.logoVisitanteUrl
                Row( // linha Principal (Tudo)
                    modifier = Modifier
                        .fillMaxWidth()
//                        .background(Color.Red)
                        .height(80.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) { // Coluna para centralizar
                        if(isCollapsed) // Campeonato - Fase
                            Row(modifier = Modifier.height(20.dp)){
                                Text(
                                    text = response.campeonato + " - " + response.fase,
                                    fontSize = 11.sp,
                                    color = Color.Black,
                                    lineHeight = 1.sp
                                )
                            }
                        Row (verticalAlignment = Alignment.CenterVertically){ // placar
                            Column( // Nome Time Mandante
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier
                                    .width(if(isCollapsed) 100.dp else 60.dp)
                            ) {
                                Text(
                                    text = if(isCollapsed) response.timeCasa else response.siglaCasa,
                                    fontSize = 15.sp,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Image( // Logo Mandante
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(homeLogo)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build()
                                ),
                                contentDescription = response.timeCasa,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column( // Status
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.height(40.dp)
                            ) {
                                Text( // tempo
                                    if (response.tempo.matches(Regex("^[0-9+]+$"))) "${response.tempo}'" else response.tempo,
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 1.sp
                                )
                                if (!response.tempo.contains(":")) // gols
                                    Text(
                                        when {
                                            response.golsMandante != null && response.golsVisitante != null -> "${response.golsMandante} x ${response.golsVisitante}"
                                            else -> "x"
                                        },
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 1.sp
                                    )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Image( // Logo Visitante
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(awayLogo)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build()
                                ),
                                contentDescription = response.timeVisitante,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column( // Nome Time Visitante
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .width(if(isCollapsed) 100.dp else 60.dp)
                            ) {
                                Text(
                                    text = if(isCollapsed) response.timeVisitante else response.siglaVisitante,
                                    fontSize = 15.sp,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                    if (scrollBehavior.state.collapsedFraction < 0.5)
                        Spacer(modifier = Modifier.width(17.dp))
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.voltar)
                )
            }
        },
        actions = {
            IconButton(onClick = { viewModel.recarregar() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.recarregar),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}