package com.example.simodista.presenter.admin.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simodista.model.FeedbackForm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class DetailFeedbackViewModel: ViewModel() {
    private val feedback = MutableLiveData<FeedbackForm>()

    fun setFeedback(id : Int){
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("feedbacks")
            .whereEqualTo("report.id", id)
            .get()
            .addOnSuccessListener {
                for (data in it){
                    val temp = data.toObject<FeedbackForm>()
                    feedback.value = temp
                }
            }
    }

    fun getFeedback() : LiveData<FeedbackForm> = feedback

}