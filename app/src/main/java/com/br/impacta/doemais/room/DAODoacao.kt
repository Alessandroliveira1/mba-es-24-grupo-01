package com.br.impacta.doemais.room

import com.br.impacta.doemais.data.doacao.Doacao
import com.br.impacta.doemais.data.pedido.Pedido
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class DAODoacao {
    private var databaseReference: DatabaseReference? = null

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(Doacao::class.java.simpleName)
    }

    fun add(emp: Doacao?): Task<Void?> {
        return databaseReference!!.push().setValue(emp)
    }

    fun update(key: String?, hashMap: HashMap<String?, String?>): Task<Void?> {
        return databaseReference!!.child(key!!).updateChildren(hashMap!! as Map<String, Any>)
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