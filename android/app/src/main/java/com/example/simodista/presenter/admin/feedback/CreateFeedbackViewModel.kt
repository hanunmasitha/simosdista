package com.example.simodista.presenter.admin.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simodista.model.ReportForm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class CreateFeedbackViewModel : ViewModel() {
    private val report = MutableLiveData<ReportForm>()

    fun setReport(id : Int){
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("reports")
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener {
                    for (data in it){
                        val temp = data.toObject<ReportForm>()
                        report.value = temp
                    }
                }
    }

    fun getReport() : LiveData<ReportForm> = report
    fun updateReport(id: Int) {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("reports")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                for (data in it){
                    firebaseFirestore.collection("reports")
                        .document(data.id)
                        .update("status", true)
                }
            }


    }


}