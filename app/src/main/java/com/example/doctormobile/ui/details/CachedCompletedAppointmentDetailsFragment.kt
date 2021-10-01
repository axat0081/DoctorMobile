package com.example.doctormobile.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentCompletedAppointmentDetailsBinding
import com.example.doctormobile.models.CachedCompletedAppointment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CachedCompletedAppointmentDetailsFragment: Fragment(R.layout.fragment_completed_appointment_details) {
    private var _binding: FragmentCompletedAppointmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<CachedCompletedAppointmentDetailsFragmentArgs>()
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding  = FragmentCompletedAppointmentDetailsBinding.bind(view)
        val appointment = args.appointment
        binding.apply {
            saveButton.isVisible = false
            caseIdTextView.text = "Case Id: ${appointment.caseId}"
            nameTextView.text = "Name: ${appointment.name}"
            enrollTextView.text = "Enrollment No: ${appointment.enrollNo}"
            causeTextView.text = "Cause: \n ${appointment.cause}"
            prescriptionTextView.text = "Prescription: ${appointment.prescription}"
            docNameTextView.text = "Doctor: ${appointment.docName}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}