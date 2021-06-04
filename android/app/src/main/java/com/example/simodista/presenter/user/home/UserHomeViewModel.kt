package com.example.simodista.presenter.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.simodista.core.domain.model.CovidIndonesia
import com.example.simodista.core.domain.usecase.ICovidUseCase

class UserHomeViewModel(private val useCase: ICovidUseCase) : ViewModel() {
    fun getCovidIndonesia(): LiveData<CovidIndonesia> = useCase.getCovidIndonesia().asLiveData()
}