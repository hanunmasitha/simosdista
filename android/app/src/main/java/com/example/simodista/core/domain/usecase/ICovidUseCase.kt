package com.example.simodista.core.domain.usecase

import com.example.simodista.core.domain.model.CovidIndonesia
import kotlinx.coroutines.flow.Flow

interface ICovidUseCase {
    fun getCovidIndonesia() : Flow<CovidIndonesia>
}