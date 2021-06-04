package com.example.simodista.presenter.admin.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simodista.R
import com.example.simodista.core.ui.ReportListAdapter
import com.example.simodista.databinding.FragmentAdminHomeBinding
import com.example.simodista.core.domain.model.ReportForm


class AdminHomeFragment : Fragment() {
    lateinit var binding : FragmentAdminHomeBinding
    lateinit var viewModel: AdminHomeViewModel
    lateinit var adapter : ReportListAdapter

    companion object{
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Home"
        return inflater.inflate(R.layout.fragment_admin_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminHomeBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(AdminHomeViewModel::class.java)

        viewModel.setReport()
        showRecycleView()
        adapter.setOnItemClickCallback(object : ReportListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ReportForm) {
                val bundle = Bundle()
                bundle.putInt(EXTRA_ID, data.id as Int)
                if(data.status == false){
                    view.findNavController().navigate(R.id.action_adminHomeFragment_to_createFeedbackFragment, bundle)
                }else{
                    view.findNavController().navigate(R.id.action_adminHomeFragment_to_detailFeedbackFragment, bundle)
                }
            }

        })
    }

    private fun showRecycleView() {
        binding.rvListReport.layoutManager = LinearLayoutManager(activity)
        binding.rvListReport.setHasFixedSize(true)

        adapter = ReportListAdapter()
        viewModel.getReport().observe(viewLifecycleOwner, {
            adapter.setReport(it)
        })
        adapter.notifyDataSetChanged()
        binding.rvListReport.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_setting -> view?.findNavController()?.navigate(R.id.action_adminHomeFragment_to_settingFragment)
        }

        return super.onOptionsItemSelected(item)
    }



}