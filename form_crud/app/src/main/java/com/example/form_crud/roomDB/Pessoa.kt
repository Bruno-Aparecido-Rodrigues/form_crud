package com.example.form_crud.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pessoa( //criação da classe pessoa
    val nome: String,
    val telefone: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
