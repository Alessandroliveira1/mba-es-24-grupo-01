package com.br.impacta.doemais.data.pedido

data class Pedido(
    val collectPoint: String? = null,
    val description: String? = null,
    val name: String? = null,
    val type: String? = null,
    val key: String? = null,
    val id: String? = null,
    val id2: String? = null,
    val timeline: String? = null,
    val perfil: String? = null,
    var doacao: List<Pedido>? = null,
    var exception: Exception? = null,
    val latitude: String? = null,
    val longitude: String? = null
)
