package com.example.doctormobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctormobile.databinding.AppointmentDsiplayLayoutBinding
import com.example.doctormobile.models.CachedCompletedAppointment

class CachedCompletedAppointmentsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<CachedCompletedAppointment, CachedCompletedAppointmentsAdapter.CompletedAppointmentViewHolder>(
        COMPARATOR
    ) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<CachedCompletedAppointment>() {
            override fun areItemsTheSame(
                oldItem: CachedCompletedAppointment,
                newItem: CachedCompletedAppointment
            ): Boolean = oldItem.caseId == newItem.caseId

            override fun areContentsTheSame(
                oldItem: CachedCompletedAppointment,
                newItem: CachedCompletedAppointment
            ): Boolean = oldItem == newItem
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompletedAppointmentViewHolder =
        CompletedAppointmentViewHolder(
            AppointmentDsiplayLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CompletedAppointmentViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: CachedCompletedAppointment)
    }

    inner class CompletedAppointmentViewHolder(private val binding: AppointmentDsiplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(item: CachedCompletedAppointment) {
            binding.apply {
                nameTextView.text = item.name
                caseIdTextView.text = item.caseId
            }
        }
    }
}