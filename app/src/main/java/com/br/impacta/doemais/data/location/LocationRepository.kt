package com.br.impacta.doemais.data.location

import javax.inject.Inject

class LocationRepository @Inject constructor(): ILocationRepository {
    override fun getLocationQueue(): LocationPagingSource {
        return LocationPagingSource()
    }

}