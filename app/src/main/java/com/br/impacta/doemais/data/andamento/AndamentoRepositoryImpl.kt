package com.br.impacta.doemais.data.andamento

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.br.impacta.doemais.data.doacao.OrderPagingSource
import javax.inject.Inject

class AndamentoRepositoryImpl @Inject constructor(
    private val source: AndamentoPagingSource,
    private val config: PagingConfig): AndamentoRepository {

    override fun getOrderFromRealtimeDatabase() = Pager(
        config = config
    ) {
        source
    }.flow


}