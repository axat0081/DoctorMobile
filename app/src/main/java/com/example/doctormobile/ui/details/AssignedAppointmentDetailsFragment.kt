package com.example.doctormobile.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.doctormobile.MainActivity
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentAssignedAppointmentDetailsBinding
import com.example.doctormobile.models.AssignedAppointment
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
@AndroidEntryPoint
class AssignedAppointmentDetailsFragment :
    Fragment(R.layout.fragment_assigned_appointment_details) {
    private var _binding: FragmentAssignedAppointmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AssignedAppointmentDetailsFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAssignedAppointmentDetailsBinding.bind(view)
        val appointment: AssignedAppointment = args.appointment
        binding.apply {
            if(!MainActivity.isDoc){
                assignPrescriptionButton.isVisible = false
            }
            caseIdTextView.text = "Case Id: ${appointment.caseId}"
            nameTextView.text = "Name: ${appointment.name}"
            causeTextView.text = "Cause: ${appointment.cause}"
            docNameTextView.text = "Doctor: ${appointment.docName}"
            enrollTextView.text = "Enrollment No: ${appointment.enrollNo}"
            timeTextView.text = "Time: ${DateFormat.getDateTimeInstance().format(appointment.time)}"
            assignPrescriptionButton.setOnClickListener {
                findNavController().navigate(
                    AssignedAppointmentDetailsFragmentDirections.actionAssignedAppointmentDetailsFragmentToAssignPrescriptionFragment(
                        appointment
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}