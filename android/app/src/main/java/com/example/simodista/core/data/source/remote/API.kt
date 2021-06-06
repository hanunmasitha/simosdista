package com.example.simodista.core.data.source.remote

import com.example.simodista.core.data.source.remote.response.CovidIndonesiaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("indonesia")
    suspend fun getCovidIndonesia(): List<CovidIndonesiaResponse>

}