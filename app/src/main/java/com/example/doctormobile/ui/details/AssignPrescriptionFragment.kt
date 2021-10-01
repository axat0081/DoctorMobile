package com.example.doctormobile.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentAssignPrescriptionBinding
import com.example.doctormobile.viewModel.AppointmentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AssignPrescriptionFragment : Fragment(R.layout.fragment_assign_prescription) {
    private var _binding: FragmentAssignPrescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AppointmentsViewModel>()
    private val args by navArgs<AssignPrescriptionFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAssignPrescriptionBinding.bind(view)
        val appointment = args.appointment
        binding.apply {
            viewModel.assignedAppointment = appointment
            prescriptionEditText.setText(viewModel.prescription)
            caseIdTextView.text = "Case Id: ${appointment.caseId}"
            nameTextView.text = "Name: ${appointment.name}"
            causeTextView.text = "Cause: ${appointment.cause}"
            docNameTextView.text = "Doctor: ${appointment.docName}"
            enrollTextView.text = "Enrollment No: ${appointment.enrollNo}"
            prescriptionEditText.addTextChangedListener {
                viewModel.prescription = it.toString()
            }
            assignPrescriptionButton.setOnClickListener {
                viewModel.onPrescribeClick()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}