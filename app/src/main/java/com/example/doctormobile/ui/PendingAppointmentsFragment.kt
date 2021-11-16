package com.example.doctormobile.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctormobile.R
import com.example.doctormobile.adapter.PendingAppointmentsAdapter
import com.example.doctormobile.databinding.FragmentPendingAppoinmentsBinding
import com.example.doctormobile.models.PendingAppointment

class PendingAppointmentsFragment : Fragment(R.layout.fragment_pending_appoinments),
    PendingAppointmentsAdapter.OnItemClickListener {
    private var _binding: FragmentPendingAppoinmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemAdapter: PendingAppointmentsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPendingAppoinmentsBinding.bind(view)
        itemAdapter = PendingAppointmentsAdapter(this)
        binding.apply {
            pendingAppointmentsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = itemAdapter
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        itemAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        itemAdapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_appointments -> {
                findNavController().navigate(
                    PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToCreateAppointmentFragment()
                )
            }
            R.id.assigned_appointments -> {
                findNavController().navigate(
                    PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToAssignedAppointmentsFragment()
                )
            }
            R.id.completed_appointments -> {
                findNavController().navigate(
                    PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToCompletedAppointmentsFragment()
                )
            }
            R.id.bookmarked_appointments -> {
                findNavController().navigate(
                    PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToBookMarkedAppointmentsFragment()
                )
            }
            R.id.orderMedicinesFragment -> {
                findNavController().navigate(
                    PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToOrderMedicinesFragment()
                )
            }
            R.id.placedOrdersFragment -> {
                findNavController().navigate(
                    PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToPlacedOrdersFragment()
                )
            }
        }
        return true
    }

    override fun onItemClick(item: PendingAppointment) {
        findNavController().navigate(
            PendingAppointmentsFragmentDirections.actionPendingAppointmentsFragmentToPendingDetailsFragment(
                item
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}