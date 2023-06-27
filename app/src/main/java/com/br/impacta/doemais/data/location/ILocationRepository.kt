package com.br.impacta.doemais.data.location

interface ILocationRepository {
    fun getLocationQueue(): LocationPagingSource
}