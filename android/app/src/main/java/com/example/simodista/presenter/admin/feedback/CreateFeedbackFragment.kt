package com.example.simodista.presenter.admin.feedback

import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.simodista.R
import com.example.simodista.databinding.FragmentCreateFeedbackBinding
import com.example.simodista.core.domain.model.FeedbackForm
import com.example.simodista.core.domain.model.ReportForm
import com.example.simodista.core.domain.model.User
import com.example.simodista.presenter.admin.home.AdminHomeFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.text.SimpleDateFormat
import java.util.*

class CreateFeedbackFragment : Fragment() {
    lateinit var binding : FragmentCreateFeedbackBinding
    lateinit var viewModel: CreateFeedbackViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var reportForm: ReportForm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Feedback"
        return inflater.inflate(R.layout.fragment_create_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateFeedbackBinding.bind(view)
        binding.backgroundDim.visibility = View.VISIBLE
        binding.progressBar6.visibility = View.VISIBLE

        viewModel = ViewModelProvider(requireActivity()).get(CreateFeedbackViewModel::class.java)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        val reportId = arguments?.getInt(AdminHomeFragment.EXTRA_ID)
        if (reportId != null) {
            viewModel.setReport(reportId)
        }

        viewModel.getReport().observe(viewLifecycleOwner, {
            reportForm = it
            setData(it)
        })

        binding.button2.setOnClickListener {
            val docRef = firestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                submitFeedback(user)
            }
        }
    }

    private fun submitFeedback(user: User?) {
        viewModel.updateReport(reportForm.id as Int)
        firestore.collection("feedbacks").get().addOnSuccessListener{ snap->
            val feedbackForm = FeedbackForm(
                id = snap.size() + 1,
                user = user,
                date = SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(Date()),
                description = binding.editTextTextMultiLine.text.toString().trim(),
                report = reportForm
            )

            val reportId = (System.currentTimeMillis()/1000).toString() +"-"+ user?.email
            val document = firestore.collection("feedbacks").document(reportId)
            document.set(feedbackForm).addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to submit feedback", Toast.LENGTH_SHORT).show()
            }

            binding.progressBar6.visibility = View.VISIBLE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            Snackbar.make(view as View, "Feedback Sent", Snackbar.LENGTH_SHORT).show()
            view?.findNavController()?.navigate(R.id.action_createFeedbackFragment_to_adminHomeFragment)
        }
    }

    private fun setData(it: ReportForm) {
        Glide.with(binding.root)
            .load(it.image_uri)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.backgroundDim.visibility = View.GONE
                    binding.progressBar6.visibility = View.GONE
                    return false
                }

            })
            .into(binding.imageView5)

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(it.lat as Double, it.long as Double, 1)
        binding.tvLocation.text = addresses[0].getAddressLine(0)
        binding.tvDescription.text = it.description
    }

}