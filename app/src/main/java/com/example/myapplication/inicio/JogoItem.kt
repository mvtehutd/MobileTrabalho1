package com.example.myapplication.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.myapplication.data.JogoInicio


@OptIn(ExperimentalCoilApi::class)
@Composable
fun JogoItem(jogo: JogoInicio, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("jogos/${jogo.id}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (jogo.tempo.matches(Regex("^[0-9+]+$"))) "${jogo.tempo}'" else jogo.tempo,
            modifier = Modifier.width(35.dp),
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            color = when {
                (jogo.golsMandante != null) and (jogo.tempo != "FIM") -> Color.Red
                else -> Color.DarkGray
            },
            fontWeight = if (jogo.tempo != null) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(0.dp))
        Text(
            text = jogo.timeCasa,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(jogo.logoCasaUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            ),
            contentDescription = jogo.timeCasa,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = when {
                jogo.golsMandante != null && jogo.golsVisitante != null -> "${jogo.golsMandante} x ${jogo.golsVisitante}"
                else -> "x"
            },
            color = when {
                (jogo.golsMandante != null) and (jogo.tempo != "FIM") -> Color.Red
                else -> Color.DarkGray
            },
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(jogo.logoVisitanteUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            ),
            contentDescription = jogo.timeVisitante,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = jogo.timeVisitante,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}