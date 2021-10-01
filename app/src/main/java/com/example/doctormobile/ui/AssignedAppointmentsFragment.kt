package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctormobile.R
import com.example.doctormobile.adapter.AssignedAppointmentsAdapter
import com.example.doctormobile.databinding.FragmentAssignedAppointmentsBinding
import com.example.doctormobile.models.AssignedAppointment

class AssignedAppointmentsFragment : Fragment(R.layout.fragment_assigned_appointments),
    AssignedAppointmentsAdapter.OnItemClickListener {
    private var _binding: FragmentAssignedAppointmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var assignedAdapter: AssignedAppointmentsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAssignedAppointmentsBinding.bind(view)
        assignedAdapter = AssignedAppointmentsAdapter(this)
        binding.apply {
            assignedAppointmentsRecyclerview.apply {
                adapter = assignedAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onItemClick(item: AssignedAppointment) {
        findNavController().navigate(
            AssignedAppointmentsFragmentDirections
                .actionAssignedAppointmentsFragmentToAssignedAppointmentDetailsFragment(item)
        )
    }

    override fun onStart() {
        super.onStart()
        assignedAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        assignedAdapter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}