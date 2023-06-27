/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.br.impacta.doemais.data.finalizado

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.br.impacta.doemais.data.doacao.Doacao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await


/**
 * A [PagingSource] that loads articles for paging. The [Int] is the paging key or query that is used to fetch more
 * data, and the [Article] specifies that the [PagingSource] fetches an [Article] [List].
 */
class FinalizadoPagingSource (private val db: DatabaseReference) : PagingSource<DataSnapshot, Doacao>() {
    // The initial key used for loading.
    // This is the article id of the first article that will be loaded
    companion object{
        private const val STARTING_KEY = 0
        private const val LOAD_DELAY_MILLIS = 3_000L
    }

    override fun getRefreshKey(state: PagingState<DataSnapshot, Doacao>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>) = try {
        val queryProductNames = db.child("Finalizado").orderByKey().limitToFirst(20)
        val currentPage = params.key ?: queryProductNames.get().await()

        queryProductNames.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.toString() != currentPage.toString()) {
                    val products = listOf(snapshot.children.last().toPedido())

                    LoadResult.Page(
                        data = products,
                        prevKey = null,
                        nextKey = snapshot
                    )

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val lastVisibleProductKey = currentPage.children.last().key
        val nextPage = queryProductNames.startAfter(lastVisibleProductKey).get().await()


        val products = currentPage.children.map { snapshot ->
            snapshot.toPedido()
        }

        val tal = arrayListOf<Doacao>()
        val id = Firebase.auth.uid ?: ""

        (products as ArrayList).forEach{
            if (it.id != id ) {
                if (it.id2 != id) {
                    tal.add(it)
                }
            }
        }

        tal.iterator().forEach {
            products.remove(it)
        }


        if (currentPage.value != STARTING_KEY) delay(LOAD_DELAY_MILLIS)


        LoadResult.Page(
            data = products,
            prevKey = null,
            nextKey = nextPage
        )



    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    fun DataSnapshot.toPedido() = Doacao(
        key = key,
        collectPoint = (value as HashMap<String, String>).get("collectPoint"),
        description = (value as HashMap<String, String>).get("description"),
        type =  (value as HashMap<String, String>).get("type"),
        id = (value as HashMap<String, String>).get("id"),
        id2 = (value as HashMap<String, String>).get("id2"),
        name = (value as HashMap<String, String>).get("name"),
        timeline = (value as HashMap<String, String>).get("timeline")
    )

}
