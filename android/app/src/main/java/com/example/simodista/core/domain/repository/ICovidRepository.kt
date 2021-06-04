package com.example.simodista.core.domain.repository

import com.example.simodista.core.domain.model.CovidIndonesia
import kotlinx.coroutines.flow.Flow

interface ICovidRepository {
    fun getCovidIndonesia() : Flow<CovidIndonesia>
}