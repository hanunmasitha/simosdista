package com.example.simodista.core.data.source.remote

import android.util.Log
import com.example.simodista.core.data.source.remote.response.CovidIndonesiaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val api: API) {

    fun getCovidIndonesia() : Flow<CovidIndonesiaResponse> {
        return flow {
            try {
                val response = ArrayList(api.getCovidIndonesia())
                emit(response[0])
            }catch (e : Exception){
                Log.e("RemoteDataSource - getCovidIndonesia", e.message.toString() )
            }
        }.flowOn(Dispatchers.IO)
    }
}