package com.example.simodista.presenter.user.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simodista.core.domain.model.FeedbackForm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FeedbackDetailViewModel: ViewModel() {
    private val feedback = MutableLiveData<FeedbackForm>()

    fun setFeedback(id : Int){
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("feedbacks")
            .whereEqualTo("id", id)
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