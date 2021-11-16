package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctormobile.R
import com.example.doctormobile.adapter.MedAdapter
import com.example.doctormobile.databinding.FragmentOrderMedicinesBinding
import com.example.doctormobile.viewModel.OrderMedicinesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrderMedicinesFragment : Fragment(R.layout.fragment_order_medicines) {
    private var _binding: FragmentOrderMedicinesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OrderMedicinesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderMedicinesBinding.bind(view)
        val medAdapter = MedAdapter(requireContext(), viewModel.medList, viewModel)
        binding.apply {
            progressBar.isVisible = false
            medRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = medAdapter
            }
            orderButton.setOnClickListener {
                progressBar.isVisible = true
                viewModel.onOrderClick()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.orderFlow.collect { event ->
                    when (event) {
                        is OrderMedicinesViewModel.OrderEvent.OrderSuccess -> {
                            progressBar.isVisible = false
                            Snackbar.make(
                                requireView(),
                                event.msg,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        is OrderMedicinesViewModel.OrderEvent.OrderFailure -> {
                            progressBar.isVisible = false
                            Snackbar.make(
                                requireView(),
                                event.msg,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}