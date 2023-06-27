package com.br.impacta.doemais.data.doacao

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val db: DatabaseReference): DatabaseRepository {
    override fun getDatabase(): DatabaseReference {
        return db
    }


}