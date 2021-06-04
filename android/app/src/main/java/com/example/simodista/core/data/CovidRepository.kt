package com.example.simodista.core.data

import com.example.simodista.core.data.source.remote.RemoteDataSource
import com.example.simodista.core.domain.model.CovidIndonesia
import com.example.simodista.core.domain.repository.ICovidRepository
import com.example.simodista.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CovidRepository(private val remoteDataSource: RemoteDataSource) : ICovidRepository {
    override fun getCovidIndonesia(): Flow<CovidIndonesia> {
        return remoteDataSource.getCovidIndonesia().map {
            DataMapper.mapCovidResponseToDomain(it)
        }
    }
}