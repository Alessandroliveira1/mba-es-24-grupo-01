package com.br.impacta.doemais.data.doacao

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val source: OrderPagingSource,
    private val config: PagingConfig): OrderRepository {

    override fun getOrderFromRealtimeDatabase() = Pager(
        config = config
    ) {
        source
    }.flow


}