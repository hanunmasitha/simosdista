package com.example.simodista.presenter.user.home

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.simodista.core.domain.model.CovidIndonesia
import com.example.simodista.core.domain.model.FeedbackForm
import com.example.simodista.core.domain.model.ReportForm
import com.example.simodista.core.domain.usecase.ICovidUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*
import kotlin.collections.ArrayList

class UserHomeViewModel(private val useCase: ICovidUseCase) : ViewModel() {
    val location = MutableLiveData<ArrayList<String>>()

    fun getCovidIndonesia(): LiveData<CovidIndonesia> = useCase.getCovidIndonesia().asLiveData()

    fun setCrowdLocation(context: Context){
        val firebaseFirestor = FirebaseFirestore.getInstance()
        firebaseFirestor.collection("reports")
                .orderBy("id")
                .limit(4)
                .get()
                .addOnSuccessListener {
                    val strings = ArrayList<String>()
                    for (data in it) {
                        val temp = data.toObject<ReportForm>()
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses: List<Address> = geocoder.getFromLocation(temp.lat as Double, temp.long as Double, 1)
                        strings.add(addresses[0].getAddressLine(0))
                    }
                    location.value = strings
                }
    }
}