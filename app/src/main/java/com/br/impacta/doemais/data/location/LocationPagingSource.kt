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

package com.br.impacta.doemais.data.location

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.br.impacta.doemais.commom.ImageEnum
import com.br.impacta.doemais.mock.LoremIpsun
import com.br.impacta.doemais.mock.LoremIpsun.Companion.SENTENCE_LOREM_IPSUN
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlin.math.max



/**
 * A [PagingSource] that loads articles for paging. The [Int] is the paging key or query that is used to fetch more
 * data, and the [Article] specifies that the [PagingSource] fetches an [Article] [List].
 */
class LocationPagingSource : PagingSource<Int, Location>() {

    // The initial key used for loading.
// This is the article id of the first article that will be loaded
    companion object{
        private const val STARTING_KEY = 0
        private const val LOAD_DELAY_MILLIS = 3_000L
    }
    private var currentNumberRandom = 0
    private val names = arrayOf("Alessandro", "Daiane", "Juliana", "Clara", "Gian", "Luan", "Vitor", "Marcelo", "Wellington", "Fabiano", "Lucas", "Pedro")
    private val numbers = arrayOf(0,1,2,3)
    private var numbersList = ArrayList<Int>()
    private var currentNumber = 0
    private var listWork = ArrayList<String>()
    private var lastName = ""
    private var currentName = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val startKey = params.key ?: STARTING_KEY

        // We fetch as many articles as hinted to by params.loadSize
        val range = startKey.until(startKey + params.loadSize)

        var database: DatabaseReference = Firebase.database.getReference("users")

        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tal = snapshot
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tal = snapshot
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //val range = startKey.until(startKey + Firebase.database.)

        // Simulate a delay for loads adter the initial load
        if (startKey != STARTING_KEY) delay(LOAD_DELAY_MILLIS)
        return LoadResult.Page(
            data = range.map { number ->
                Location(
                    id = number,
                    image = getRandomNumber(),
                    name = getRandomName(),
                    title = SENTENCE_LOREM_IPSUN,
                    description = LoremIpsun().getTopicsLoremIpsun(),
                    location = "Location $number",
                    donateType = getDonateType(),
                    latitude = getRandomLatitude(),
                    longitude = getRandomLongitude()
                )
            },
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)



    private fun getRandomLatitude(): Double {
        return -23.65300 + (10..99).shuffled().first()
    }

    private fun getRandomLongitude(): Double {
        return -46.71292 + (10..99).shuffled().first()
    }

    private fun getRandomNumber(): Int {
        if (numbersList.isEmpty()){
            numbersList.addAll(numbers)
        }

        currentNumber = 0
        numbersList.remove(currentNumber)

        return currentNumber
    }

    private fun getRandomName(): String {
        if (listWork.isEmpty()){
            listWork.addAll(names)
        }

        currentName = listWork[(0 until listWork.size).random()]
        listWork.remove(currentName)

        return currentName
    }

    private fun getDonateType(): String {
        return when(currentNumber){
            ImageEnum.FOOD.value -> "Alimento"
            ImageEnum.CLOTHER.value -> "Roupa"
            ImageEnum.VOLUNTARY.value -> "Voluntário"
            ImageEnum.SUPPORT.value -> "Serviço"
            else -> "None"
        }
    }
}
