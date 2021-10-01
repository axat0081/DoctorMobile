package com.example.doctormobile.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctormobile.models.CachedCompletedAppointment
import com.example.doctormobile.models.CompletedAppointment
import com.example.doctormobile.room.CompletedAppointmentsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedAppointmentsViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val dao: CompletedAppointmentsDao
) : ViewModel() {

    var appointment = state.get<CompletedAppointment>("completed_appointment_details")
        set(value) {
            field = value
            state.set("completed_appointment_details", appointment)
        }
    private val bookMarkChannel = Channel<BookMarkEvent>()
    val bookMarkFlow = bookMarkChannel.receiveAsFlow()
    var isBookMarked = MutableStateFlow(false)
    val bookMarkedAppointments = dao.getCompletedAppointments()
    fun onBookMarkClick() {
        isBookMarked.value = !isBookMarked.value
        if (isBookMarked.value) {
            viewModelScope.launch {
                dao.insertCompletedAppointment(
                    CachedCompletedAppointment(
                        caseId = appointment!!.caseId!!,
                        name = appointment!!.name!!,
                        docName = appointment!!.docName!!,
                        prescription = appointment!!.prescription!!,
                        enrollNo = appointment!!.enrollNo!!,
                        cause = appointment!!.cause!!
                    )
                )
            }
            viewModelScope.launch {
                bookMarkChannel.send(BookMarkEvent.BookMarkSuccess("Added to Bookmarks"))
            }
        } else {
            viewModelScope.launch {
                dao.deleteCompletedAppointment(
                    CachedCompletedAppointment(
                        caseId = appointment!!.caseId!!,
                        name = appointment!!.name!!,
                        docName = appointment!!.docName!!,
                        prescription = appointment!!.prescription!!,
                        enrollNo = appointment!!.enrollNo!!,
                        cause = appointment!!.cause!!
                    )
                )
            }
            viewModelScope.launch {
                bookMarkChannel.send(BookMarkEvent.BookMarkSuccess("Removed from Bookmarks"))
            }
        }
    }

    sealed class BookMarkEvent {
        data class BookMarkSuccess(val msg: String) : BookMarkEvent()
    }
}