package com.example.doctormobile.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentCompletedAppointmentDetailsBinding
import com.example.doctormobile.viewModel.CompletedAppointmentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CompletedAppointmentsDetailsFragment :
    Fragment(R.layout.fragment_completed_appointment_details) {
    private var _binding: FragmentCompletedAppointmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<CompletedAppointmentsDetailsFragmentArgs>()
    private val viewModel by viewModels<CompletedAppointmentsViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompletedAppointmentDetailsBinding.bind(view)
        val appointment = args.appointment
        binding.apply {
            viewModel.appointment = appointment
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.isBookMarked.collect { isBookMarked ->
                    if (isBookMarked) {
                        saveButton.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.un_bookmark,
                            0,
                            0,
                            0
                        )
                        saveButton.text = "Remove from Bookmarks"
                    } else {
                        saveButton.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.bookmark,
                            0,
                            0,
                            0
                        )
                        saveButton.text = "Bookmark"
                    }
                }
            }
            caseIdTextView.text = "Case Id: ${appointment.caseId}"
            nameTextView.text = "Name: ${appointment.name}"
            enrollTextView.text = "Enrollment No: ${appointment.enrollNo}"
            causeTextView.text = "Cause: \n ${appointment.cause}"
            prescriptionTextView.text = "Prescription: ${appointment.prescription}"
            docNameTextView.text = "Doctor: ${appointment.docName}"
            saveButton.setOnClickListener {
                viewModel.onBookMarkClick()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.bookMarkFlow.collect { event ->
                    when (event) {
                        is CompletedAppointmentsViewModel.BookMarkEvent.BookMarkSuccess -> {
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