package com.example.doctormobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctormobile.databinding.AppointmentDsiplayLayoutBinding
import com.example.doctormobile.models.AssignedAppointment
import com.example.doctormobile.util.assignedQuery
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class AssignedAppointmentsAdapter(private val listener: OnItemClickListener) :
    FirestoreRecyclerAdapter<AssignedAppointment, AssignedAppointmentsAdapter.AssignedAppointmentViewHolder>(
        options
    ) {


    companion object {
        var options: FirestoreRecyclerOptions<AssignedAppointment> =
            FirestoreRecyclerOptions.Builder<AssignedAppointment>()
                .setQuery(
                    assignedQuery,
                    AssignedAppointment::class.java
                )
                .build()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssignedAppointmentViewHolder =
        AssignedAppointmentViewHolder(
            AppointmentDsiplayLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: AssignedAppointmentViewHolder,
        position: Int,
        model: AssignedAppointment
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(item: AssignedAppointment)
    }

    inner class AssignedAppointmentViewHolder(private val binding: AppointmentDsiplayLayoutBinding) :
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
        fun bind(item: AssignedAppointment) {
            binding.apply {
                nameTextView.text = item.name
                caseIdTextView.text = "Case Id: ${item.caseId}"
            }
        }
    }
}