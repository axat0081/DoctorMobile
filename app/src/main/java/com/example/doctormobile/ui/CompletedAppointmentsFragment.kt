package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctormobile.R
import com.example.doctormobile.adapter.CompletedAppointmentsAdapter
import com.example.doctormobile.databinding.FragmentCompeletedAppointmentsBinding
import com.example.doctormobile.models.CompletedAppointment

class CompletedAppointmentsFragment : Fragment(R.layout.fragment_compeleted_appointments),
    CompletedAppointmentsAdapter.OnItemClickListener {
    private var _binding: FragmentCompeletedAppointmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var completedAdapter: CompletedAppointmentsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompeletedAppointmentsBinding.bind(view)
        completedAdapter = CompletedAppointmentsAdapter(this)
        binding.apply {
            completedAppointmentsRecyclerview.apply {
                adapter = completedAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onItemClick(item: CompletedAppointment) {
        findNavController().navigate(
            CompletedAppointmentsFragmentDirections.actionCompletedAppointmentsFragmentToCompletedAppointmentsDetailsFragment(item)
        )
    }

    override fun onStart() {
        super.onStart()
        completedAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        completedAdapter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}