@file:Suppress("IMPLICIT_CAST_TO_ANY")

package br.edu.utfpr.trabalhofinal.ui.utils.composables.conta

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbDownOffAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.trabalhofinal.R
import br.edu.utfpr.trabalhofinal.data.Conta
import br.edu.utfpr.trabalhofinal.data.TipoContaEnum
import br.edu.utfpr.trabalhofinal.ui.theme.TrabalhoFinalTheme
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun ContaIcon(
    modifier: Modifier = Modifier,
    onPressed: () -> Unit,
    conta: Conta
) {

    IconButton(
        modifier = modifier,
        onClick = onPressed
    )
    {
        Icon(
            imageVector = when {
                conta.paga && conta.tipo == TipoContaEnum.RECEITA -> Icons.Filled.ThumbUp
                conta.tipo == TipoContaEnum.RECEITA -> Icons.Filled.ThumbUpOffAlt
                conta.paga && conta.tipo == TipoContaEnum.DESPESA -> Icons.Filled.ThumbDown
                conta.tipo == TipoContaEnum.DESPESA -> Icons.Filled.ThumbDownOffAlt
                else -> Icons.Default
            } as ImageVector,
            contentDescription = stringResource(R.string.status_da_conta),
            tint = when (conta.tipo) {
                        TipoContaEnum.RECEITA -> Color(0xFF00984E)
                        TipoContaEnum.DESPESA -> Color(0xFFCF5355)
                     else -> LocalContentColor.current
            }
        )
    }

}

@Preview
@Composable
fun ConIconPreview(modifier: Modifier = Modifier) {
    TrabalhoFinalTheme {

        ContaIcon(
          onPressed = {},
          conta =  Conta(
            descricao = "Salário",
            valor = BigDecimal("5000.0"),
            tipo = TipoContaEnum.RECEITA,
            data = LocalDate.of(2024, 9, 5),
            paga = true)
        )
    }

}

@Preview
@Composable
fun ConIconPreview2(modifier: Modifier = Modifier) {
    TrabalhoFinalTheme {

        ContaIcon(
            onPressed = {},
            conta =  Conta(
                descricao = "Aluguel",
                valor = BigDecimal("1200.0"),
                tipo = TipoContaEnum.DESPESA,
                data = LocalDate.of(2024, 9, 5),
                paga = true)
        )
    }

}

@Preview
@Composable
fun ConIconPreview3(modifier: Modifier = Modifier) {
    TrabalhoFinalTheme {

        ContaIcon(
            onPressed = {},
            conta =  Conta(
                descricao = "Aluguel",
                valor = BigDecimal("1200.0"),
                tipo = TipoContaEnum.DESPESA,
                data = LocalDate.of(2024, 9, 5),
                paga = false)
        )
    }

}

@Preview
@Composable
fun ConIconPreview4(modifier: Modifier = Modifier) {
    TrabalhoFinalTheme {

        ContaIcon(
            onPressed = {},
            conta =  Conta(
                descricao = "Salario",
                valor = BigDecimal("5000.0"),
                tipo = TipoContaEnum.RECEITA,
                data = LocalDate.of(2024, 9, 5),
                paga = false)
        )
    }

}
