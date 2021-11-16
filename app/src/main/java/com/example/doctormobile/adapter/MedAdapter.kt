package com.example.doctormobile.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.doctormobile.R
import com.example.doctormobile.databinding.MedDisplayLayoutBinding
import com.example.doctormobile.viewModel.OrderMedicinesViewModel

class MedAdapter(
    val context: Context,
    var medList: ArrayList<String>,
    private val viewModel: OrderMedicinesViewModel
) : RecyclerView.Adapter<MedAdapter.MedsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedsViewHolder =
        MedsViewHolder(
            MedDisplayLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MedsViewHolder, position: Int) {
        holder.bind(medList[position])
    }

    override fun getItemCount(): Int = medList.size

    inner class MedsViewHolder(private val binding: MedDisplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = medList[position]
                    if (!viewModel.orderedMeds.contains(item)){
                        binding.card.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.teal_200
                            )
                        )
                    }
                    else {
                        binding.card.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                    }
                    viewModel.onMedClick(item)
                }
            }
        }

        fun bind(med: String) {
            binding.apply {
                tvName.text = med
            }
        }
    }
}