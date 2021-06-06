package com.example.simodista.presenter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.simodista.MainActivity
import com.example.simodista.R

class SplashFragment: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashFragment, MainActivity::class.java))
            finish()
        },3000)
    }
}