package com.example.simodista.presenter.user.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.simodista.R
import com.example.simodista.core.domain.model.User
import com.example.simodista.databinding.FragmentUserHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import org.koin.android.viewmodel.ext.android.viewModel

class UserHomeFragment : Fragment() {
    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val homeViewModel : UserHomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Home"
        binding = FragmentUserHomeBinding.inflate(inflater,  container, false)
        binding.floatingActionButton.visibility = View.GONE
        binding.progressBar3.visibility = View.VISIBLE
        binding.backgroundDim.visibility = View.VISIBLE
        binding.progressBar5.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val docRef = firestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            var user = documentSnapshot.toObject<User>()
            binding.progressBar3.visibility = View.GONE
            binding.floatingActionButton.visibility = View.VISIBLE
            binding.textView2.text = "Welcome, "
            binding.textView3.text = user?.name
        }

        homeViewModel.getCovidIndonesia().observe(requireActivity(), {
            binding.tvDirawat.text = it.dirawat
            binding.tvMeninggal.text = it.meninggal
            binding.tvSembuh.text = it.sembuh
            binding.tvPositif.text = it.positif
            binding.backgroundDim.visibility = View.GONE
            binding.progressBar5.visibility = View.GONE
        })

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