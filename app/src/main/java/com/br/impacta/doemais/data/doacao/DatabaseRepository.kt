package com.br.impacta.doemais.data.doacao

import com.google.firebase.database.DatabaseReference

interface DatabaseRepository {
    fun getDatabase(): DatabaseReference
}