package com.example.simodista.presenter.user.report

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateReportViewModel : ViewModel() {
    private val bitmapImage  = MutableLiveData<Bitmap>()
    private var location = arrayOf<Double>()

    fun setImageBitmap(bitmap: Bitmap){
        bitmapImage.value = bitmap
    }

    fun setLocation(latitude: Double, longitude: Double){
        location = arrayOf(latitude, longitude)
    }

    fun getImageBitmap() : LiveData<Bitmap> = bitmapImage
    fun getLocation() : Array<Double> = location
}