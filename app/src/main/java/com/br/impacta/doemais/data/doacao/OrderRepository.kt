package com.br.impacta.doemais.data.doacao

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrderFromRealtimeDatabase(): Flow<PagingData<Doacao>>
}