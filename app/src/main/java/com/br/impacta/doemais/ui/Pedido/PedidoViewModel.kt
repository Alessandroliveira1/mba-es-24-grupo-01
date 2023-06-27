package com.br.impacta.doemais.ui.Pedido

import androidx.lifecycle.ViewModel
import com.br.impacta.doemais.data.doacao.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(
    repository: OrderRepository,
) : ViewModel() {

    val products = repository.getOrderFromRealtimeDatabase()
}