package com.example.simodista.core.utils

import com.example.simodista.core.data.source.remote.response.CovidIndonesiaResponse
import com.example.simodista.core.domain.model.CovidIndonesia

object DataMapper {
    fun mapCovidResponseToDomain(input : CovidIndonesiaResponse) : CovidIndonesia{
        return CovidIndonesia(
            positif = input.positif,
            sembuh = input.sembuh,
            meninggal = input.meninggal,
            dirawat = input.dirawat
        )
    }
}