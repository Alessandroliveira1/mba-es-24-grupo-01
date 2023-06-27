package com.br.impacta.doemais.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.impacta.doemais.commom.BaseSingleton.isbackButton
import com.br.impacta.doemais.commom.BaseSingleton.setBackButton

class HomeViewModel: ViewModel() {
    private val mutableBackButton = MutableLiveData<Boolean>()
    val liveDataBackButton: LiveData<Boolean> get() = mutableBackButton


    fun isBackButtonFragment(boolean: Boolean) {

    }
}