package com.example.t120b192.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.t120b192.database.DatabaseViewModel

import com.example.t120b192.databinding.FragmentMeasurementListBinding
import com.example.t120b192.entities.CombinedData

class MyMeasurementRecyclerViewAdapter(
    private val values: List<CombinedData>, private val viewModel: DatabaseViewModel
) : RecyclerView.Adapter<MyMeasurementRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMeasurementListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.matavimas.toString()
        holder.xView.text = item.x.toString()
        holder.yView.text = item.y.toString()
        holder.valueView.text = item.atstumas.toString()
        holder.s1View.text = item.stiprumasList[0].toString()
        holder.s2View.text = item.stiprumasList[1].toString()
        holder.s3View.text = item.stiprumasList[2].toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMeasurementListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val xView: TextView = binding.x
        val yView: TextView = binding.y
        val valueView: TextView = binding.value
        val s1View: TextView = binding.stiprumas1
        val s2View: TextView = binding.stiprumas2
        val s3View: TextView = binding.stiprumas3

    }

}