package com.br.impacta.doemais.data.finalizado

import androidx.paging.PagingData
import com.br.impacta.doemais.data.doacao.Doacao
import kotlinx.coroutines.flow.Flow

interface FinalizadoRepository {
    fun getOrderFromRealtimeDatabase(): Flow<PagingData<Doacao>>
}