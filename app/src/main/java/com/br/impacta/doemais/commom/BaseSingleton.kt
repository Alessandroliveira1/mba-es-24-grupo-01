package com.br.impacta.doemais.commom

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object BaseSingleton {
    private lateinit var uModel: UserModel

    private var isbackButton: Boolean = false

    private var idAuth = Firebase.auth.uid ?: ""

    fun setUser(userModel: UserModel) {
        uModel = UserModel(userModel.id, userModel.userName, userModel.email)
    }

    fun getUser(): UserModel {
        return uModel
    }

    fun setBackButton(boolean: Boolean) {
        isbackButton = boolean
    }

    fun isbackButton() = isbackButton

    fun getIdAuth() = idAuth
}