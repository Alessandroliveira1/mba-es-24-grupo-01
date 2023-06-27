package com.br.impacta.doemais.ui.campanha


import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.impacta.doemais.data.doacao.Doacao
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class CampanhaViewModel @Inject constructor (private val db: DatabaseReference) : ViewModel() {

    private val mutableLocation = MutableLiveData<HashMap<Doacao, Boolean>>()
    val liveDataLocation: LiveData<HashMap<Doacao, Boolean>> get() = mutableLocation

    private val mutableButton = MutableLiveData<Boolean>()
    val liveDataButton: LiveData<Boolean> get() = mutableButton


    private val mutableClickConfirm = MutableLiveData<Unit>()
    val liveClickConfirm: LiveData<Unit> get() = mutableClickConfirm

    fun setLocation(location: Doacao, firstClick: Boolean) {
        val hashMap = HashMap<Doacao, Boolean>()
        hashMap[location] = firstClick
        mutableLocation.value = hashMap
    }

    fun setButtonView(isVisible: Boolean) {
        mutableButton.value = isVisible
    }

    fun onClickConfirm() {
        mutableClickConfirm.value = Unit
    }

    fun getDatabase(): DatabaseReference {
        return db
    }

}