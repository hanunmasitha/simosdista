package com.example.simodista.core.domain.usecase

import com.example.simodista.core.domain.model.CovidIndonesia
import com.example.simodista.core.domain.repository.ICovidRepository
import kotlinx.coroutines.flow.Flow

class CovidInteractor(private val repository: ICovidRepository): ICovidUseCase {
    override fun getCovidIndonesia(): Flow<CovidIndonesia> = repository.getCovidIndonesia()
}