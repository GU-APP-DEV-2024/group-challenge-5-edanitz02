package com.zybooks.workingwithdata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PatentCustomAdapter(
    private val dataSet: List<DisplayData.PatentData>,
    private val onClick: (DisplayData.PatentData) -> Unit
) : RecyclerView.Adapter<PatentCustomAdapter.PatentViewHolder>() {

    class PatentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val patentNumberTextView: TextView = view.findViewById(R.id.patentNumberTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patent, parent, false)
        return PatentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatentViewHolder, position: Int) {
        val patent = dataSet[position]
        holder.titleTextView.text = patent.title
        holder.patentNumberTextView.text = "Patent #: ${patent.patentNumber}"
        holder.view.setOnClickListener { onClick(patent) }
    }

    override fun getItemCount() = dataSet.size
}