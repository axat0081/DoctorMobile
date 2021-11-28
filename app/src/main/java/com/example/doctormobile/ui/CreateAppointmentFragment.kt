package com.example.doctormobile.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentCreateAppointmentBinding
import com.example.doctormobile.viewModel.AppointmentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAppointmentFragment : Fragment(R.layout.fragment_create_appointment) {
    private var _binding: FragmentCreateAppointmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AppointmentsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateAppointmentBinding.bind(view)
        binding.apply {
            nameEditText.editText!!.setText(viewModel.studentName)
            causeEditText.editText!!.setText(viewModel.cause)
            enrollEditText.editText!!.setText(viewModel.enrollNo)
            nameEditText.editText!!.addTextChangedListener {
                viewModel.studentName = it.toString()
            }
            causeEditText.editText!!.addTextChangedListener {
                viewModel.cause = it.toString()
            }
            enrollEditText.editText!!.addTextChangedListener {
                viewModel.enrollNo = it.toString()
            }
            createButton.setOnClickListener {
                viewModel.onCreateClick()
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.appointmentFlow.collect { event ->
                    when (event) {
                        is AppointmentsViewModel.AppointmentEvent.CreationFailure -> {
                            Snackbar.make(
                                requireView(),
                                event.msg,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        is AppointmentsViewModel.AppointmentEvent.CreationSuccess -> {
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
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.assigned_appointments -> {
                findNavController().navigate(
                    R.id.assignAppointmentFragment
                )
            }
            R.id.completed_appointments -> {
                findNavController().navigate(
                    R.id.completedAppointmentsFragment
                )
            }
            R.id.orderMedicinesFragment -> {
                findNavController().navigate(
                    R.id.orderMedicinesFragment
                )
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}