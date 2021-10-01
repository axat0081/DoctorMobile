package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
    }
}