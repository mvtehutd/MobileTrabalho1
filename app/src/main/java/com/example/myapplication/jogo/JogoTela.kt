package com.example.myapplication.jogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.myapplication.BottomNavigationBar
import com.example.myapplication.R
import com.example.myapplication.viewModels.AppViewModelProvider
import com.example.myapplication.viewModels.JogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JogoTela(navController: NavHostController, jogoId: Int) {
    val viewModel: JogoViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val jogoDados by viewModel.jogoData.observeAsState()
    val palpite by viewModel.palpite.observeAsState()

    DisposableEffect(Unit) {
        viewModel.iniciarVerificacaoAoVivo(jogoId)
        onDispose {
            viewModel.pararVerificacaoAoVivo()
        }
    }

    Scaffold(
        topBar = {
            JogosTopAppBar(navController, scrollBehavior, viewModel)
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        jogoDados?.let { response ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item {
                    if(response.lances.size > 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            response.lances.forEach { lance ->
                                LanceItem(lance)
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Palpite",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(2.dp),
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        palpite?.let {
                            Text(text = "Você votou em:",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    ,)
                            Row(modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(13.dp)
                                .height(40.dp)) {
                                if (it.time != "Empate") {
                                    var logo = if(it.time == response.timeCasa) response.logoCasaUrl else response.logoVisitanteUrl
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(logo)
                                                .decoderFactory(SvgDecoder.Factory())
                                                .build()
                                        ),
                                        contentDescription = it.time,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                                Text(text = it.time, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterVertically))
                            }
                            Button(
                                onClick = { viewModel.removerPalpite(jogoId) },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(2.dp),
                            ){
                                Text(text = stringResource(id = R.string.excluir_palpite))
                            }
                        } ?: run {
                            PalpiteButtons(
                                viewModel= viewModel,
                                idJogo= jogoId,
                                homeTeam = response.timeCasa,
                                awayTeam = response.timeVisitante,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }?: run {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun PalpiteButtons(
    viewModel: JogoViewModel,
    idJogo: Int,
    homeTeam: String,
    awayTeam: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(110.dp) // Tamanho do botão, ajuste conforme necessário
                .clip(CircleShape) // Torna o botão redondo
                .background(Color.Gray) // Cor de fundo do botão
                .clickable { viewModel.inserirPalpite(idJogo, homeTeam) },
            contentAlignment = Alignment.Center // Alinha o texto no centro
        ) {
            Text(
                text = homeTeam,
                color = Color.Black, // Cor do texto
                fontSize = 12.sp, // Tamanho da fonte, ajuste conforme necessário
                textAlign = TextAlign.Center, // Centraliza o texto
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Faz o texto ocupar a largura máxima
                    .padding(horizontal = 8.dp) // Ajusta o padding horizontal
            )
        }

        Box(
            modifier = Modifier
                .size(110.dp) // Tamanho do botão, ajuste conforme necessário
                .clip(CircleShape) // Torna o botão redondo
                .background(Color.Gray) // Cor de fundo do botão
                .clickable { viewModel.inserirPalpite(idJogo, "Empate") },
            contentAlignment = Alignment.Center // Alinha o texto no centro
        ) {
            Text(
                text = stringResource(id = R.string.empate),
                color = Color.Black, // Cor do texto
                fontSize = 12.sp, // Tamanho da fonte, ajuste conforme necessário
                textAlign = TextAlign.Center, // Centraliza o texto
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Faz o texto ocupar a largura máxima
                    .padding(horizontal = 8.dp) // Ajusta o padding horizontal
            )
        }

        Box(
            modifier = Modifier
                .size(110.dp) // Tamanho do botão, ajuste conforme necessário
                .clip(CircleShape) // Torna o botão redondo
                .background(Color.Gray) // Cor de fundo do botão
                .clickable { viewModel.inserirPalpite(idJogo, awayTeam) },
            contentAlignment = Alignment.Center // Alinha o texto no centro
        ) {
            Text(
                text = awayTeam,
                color = Color.Black, // Cor do texto
                fontSize = 12.sp, // Tamanho da fonte, ajuste conforme necessário
                textAlign = TextAlign.Center, // Centraliza o texto
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Faz o texto ocupar a largura máxima
                    .padding(horizontal = 8.dp), // Ajusta o padding horizontal
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}