package com.example.doctormobile.ui.details

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentAssignAppointmentBinding
import com.example.doctormobile.viewModel.AppointmentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class AssignAppointmentFragment : Fragment(R.layout.fragment_assign_appointment),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentAssignAppointmentBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AssignAppointmentFragmentArgs>()
    private val viewModel by viewModels<AppointmentsViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAssignAppointmentBinding.bind(view)
        val appointment = args.pendingAppointment
        viewModel.pendingAppointment = appointment
        binding.apply {
            docNameEditText.setText(viewModel.docName)
            dateTextView.text =
                "Date: ${viewModel.savedDay}/${viewModel.savedMonth + 1}/${viewModel.savedYear}"
            timeTextView.text = "Time: ${viewModel.savedHour}:${viewModel.savedMinutes}"
            caseIdTextView.text = "Case Id: ${appointment.caseId}"
            nameTextView.text = "Name: ${appointment.name}"
            enrollTextView.text = "Enrollment No: ${appointment.enrollNo}"
            causeTextView.text = "Cause: \n ${appointment.cause}"
            docNameEditText.addTextChangedListener {
                viewModel.docName = it.toString()
            }
            selectDataTimeButton.setOnClickListener {
                getDateTimeCalender()
                DatePickerDialog(
                    requireContext(),
                    this@AssignAppointmentFragment,
                    viewModel.year,
                    viewModel.month,
                    viewModel.day
                ).show()
            }
            assignAppointmentButton.setOnClickListener {
                viewModel.onAssignClick()
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

    private fun getDateTimeCalender() {
        val cal = Calendar.getInstance()
        viewModel.day = cal.get(Calendar.DAY_OF_MONTH)
        viewModel.month = cal.get(Calendar.MONTH)
        viewModel.year = cal.get(Calendar.YEAR)
        viewModel.hour = cal.get(Calendar.HOUR)
        viewModel.minutes = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.savedDay = dayOfMonth
        viewModel.savedMonth = month
        viewModel.savedYear = year
        getDateTimeCalender()
        TimePickerDialog(
            requireContext(),
            this,
            viewModel.hour,
            viewModel.minutes,
            true
        ).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.savedHour = hourOfDay
        viewModel.savedMinutes = minute
        binding.apply {
            dateTextView.text =
                "Date: ${viewModel.savedDay}/${viewModel.savedMonth + 1}/${viewModel.savedYear}"
            timeTextView.text = "Time: ${viewModel.savedHour}:${viewModel.savedMinutes}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}