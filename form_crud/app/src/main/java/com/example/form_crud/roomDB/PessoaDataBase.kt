package com.example.form_crud.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(// faz a declaração das tabelas e a versãodo banco
    entities = [Pessoa::class],
    version = 1
)

abstract class PessoaDataBase: RoomDatabase() {
    abstract fun pessoaDao(): PessoaDao
}