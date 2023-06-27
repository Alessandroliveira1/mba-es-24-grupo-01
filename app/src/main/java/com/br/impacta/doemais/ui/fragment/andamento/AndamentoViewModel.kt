package com.br.impacta.doemais.ui.fragment.andamento

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.impacta.doemais.data.andamento.AndamentoRepository
import com.br.impacta.doemais.data.doacao.Doacao
import com.br.impacta.doemais.data.doacao.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndamentoViewModel @Inject constructor(
    repository: AndamentoRepository,
) : ViewModel() {

    private val mutableLocation = MutableLiveData<HashMap<Doacao, Boolean>>()
    val liveDataLocation: LiveData<HashMap<Doacao, Boolean>> get() = mutableLocation

    val products = repository.getOrderFromRealtimeDatabase()

    fun setLocation(location: Doacao, firstClick: Boolean) {
        val hashMap = HashMap<Doacao, Boolean>()
        hashMap[location] = firstClick
        mutableLocation.value = hashMap
    }
}