package com.example.simodista.presenter.admin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simodista.model.ReportForm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class AdminHomeViewModel : ViewModel(){
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val reports = MutableLiveData<ArrayList<ReportForm>>()

    fun setReport(){
        firebaseFirestore.collection("reports").get().addOnSuccessListener {
            val list = ArrayList<ReportForm>()
            for (document in it) {
                val temp = document.toObject<ReportForm>()
                list.add(temp)
            }
            reports.postValue(list)
        }
    }

    fun getReport() : LiveData<ArrayList<ReportForm>> = reports
}