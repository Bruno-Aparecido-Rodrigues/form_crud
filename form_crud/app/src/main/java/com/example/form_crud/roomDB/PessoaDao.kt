package com.example.form_crud.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PessoaDao {

    @Upsert// realiza o inserset e faz o update caso necessário
    suspend fun upsertPessoa(pessoa: Pessoa)

    @Delete //vai realizar o delete do campo preenchido
    suspend fun deletePessoa(pessoa: Pessoa)

    @Query("SELECT * FROM pessoa") //realiza um select geral da classe pessoa
    fun getAllPessoa(): Flow<List<Pessoa>>
}