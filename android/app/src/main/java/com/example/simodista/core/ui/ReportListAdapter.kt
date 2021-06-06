package com.example.simodista.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simodista.databinding.ReportItemRowBinding
import com.example.simodista.core.domain.model.ReportForm

class ReportListAdapter : RecyclerView.Adapter<ReportListAdapter.ListViewHolder>() {
    private val reports: ArrayList<ReportForm> = ArrayList()
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ReportForm)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setReport(list: ArrayList<ReportForm>){
        reports.clear()
        reports.addAll(list)
        notifyDataSetChanged()
    }

    class ListViewHolder(private val binding: ReportItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(report: ReportForm){
            binding.tvFeedbackTitle.text = "Report - ${report.id}"
            binding.tvFeedbackDescription.text = report.description
            binding.tvFeedbackDate.text = report.date
            if (report.status == true){
                binding.imageView6.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ReportItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(reports[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(reports[position]) }
    }

    override fun getItemCount(): Int = reports.size
}