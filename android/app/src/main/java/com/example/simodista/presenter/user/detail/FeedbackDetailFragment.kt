package com.example.simodista.presenter.user.detail

import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.simodista.R
import com.example.simodista.databinding.FragmentFeedbackDetailBinding
import com.example.simodista.presenter.admin.home.AdminHomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class FeedbackDetailFragment : Fragment() {
    lateinit var binding : FragmentFeedbackDetailBinding
    lateinit var viewModel: FeedbackDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedbackDetailBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(FeedbackDetailViewModel::class.java)
        binding.backgroundDim.visibility = View.VISIBLE
        binding.progressBar6.visibility = View.VISIBLE
        val reportId = arguments?.getInt(AdminHomeFragment.EXTRA_ID)
        if (reportId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.setFeedback(reportId)
                withContext(Dispatchers.Main){
                    setData()
                }
            }
        }
    }

    private fun setData() {
        viewModel.getFeedback().observe(viewLifecycleOwner,{
            binding.tvDescription.text = it.report?.description
            binding.textView8.text = it.date
            binding.textView10.text = it.description
            Glide.with(binding.root)
                .load(it.report?.image_uri)
                .listener(object : RequestListener<Drawable> {
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
            val addresses: List<Address> = geocoder.getFromLocation(it.report?.lat as Double, it.report.long as Double, 1)
            binding.tvLocation.text = addresses[0].getAddressLine(0)
        })
    }

}