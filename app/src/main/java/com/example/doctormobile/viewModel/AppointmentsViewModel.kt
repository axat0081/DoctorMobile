package com.example.doctormobile.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.doctormobile.models.AssignedAppointment
import com.example.doctormobile.models.CompletedAppointment
import com.example.doctormobile.models.PendingAppointment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val app: Application,
    private val state: SavedStateHandle,
    @Named("Pending")
    private val pendingRef: DatabaseReference,
    @Named("Assigned")
    private val assignedRef: DatabaseReference,
    @Named("Completed")
    private val completedRef: DatabaseReference,

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

    var docName = state.get<String>("doc_name") ?: ""
        set(value) {
            field = value
            state.set("doc_name", docName)
        }

    var pendingAppointment = state.get<PendingAppointment>("pending_appointment")
        set(value) {
            field = value
            state.set("pending_appointment", pendingAppointment)
        }

    var assignedAppointment = state.get<AssignedAppointment>("assigned_appointment")
        set(value) {
            field = value
            state.set("assigned_appointment", assignedAppointment)
        }

    var hour = state.get<Int>("hour") ?: 0
        set(value) {
            field = value
            state.set("hour", hour)
        }
    var minutes = state.get<Int>("minutes") ?: 0
        set(value) {
            field = value
            state.set("minutes", minutes)
        }
    var day = state.get<Int>("day") ?: 0
        set(value) {
            field = value
            state.set("day", day)
        }
    var month = state.get<Int>("month") ?: 0
        set(value) {
            field = value
            state.set("month", month)
        }
    var year = state.get<Int>("year") ?: 0
        set(value) {
            field = value
            state.set("year", year)
        }

    var savedHour = state.get<Int>("savedHour") ?: 0
        set(value) {
            field = value
            state.set("savedHour", savedHour)
        }

    var savedMinutes = state.get<Int>("savedMinutes") ?: 0
        set(value) {
            field = value
            state.set("savedMinutes", savedMinutes)
        }
    var savedDay = state.get<Int>("savedDay") ?: 0
        set(value) {
            field = value
            state.set("savedDay", savedDay)
        }
    var savedMonth = state.get<Int>("savedMonth") ?: 0
        set(value) {
            field = value
            state.set("savedMonth", savedMonth)
        }
    var savedYear = state.get<Int>("savedYear") ?: 0
        set(value) {
            field = value
            state.set("savedYear", savedYear)
        }

    private val appointmentChannel = Channel<AppointmentEvent>()
    val appointmentFlow = appointmentChannel.receiveAsFlow()

    @SuppressLint("SimpleDateFormat")
    fun onAssignClick() {
        if (pendingAppointment == null) {
            return
        }
        if (savedDay == 0 && savedHour == 0 && savedMinutes == 0 &&
            savedMinutes == 0 && savedMonth == 0 && savedYear == 0
        ) {
            viewModelScope.launch {
                appointmentChannel.send(AppointmentEvent.CreationFailure("Please select date and time"))
            }
            return
        }
        if (docName.isBlank()) {
            viewModelScope.launch {
                appointmentChannel.send(AppointmentEvent.CreationFailure("Doctor name cannot be empty"))
            }
        }
        viewModelScope.launch {
            pendingRef.child(pendingAppointment!!.caseId!!)
                .removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val id = pendingAppointment!!.caseId!!
                        val time =
                            savedYear.toString() + "-" + savedMonth.toString() + "-" + savedDay.toString() +
                                    " " + savedHour.toString() + ":" + savedMinutes.toString()
                        Log.e("Time", time)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
                        var appointmentTime = 0L
                        try {
                            appointmentTime = dateFormat.parse(time).time
                        } catch (e: Exception) {
                            viewModelScope.launch {
                                viewModelScope.launch {
                                    appointmentChannel.send(AppointmentEvent.CreationFailure(e.localizedMessage!!))
                                }
                            }
                            return@addOnCompleteListener
                        }
                        val assignedAppointment = AssignedAppointment(
                            caseId = pendingAppointment!!.caseId,
                            enrollNo = pendingAppointment!!.enrollNo,
                            name = pendingAppointment!!.name,
                            cause = pendingAppointment!!.cause,
                            docName = docName,
                            time = appointmentTime,
                        )
                        assignedRef.child(id).setValue(assignedAppointment)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    viewModelScope.launch {
                                        appointmentChannel.send(AppointmentEvent.CreationSuccess("Appointment assigned"))
                                    }
                                } else {
                                    viewModelScope.launch {
                                        appointmentChannel.send(
                                            AppointmentEvent.CreationFailure(
                                                task2.exception!!.localizedMessage!!
                                            )
                                        )
                                    }
                                }
                            }
                    } else {
                        viewModelScope.launch {
                            appointmentChannel.send(
                                AppointmentEvent.CreationFailure(
                                    task.exception!!.localizedMessage!!
                                )
                            )
                        }
                    }
                }
        }
    }

    fun onCreateClick() {
        if (studentName.isBlank()) {
            viewModelScope.launch {
                appointmentChannel.send(AppointmentEvent.CreationFailure("Name cannot be empty"))
            }
            return
        }
        if (cause.isBlank()) {
            viewModelScope.launch {
                appointmentChannel.send(AppointmentEvent.CreationFailure("Reason cannot be empty"))
            }
            return
        }
        if (enrollNo.isBlank()) {
            viewModelScope.launch {
                appointmentChannel.send(AppointmentEvent.CreationFailure("Enrollment cannot be empty"))
            }
            return
        }

        viewModelScope.launch {
            val id = pendingRef.push().key
            val item = PendingAppointment(
                caseId = id,
                name = studentName,
                cause = cause,
                time = System.currentTimeMillis(),
                enrollNo = enrollNo
            )

            pendingRef.child(id!!).setValue(item).addOnCompleteListener {
                if (it.isSuccessful) {
                    viewModelScope.launch {
                        appointmentChannel.send(AppointmentEvent.CreationSuccess("Appointment Request Sent"))
                    }
                } else {
                    viewModelScope.launch {
                        appointmentChannel.send(AppointmentEvent.CreationFailure(it.exception?.localizedMessage.toString()))
                    }
                }
            }
        }
    }

    fun onPrescribeClick() {
        if (prescription.isBlank()) {
            viewModelScope.launch {
                appointmentChannel.send(AppointmentEvent.CreationFailure("Prescription cannot be blank"))
            }
            return
        }
        Log.e("ID", assignedAppointment!!.caseId!!)
        assignedRef.child(assignedAppointment!!.caseId!!).removeValue()
            .addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    val completedAppointment = CompletedAppointment(
                        caseId = assignedAppointment!!.caseId,
                        name = assignedAppointment!!.name,
                        docName = assignedAppointment!!.docName,
                        prescription = prescription,
                        enrollNo = assignedAppointment!!.enrollNo,
                        cause = assignedAppointment!!.cause
                    )
                    completedRef.child(assignedAppointment!!.caseId!!)
                        .setValue(completedAppointment)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                viewModelScope.launch {
                                    appointmentChannel.send(AppointmentEvent.CreationSuccess("Appointment Prescribed"))
                                }
                            } else {
                                viewModelScope.launch {
                                    appointmentChannel.send(AppointmentEvent.CreationFailure(task2.exception!!.localizedMessage!!))
                                }
                            }
                        }
                } else {
                    viewModelScope.launch {
                        viewModelScope.launch {
                            appointmentChannel.send(AppointmentEvent.CreationFailure(task1.exception!!.localizedMessage!!))
                        }
                    }
                }
            }

    }

    sealed class AppointmentEvent {
        data class CreationSuccess(val msg: String) : AppointmentEvent()
        data class CreationFailure(val msg: String) : AppointmentEvent()
    }

}