package com.example.doctormobile.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentPendingDetailsBinding

class PendingDetailsFragment : Fragment(R.layout.fragment_pending_details) {
    private var _binding: FragmentPendingDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PendingDetailsFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPendingDetailsBinding.bind(view)
        val appointment = args.pendingAppointmnent
        binding.apply {
            caseIdTextView.text = "Case Id: ${appointment.caseId}"
            nameTextView.text = "Name: ${appointment.name}"
            enrollTextView.text = "Enrollment No: ${appointment.enrollNo}"
            causeTextView.text = "Cause: \n ${appointment.cause}"
            assignAppointmentButton.setOnClickListener {
                findNavController().navigate(
                    PendingDetailsFragmentDirections.actionPendingDetailsFragmentToAssignAppointmentFragment(
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