package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctormobile.R
import com.example.doctormobile.adapter.PlacedOrderAdapter
import com.example.doctormobile.databinding.FragmentPlacedOrdersBinding
import com.example.doctormobile.models.MedOrder

class PlacedOrdersFragment : Fragment(R.layout.fragment_placed_orders),
    PlacedOrderAdapter.OnItemClickListener {
    private var _binding: FragmentPlacedOrdersBinding? = null
    private val binding get() = _binding!!
    private lateinit var placedOrderAdapter: PlacedOrderAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlacedOrdersBinding.bind(view)
        placedOrderAdapter = PlacedOrderAdapter(this)
        binding.apply {
            placedOrdersRecyclerview.apply {
                adapter = placedOrderAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        placedOrderAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        placedOrderAdapter.stopListening()
    }

    override fun onItemClick(order: MedOrder) {
        findNavController().navigate(
            PlacedOrdersFragmentDirections.actionPlacedOrdersFragmentToPlacedOrderDetailsFragment(
                order
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}