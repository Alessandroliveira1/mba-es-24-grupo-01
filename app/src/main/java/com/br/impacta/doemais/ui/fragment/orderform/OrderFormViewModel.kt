package com.br.impacta.doemais.ui.fragment.orderform

import androidx.lifecycle.ViewModel
import com.br.impacta.doemais.data.doacao.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderFormViewModel @Inject constructor(
    repository: DatabaseRepository,
) : ViewModel() {

    val db = repository.getDatabase()
}