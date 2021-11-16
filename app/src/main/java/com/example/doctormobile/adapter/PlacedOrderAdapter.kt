package com.example.doctormobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctormobile.databinding.AppointmentDsiplayLayoutBinding
import com.example.doctormobile.models.MedOrder
import com.example.doctormobile.util.orderRefQuery
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class PlacedOrderAdapter(private val listener: OnItemClickListener) :
    FirebaseRecyclerAdapter<MedOrder, PlacedOrderAdapter.MedOrderViewHolder>(options) {

    companion object {
        val options = FirebaseRecyclerOptions.Builder<MedOrder>()
            .setQuery(orderRefQuery, MedOrder::class.java)
            .build()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedOrderViewHolder =
        MedOrderViewHolder(
            AppointmentDsiplayLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MedOrderViewHolder, position: Int, model: MedOrder) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(order: MedOrder)
    }

    inner class MedOrderViewHolder(private val binding: AppointmentDsiplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemClick(item)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(order: MedOrder) {
            binding.apply {
                caseIdTextView.text = "Order Id: ${order.id}"
                nameTextView.text = "Email: ${order.email}"
            }
        }
    }
}