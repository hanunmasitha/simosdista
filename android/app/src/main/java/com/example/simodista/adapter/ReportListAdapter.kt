package com.example.simodista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simodista.databinding.NotificationItemRowBinding
import com.example.simodista.model.ReportForm

class ReportListAdapter : RecyclerView.Adapter<ReportListAdapter.ListViewHolder>() {
    private val reports: ArrayList<ReportForm> = ArrayList()

    fun setReport(list: ArrayList<ReportForm>){
        reports.clear()
        reports.addAll(list)
        notifyDataSetChanged()
    }

    class ListViewHolder(private val binding: NotificationItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(report: ReportForm){
            binding.tvFeedbackTitle.text = "Report - ${report.id}"
            binding.tvFeedbackDescription.text = report.status.toString()
            binding.tvFeedbackDate.text = report.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = NotificationItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(reports[position])
    }

    override fun getItemCount(): Int = reports.size
}