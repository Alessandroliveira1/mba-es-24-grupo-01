package com.br.impacta.doemais.data.andamento

import androidx.paging.PagingData
import com.br.impacta.doemais.data.doacao.Doacao
import kotlinx.coroutines.flow.Flow

interface AndamentoRepository {
    fun getOrderFromRealtimeDatabase(): Flow<PagingData<Doacao>>
}