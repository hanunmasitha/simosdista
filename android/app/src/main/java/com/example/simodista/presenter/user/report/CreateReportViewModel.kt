package com.example.simodista.presenter.user.report

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateReportViewModel : ViewModel() {
    var bitmapImage  = MutableLiveData<Bitmap>()

    fun setImageBitmap(bitmap: Bitmap){
        bitmapImage.value = bitmap
    }

    fun getImageBitmap() : LiveData<Bitmap> = bitmapImage
}