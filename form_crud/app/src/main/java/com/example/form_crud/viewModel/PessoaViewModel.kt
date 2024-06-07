package com.example.form_crud.viewModel

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.form_crud.roomDB.Pessoa
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class PessoaViewModel(private val repository: Repository): ViewModel() { //vai criar a função para executar os comando do pessoadao no mainAct
    fun getPessoa() = repository.getAllPessoa().asLiveData(viewModelScope.coroutineContext)

    fun upsertPessoa(pessoa: Pessoa) {
        viewModelScope.launch {
            repository.upsertPessoa(pessoa)
        }
    }

    fun deletePessoa(pessoa: Pessoa){
        viewModelScope.launch {
            repository.deletePessoa(pessoa)
        }
    }
}