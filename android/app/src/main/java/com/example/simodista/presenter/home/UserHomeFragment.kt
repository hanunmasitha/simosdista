package com.example.simodista.presenter.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simodista.R
import com.example.simodista.databinding.FragmentUserHomeBinding
import com.google.firebase.auth.FirebaseAuth

class UserHomeFragment : Fragment() {
    lateinit var binding: FragmentUserHomeBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserHomeBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()

        Log.d("Current User", firebaseAuth.currentUser?.email.toString())
    }

}