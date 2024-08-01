package com.example.myapplication.jogo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Lance

@Composable
fun LanceItem(lance: Lance) {
    val alignment = if (lance.isHomeTeam) Alignment.Start else Alignment.End
    val (icone, descricao) = when (lance.tipo) {
        "Gol" -> Pair(
            R.drawable.ic_gol,
            stringResource(id = R.string.gol)
        )
        "Gol Pênalti" -> Pair(
            R.drawable.ic_gol_penalti,
            stringResource(id = R.string.gol_penalti)
        )
        "Gol Contra" -> Pair(
            R.drawable.ic_gol_contra,
            stringResource(id = R.string.gol_contra)
        )
        "Cartão Amarelo" -> Pair(
            R.drawable.ic_cartao_amarelo,
            stringResource(id = R.string.cartao_amarelo)
        )
        "Segundo Amarelo" -> Pair(
            R.drawable.ic_segundo_amarelo,
            stringResource(id = R.string.segundo_cartao_amarelo)
        )
        "Cartão Vermelho" -> Pair(
            R.drawable.ic_cartao_vermelho,
            stringResource(id = R.string.cartao_vermelho)
        )
        "Substituição" -> Pair(
            R.drawable.ic_substituicao,
            stringResource(id = R.string.substituicao)
        )
        "Pênalti Perdido" -> Pair(
            R.drawable.ic_penalti_perdido,
            stringResource(id = R.string.penalti_perdido)
        )
        else -> Pair(
            R.drawable.ic_var,
            stringResource(id = R.string.`var`)
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 10.dp)
    ) {
        if (lance.isHomeTeam) {
            Text(
                text = "${lance.minuto}'",
                textAlign = if (lance.isHomeTeam) TextAlign.Start else TextAlign.End,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(painter = painterResource(id = icone), contentDescription = descricao, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            horizontalAlignment = alignment,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = lance.nomeJogador1,
                textAlign = if (lance.isHomeTeam) TextAlign.Start else TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 1.sp
            )
            if (lance.nomeJogador2 != null)
                Text(
                    text = lance.nomeJogador2,
                    textAlign = if (lance.isHomeTeam) TextAlign.Start else TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 1.sp
                )
        }
        if (!lance.isHomeTeam) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = icone),
                contentDescription = descricao,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${lance.minuto}'",
                textAlign = if (lance.isHomeTeam) TextAlign.Start else TextAlign.End,
                fontWeight = FontWeight.Bold
            )
        }
    }
}