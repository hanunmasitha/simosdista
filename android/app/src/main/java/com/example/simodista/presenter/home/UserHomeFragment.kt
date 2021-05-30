package com.example.simodista.presenter.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.simodista.R
import com.example.simodista.model.User
import com.example.simodista.databinding.FragmentUserHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class UserHomeFragment : Fragment() {
    lateinit var binding: FragmentUserHomeBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Home"
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserHomeBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val docRef = firestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
//            Log.d("USER DATA", "DocumentSnapshot data: ${documentSnapshot.data}")
            var user = documentSnapshot.toObject<User>()
            binding.textView3.text = user?.name
        }

        Log.d("Current User", firebaseAuth.currentUser?.email.toString())


        binding.floatingActionButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_userHomeFragment_to_createReportFragment2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_notification -> view?.findNavController()?.navigate(R.id.action_userHomeFragment_to_notificationFragment)
            R.id.menu_setting -> view?.findNavController()?.navigate(R.id.action_userHomeFragment_to_settingFragment)
        }

        return super.onOptionsItemSelected(item)
    }

}