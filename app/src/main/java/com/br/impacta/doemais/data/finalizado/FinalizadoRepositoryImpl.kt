package com.br.impacta.doemais.data.finalizado

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class FinalizadoRepositoryImpl @Inject constructor(
    private val source: FinalizadoPagingSource,
    private val config: PagingConfig): FinalizadoRepository {

    override fun getOrderFromRealtimeDatabase() = Pager(
        config = config
    ) {
        source
    }.flow


}