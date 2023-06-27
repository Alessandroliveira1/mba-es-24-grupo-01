package com.br.impacta.doemais.room

import com.br.impacta.doemais.data.doacao.Doacao
import com.br.impacta.doemais.data.finalizado.Finalizado
import com.br.impacta.doemais.data.pedido.Pedido
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class DAOFinalizado {
    private var databaseReference: DatabaseReference? = null

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(Finalizado::class.java.simpleName)
    }

    fun add(emp: Finalizado?): Task<Void?> {
        return databaseReference!!.push().setValue(emp)
    }

    fun update(key: String?, hashMap: HashMap<String?, Any?>?): Task<Void?> {
        return databaseReference!!.child(key!!).updateChildren(hashMap!!)
    }

    fun remove(key: String?): Task<Void?> {
        return databaseReference!!.child(key!!).removeValue()
    }

    operator fun get(key: String?): Query {
        return if (key == null) {
            databaseReference!!.orderByKey().limitToFirst(8)
        } else databaseReference!!.orderByKey().startAfter(key).limitToFirst(8)
    }

    fun get(): Query? {
        return databaseReference
    }
}