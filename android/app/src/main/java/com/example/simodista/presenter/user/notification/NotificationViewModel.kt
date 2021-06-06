package com.example.simodista.presenter.user.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simodista.core.domain.model.FeedbackForm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class NotificationViewModel : ViewModel() {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val reports = MutableLiveData<ArrayList<FeedbackForm>>()

    fun setReport(email: String){
        firebaseFirestore.collection("feedbacks")
            .whereEqualTo("report.user.email", email)
            .get()
            .addOnSuccessListener {
                val list = ArrayList<FeedbackForm>()
                for (data in it){
                    val temp = data.toObject<FeedbackForm>()
                    list.add(temp)
                }
                reports.postValue(list)
            }
    }

    fun getReport() : LiveData<ArrayList<FeedbackForm>> = reports
}