package com.example.doctormobile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val app: Application,
    private val state: SavedStateHandle
) : AndroidViewModel(app) {
    var studentName = state.get<String>("student_name") ?: ""
        set(value) {
            field = value
            state.set("student_name", studentName)
        }
    var enrollNo = state.get<String>("enrollment_number") ?: ""
        set(value) {
            field = value
            state.set("enrollment_number", enrollNo)
        }
    var cause = state.get<String>("cause") ?: ""
        set(value) {
            field = value
            state.set("cause", cause)
        }
    var caseId = state.get<String>("case_id") ?: ""
        set(value) {
            field = value
            state.set("caseId", caseId)
        }
    var prescription = state.get<String>("prescription") ?: ""
        set(value) {
            field = value
            state.set("prescription", prescription)
        }
    var appointmentTime = state.get<String>("appointment_time") ?: ""
        set(value) {
            field = value
            state.set("appointment_time", appointmentTime)
        }
    var appointmentDate = state.get<String>("appointment_time") ?: ""
        set(value) {
            field = value
            state.set("appointment_time", appointmentDate)
        }

}