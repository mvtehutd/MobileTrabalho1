package com.example.myapplication.campeonato

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.myapplication.data.LinhaLegenda
import com.example.myapplication.ui.theme.NavyBlue
import com.example.myapplication.viewModels.ClassificacaoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun ClassificacaoTela(viewModel: ClassificacaoViewModel = viewModel(), navController: NavHostController) {
    val scrollState = rememberScrollState()
    val isScrolled by derivedStateOf { scrollState.value > 10 }
    val classificacaoData by viewModel.classificacao.observeAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    viewModel.verificarClassificacao()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.app_name).uppercase(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.voltar),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.verificarClassificacao() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.recarregar),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        classificacaoData?.let { response ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .fillMaxWidth()

                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(response.url_logo)
                                .decoderFactory(SvgDecoder.Factory())
                                .build()
                        ),
                        contentDescription = response.campeonato,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterVertically),
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = response.campeonato,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column {
                    response.classificacao.forEach { tabela ->
                        if (response.classificacao.size > 1)
                            Row {
                                Column {
                                    Text(
                                        tabela.nome,
                                        style = MaterialTheme.typography.labelLarge,
                                        fontSize = 17.sp,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 3.dp
                                        )
                                    )
                                }
                            }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp)
                            ) {
                                Column(modifier = Modifier.width(25.dp)) {
                                    HeaderCell(text = " ")
                                    tabela.linhas.forEach { time ->
                                        val (corNumero, corFundo) = CoresPosicoes(
                                            time.idLegenda,
                                            response.legenda
                                        )
                                        PosicaoCell(
                                            text = time.posicao.toString(),
                                            corNumero,
                                            corFundo
                                        )
                                    }
                                }
                                Column(
                                    modifier = if (isScrolled) Modifier.width(70.dp) else Modifier.width(
                                        150.dp
                                    )
                                )
                                {
                                    HeaderCell(text = stringResource(id = R.string.time))
                                    tabela.linhas.forEach { time ->
                                        TimeCell(
                                            logoUrl = time.logo,
                                            text = if (isScrolled) time.sigla else time.time
                                        )
                                    }
                                }
                                Column(modifier = Modifier.width(37.dp)) {
                                    HeaderCell(text = stringResource(id = R.string.pontos))
                                    tabela.linhas.forEach { time ->
                                        Cell(text = time.pontos.toString())
                                    }
                                }

                                Row(
                                    modifier = Modifier.horizontalScroll(scrollState)
                                ) {
                                    Column(modifier = Modifier.width(35.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.jogos))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.jogos.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(35.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.vitorias))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.vitorias.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(35.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.empates))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.empates.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(35.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.derrotas))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.derrotas.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(37.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.gols_pro))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.golsPro.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(37.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.gols_contra))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.golsContra.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(37.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.saldo_gols))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = time.saldoGols.toString())
                                        }
                                    }
                                    Column(modifier = Modifier.width(50.dp)) {
                                        HeaderCell(text = stringResource(id = R.string.aproveitamento))
                                        tabela.linhas.forEach { time ->
                                            Cell(text = "${time.aproveitamento}%")
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                Legenda(response.legenda)

            }
        } ?: run {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

    }
}

fun CoresPosicoes(indice: Int?, legenda: List<LinhaLegenda>): Pair<Color, Color> {
    var texto = Color.Black
    var fundo = Color.LightGray
    if (indice != null)
        legenda.forEach{linha ->
            if(linha.id == indice){
                texto = Color(linha.corTexto.removePrefix("0x").toLong(16))
                fundo = Color(linha.corFundo.removePrefix("0x").toLong(16))
            }
        }
    return Pair(texto, fundo)
}

@Composable
fun HeaderCell(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
    )
}

@Composable
fun Cell(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
}

@Composable
fun PosicaoCell(text: String, corTexto: Color, corFundo: Color) {
    Spacer(modifier = Modifier.height(3.2.dp))
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(20.dp)
            .background(color = corFundo, shape = androidx.compose.foundation.shape.CircleShape)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = corTexto,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
    Spacer(modifier = Modifier.height(3.5.dp))
}

@Composable
fun LegendaCor(cor: Color) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(color = cor, shape = androidx.compose.foundation.shape.CircleShape)
    )
}

@Composable
fun TimeCell(logoUrl: String, text: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(logoUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            ),
            contentDescription = text,
            modifier = Modifier.size(19.dp),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun Legenda(legenda: List<LinhaLegenda>){
    Spacer(modifier = Modifier.height(10.dp))
    Column {
        legenda.forEach { linha ->
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendaCor(Color(linha.corFundo.removePrefix("0x").toLong(16)))
                Text(
                    text = linha.nome,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(horizontal = 5.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}