package com.br.impacta.doemais.data.location

import java.io.Serializable

data class Location(
    val id: Int,
    val image: Int,
    val name: String,
    val title: String,
    val description: CharSequence,
    val location: String,
    val donateType: String,
    val latitude: Double,
    val longitude: Double,
): Serializable
