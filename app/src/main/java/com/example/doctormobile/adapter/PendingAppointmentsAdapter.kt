package com.example.doctormobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctormobile.databinding.AppointmentDsiplayLayoutBinding
import com.example.doctormobile.models.PendingAppointment
import com.example.doctormobile.util.pendingQuery
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PendingAppointmentsAdapter(private val listener: OnItemClickListener) :
    FirestoreRecyclerAdapter<PendingAppointment, PendingAppointmentsAdapter.PendingAppointmentViewHolder>(
        options
    ) {


    companion object {
        var options: FirestoreRecyclerOptions<PendingAppointment> =
            FirestoreRecyclerOptions.Builder<PendingAppointment>()
                .setQuery(
                    pendingQuery,
                    PendingAppointment::class.java
                )
                .build()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingAppointmentViewHolder =
        PendingAppointmentViewHolder(
            AppointmentDsiplayLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: PendingAppointmentViewHolder,
        position: Int,
        model: PendingAppointment
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(item: PendingAppointment)
    }

    inner class PendingAppointmentViewHolder(private val binding: AppointmentDsiplayLayoutBinding) :
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
        fun bind(item: PendingAppointment) {
            binding.apply {
                nameTextView.text = item.name
                caseIdTextView.text = "Case Id: ${item.caseId}"
            }
        }
    }
}