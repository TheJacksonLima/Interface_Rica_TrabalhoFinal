package br.edu.utfpr.trabalhofinal.ui.conta.lista

import br.edu.utfpr.trabalhofinal.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.trabalhofinal.data.Conta
import br.edu.utfpr.trabalhofinal.data.TipoContaEnum
import br.edu.utfpr.trabalhofinal.ui.theme.TrabalhoFinalTheme
import br.edu.utfpr.trabalhofinal.ui.utils.composables.Carregando
import br.edu.utfpr.trabalhofinal.ui.utils.composables.conta.ContaIcon
import br.edu.utfpr.trabalhofinal.ui.utils.composables.ErroAoCarregar
import br.edu.utfpr.trabalhofinal.utils.calcularProjecao
import br.edu.utfpr.trabalhofinal.utils.calcularSaldo
import br.edu.utfpr.trabalhofinal.utils.formatar
import java.math.BigDecimal
import java.time.LocalDate


@Composable
fun ListaContasScreen(
    modifier: Modifier = Modifier,
    onAdicionarPressed: () -> Unit,
    onContaPressed: (Conta) -> Unit,
    viewModel: ListaContasViewModel = viewModel()
) {
    val contentModifier: Modifier = modifier.fillMaxSize()
    if (viewModel.state.carregando) {
        Carregando(modifier = contentModifier)
    } else if (viewModel.state.erroAoCarregar) {
        ErroAoCarregar(
            modifier = contentModifier,
            onTryAgainPressed = viewModel::carregarContas,
        )
    } else {
        Scaffold(
            modifier = contentModifier,
            topBar = { AppBar(onAtualizarPressed = viewModel::carregarContas) },
            bottomBar = { BottomBar(contas = viewModel.state.contas) },
            floatingActionButton = {
                FloatingActionButton(onClick = onAdicionarPressed) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.adicionar)
                    )
                }
            }
        ) { paddingValues ->
            val modifierWithPadding = Modifier.padding(paddingValues)
            if (viewModel.state.contas.isEmpty()) {
                ListaVazia(modifier = modifierWithPadding.fillMaxSize())
            } else {
                List(
                    modifier = modifierWithPadding,
                    contas = viewModel.state.contas,
                    onContaPressed = onContaPressed
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    onAtualizarPressed: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.contas)) },
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = onAtualizarPressed) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.atualizar)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    TrabalhoFinalTheme {
        AppBar(
            onAtualizarPressed = {}
        )
    }
}

@Composable
private fun ListaVazia(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = stringResource(R.string.lista_vazia_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = stringResource(br.edu.utfpr.trabalhofinal.R.string.lista_vazia_subtitle),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListaVaziaPreview() {
    TrabalhoFinalTheme {
        ListaVazia()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun List(
    modifier: Modifier = Modifier,
    contas: List<Conta>,
    onContaPressed: (Conta) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(contas) { conta ->

            val data = "${conta.data.formatar()}"
            val descricao = "${conta.descricao}"
            val minusValor = "- $ ${conta.valor}"
            val valor = "$ ${conta.valor}"

            ListItem(
                modifier = Modifier.clickable { onContaPressed(conta) },
                trailingContent = {},
               headlineContent = {
                   Row(
                       modifier =  Modifier.fillMaxWidth(),
                       verticalAlignment = Alignment.CenterVertically
                   )
                   {
                      ContaIcon(onPressed = {  }, conta = conta )

                       var textPadding = PaddingValues(
                           top = 8.dp,
                           start = 8.dp,
                           end = 8.dp
                       )

                       Spacer(Modifier.size(24.dp))

                       Column (modifier = Modifier.weight(1f)){
                           Text(
                               modifier = Modifier.padding(textPadding),
                               text = descricao,
                               style = typography.bodyMedium,
                               color = if (conta.paga and (conta.tipo == TipoContaEnum.RECEITA))
                                                Color(0xFF00984E)
                                       else if (conta.paga and (conta.tipo == TipoContaEnum.DESPESA))
                                             Color(0xFFCF5355)
                                       else
                                   LocalContentColor.current
                           )
                           Text(
                               modifier = Modifier.padding(textPadding),
                               text = data,
                               style = typography.labelSmall,
                               color = if (conta.paga and (conta.tipo == TipoContaEnum.RECEITA)) Color(0xFF00984E)
                                      else if (conta.paga and (conta.tipo == TipoContaEnum.DESPESA)) Color(0xFFCF5355)
                               else
                                   LocalContentColor.current
                           )
                       }

                       Spacer(Modifier.size(24.dp))

                       textPadding = PaddingValues(
                           top = 12.dp,
                           start = 12.dp,
                           end = 0.dp
                       )

                       Text(
                           modifier = Modifier
                               .padding(PaddingValues(
                                   top = 12.dp,
                                   start = 12.dp,
                                   end = 0.dp
                               )),
                           text =  if (conta.tipo == TipoContaEnum.RECEITA) valor
                                   else minusValor ,
                           style = typography.titleSmall,
                           color = if (conta.paga and (conta.tipo == TipoContaEnum.RECEITA))
                               Color(0xFF00984E)
                           else if (conta.paga and (conta.tipo == TipoContaEnum.DESPESA))
                               Color.Red
                           else
                               LocalContentColor.current
                       )
                   }
               }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListPreview() {
    TrabalhoFinalTheme {
        List(
            contas = gerarContas(),
            onContaPressed = {}
        )
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    contas: List<Conta>
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Totalizador(
            modifier = Modifier.padding(top = 20.dp),
            titulo = stringResource(R.string.saldo),
            valor = contas.calcularSaldo(),
        )
        Totalizador(
            modifier = Modifier.padding(bottom = 20.dp),
            titulo = stringResource(R.string.previsao),
            valor = contas.calcularProjecao(),
        )
    }
}

@Composable
fun Totalizador(
    modifier: Modifier = Modifier,
    titulo: String,
    valor: BigDecimal,
) {

    var textColor = Color(0xFF00984E)
    var valorAux: String

    if (valor.compareTo(BigDecimal.ZERO) >= 0) {
         valorAux = "${valor.formatar()}"
         textColor = Color(0xFF00984E)

    }
    else{
         valorAux = "${valor.formatar()}"
         textColor = Color(0xFFCF5355)
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            text = titulo,
            color = textColor
        )
        Spacer(Modifier.size(10.dp))
        Text(
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.End,
            text = valorAux,
            color = textColor
        )
        Spacer(Modifier.size(20.dp))
    }
}


@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    TrabalhoFinalTheme {
        BottomBar(
            contas = gerarContas()
        )
    }
}

private fun gerarContas(): List<Conta> = listOf(
    Conta(
        descricao = "Salário",
        valor = BigDecimal("5000.0"),
        tipo = TipoContaEnum.RECEITA,
        data = LocalDate.of(2024, 9, 5),
        paga = true
    ),
    Conta(
        descricao = "Aluguel",
        valor = BigDecimal("1500.0"),
        tipo = TipoContaEnum.DESPESA,
        data = LocalDate.of(2024, 9, 10),
        paga = true
    ),
    Conta(
        descricao = "Condomínio",
        valor = BigDecimal("200.0"),
        tipo = TipoContaEnum.DESPESA,
        data = LocalDate.of(2024, 9, 15),
        paga = false
    ),
    Conta(
        descricao = "Jogo do Tigrinho",
        valor = BigDecimal("115.90"),
        tipo = TipoContaEnum.RECEITA,
        data = LocalDate.of(2024, 9, 15),
        paga = false
    )

)