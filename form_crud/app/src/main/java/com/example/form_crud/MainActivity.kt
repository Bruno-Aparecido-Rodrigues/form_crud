package com.example.form_crud

import Repository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.form_crud.roomDB.Pessoa
import com.example.form_crud.roomDB.PessoaDataBase
import com.example.form_crud.ui.theme.Form_crudTheme
import com.example.form_crud.viewModel.PessoaViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy { //criação do banco dentro da aplicação
        Room.databaseBuilder(
            applicationContext,
            PessoaDataBase::class.java,
            "pessoa.db"
        ).build()
    }

    private val viewModel by viewModels<PessoaViewModel>( //criação da classe de modelagem
        factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T: ViewModel> create(modelClass: Class<T>): T{
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Form_crudTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(viewModel,this)
                }
            }
        }
    }
}

@Composable
fun App(viewModel: PessoaViewModel, mainActivity: MainActivity) {
    var nome by remember { //criando as váriaveis a ser colocadas no banco
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    val pessoa = Pessoa( //instanciando a classe pessoa do banco e definido suas variávies no mainAct
        nome,
        telefone
    )

    var pessoaList by remember { //vai armazenar os dados da classe pessoa em um array
        mutableStateOf(listOf<Pessoa>())
    }

    viewModel.getPessoa().observe(mainActivity) {
        pessoaList = it
    }

    Column( //background da page
        Modifier
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text( //texto com o título do app
                text = "App Cadastro Cliente",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Row( //espaçadores
            Modifier
                .padding(20.dp)
        ) {

        }
        Row( //centralização
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField( // caixa de texto do campo nome
                value = nome,
                onValueChange = { nome = it },
                label = { Text(text = "Nome:") }
            )
        }
        Row( //espaçadores
            Modifier
                .padding(20.dp)
        ) {

        }
        Row( //cewntralização
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField( //caixa de texto do campo telefone
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text(text = "Telefone:") }
            )
        }
        Row( //espaçamento e centralização do botão
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            Button( //criação do botão de cadastro
                onClick = {
                    viewModel.upsertPessoa(pessoa)
                }
            ) { //texto do botão
                Text(text = "Cadastrar")
            }
        }
        Divider()//divisória
        LazyColumn {
            items(pessoaList) { pessoa -> //vai criar uma lista com todos os clientes
                Row(
                    Modifier
                        .clickable { //ao clickar no campo exibido na tela o mesmo será deletado
                            viewModel.deletePessoa(pessoa)
                        }
                        .fillMaxWidth(), //vai ocupar todo os lados
                    Arrangement.Center //vai alinhar ao centro
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f),
                        Arrangement.Center
                    ) {//exibição do nome da pessoa
                        Text(text = "${pessoa.nome}")
                    }
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f),
                        Arrangement.Center
                    ) {//exibição do telefone da pessoa
                        Text(text = "${pessoa.telefone}")
                    }
                }
                Divider()//divisor
            }
        }
    }
}
/*@Preview
@Composable
fun AppPreview(){
    Form_crudTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            App()
        }
    }
}
*/
