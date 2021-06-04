package com.example.simodista.presenter.user.notification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simodista.R
import com.example.simodista.core.ui.FeedbackListAdapter
import com.example.simodista.databinding.FragmentNotificationBinding
import com.example.simodista.core.domain.model.FeedbackForm
import com.google.firebase.auth.FirebaseAuth


class NotificationFragment : Fragment() {
    lateinit var binding : FragmentNotificationBinding
    lateinit var viewModel: NotificationViewModel
    lateinit var adapter: FeedbackListAdapter
    lateinit var firebaseAuth: FirebaseAuth

    companion object{
        private const val EXTRA_ID = "extra_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Notification"
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(NotificationViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()

        viewModel.setReport(firebaseAuth.currentUser?.email as String)
        showRecycleView()
        adapter.setOnItemClickCallback(object : FeedbackListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FeedbackForm) {
                val bundle = Bundle()
                bundle.putInt(EXTRA_ID, data.id as Int)
                view.findNavController().navigate(R.id.action_notificationFragment_to_feedbackDetailFragment, bundle)
            }
        })
    }

    private fun showRecycleView() {
        binding.rvListNotification.layoutManager = LinearLayoutManager(activity)
        binding.rvListNotification.setHasFixedSize(true)

        adapter = FeedbackListAdapter()
        viewModel.getReport().observe(viewLifecycleOwner, {
            adapter.setReport(it)
        })
        adapter.notifyDataSetChanged()
        binding.rvListNotification.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Destroy", "DESTROYUEURASJDMASD")
    }

}