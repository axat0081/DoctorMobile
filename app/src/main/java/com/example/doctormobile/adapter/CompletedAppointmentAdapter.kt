package com.example.doctormobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctormobile.databinding.AppointmentDsiplayLayoutBinding
import com.example.doctormobile.models.CompletedAppointment
import com.example.doctormobile.util.completedQuery
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CompletedAppointmentsAdapter(private val listener: OnItemClickListener) :
    FirestoreRecyclerAdapter<CompletedAppointment, CompletedAppointmentsAdapter.CompletedAppointmentViewHolder>(
        options
    ) {


    companion object {
        var options: FirestoreRecyclerOptions<CompletedAppointment> =
            FirestoreRecyclerOptions.Builder<CompletedAppointment>()
                .setQuery(
                    completedQuery,
                    CompletedAppointment::class.java
                )
                .build()
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

    override fun onBindViewHolder(
        holder: CompletedAppointmentViewHolder,
        position: Int,
        model: CompletedAppointment
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(item: CompletedAppointment)
    }

    inner class CompletedAppointmentViewHolder(private val binding: AppointmentDsiplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemClick(item)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: CompletedAppointment) {
            binding.apply {
                nameTextView.text = item.name
                caseIdTextView.text = "Case Id: ${item.caseId}"
            }
        }
    }
}